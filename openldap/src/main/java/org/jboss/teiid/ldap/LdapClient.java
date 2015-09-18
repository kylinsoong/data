package org.jboss.teiid.ldap;

import java.util.Hashtable;

import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.ReferralException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapClient {

    public static void main(String[] args) throws NamingException {
        
        String user = "kylin";
        String baseDN = "ou=Customers,dc=example,dc=com";
        String filter = "(uid={0})";

        Hashtable<String,String> env = new Hashtable<String,String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://10.66.218.46:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn=Manager,dc=example,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "redhat");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //additional
        env.put("baseCtxDN", "ou=Customers,dc=example,dc=com");
        env.put("roleAttributeID", "cn");
        env.put("roleFilter", "(uniqueMember={1})");
        env.put("rolesCtxDN", "ou=Tester,dc=example,dc=com");
        env.put("baseFilter", "(uid={0})");
        env.put("bindCredential", "redhat");
        env.put("bindDN", "cn=Manager,dc=example,dc=com");
        env.put("jboss.security.security_domain", "test-security");
        InitialLdapContext ctx = new InitialLdapContext(env, null);
        
        int searchTimeLimit = 10000 ;
        String distinguishedNameAttribute = "distinguishedName";
        
        SearchControls constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setTimeLimit(searchTimeLimit);
        String attrList[] = {distinguishedNameAttribute};
        constraints.setReturningAttributes(attrList);
        
        NamingEnumeration results = null;
        
        Object[] filterArgs = {user};
        
        LdapContext ldapCtx = ctx;
        
        boolean referralsLeft = true;
        SearchResult sr = null;
        while (referralsLeft) {
            try {
                results = ldapCtx.search(baseDN, filter, filterArgs, constraints);
                while (results.hasMore()) {
                    sr = (SearchResult) results.next();
                    break;
                 }
                 referralsLeft = false;
            } catch (ReferralException e) {
                ldapCtx = (LdapContext) e.getReferralContext();
                if (results != null) {
                   results.close();
                }
            }
        }
        
        if (sr == null){
            results.close();
        }
        
        String name = sr.getName();
        String userDN = null;
        Attributes attrs = sr.getAttributes();
        if (attrs != null) {
            Attribute dn = attrs.get(distinguishedNameAttribute);
            if (dn != null){
                userDN = (String) dn.get();
            }
        }
        
        results.close();
        results = null;
        boolean isPasswordValidated = true;
        
        if (userDN == null) {
            if (sr.isRelative() == true) {
                userDN = new CompositeName(name).get(0) + ("".equals(baseDN) ? "" : "," + baseDN);
            }
        }
        
        int searchScope = SearchControls.SUBTREE_SCOPE; 
        
        constraints = new SearchControls();
        constraints.setSearchScope(searchScope);
        constraints.setTimeLimit(searchTimeLimit);
        attrList = new String[] {"cn"};
        constraints.setReturningAttributes(attrList);
        rolesSearch(ctx, constraints, user, userDN, 0, 0);
        
        
        
    }
    
    static String rolesCtxDN = "ou=Tester,dc=example,dc=com";
    static String roleFilter = "(uniqueMember={1})";
    static String roleAttributeID = "cn" ;

    private static void rolesSearch(LdapContext ctx, SearchControls constraints, String user, String userDN, int recursionMax, int nesting) throws NamingException {

        
        
        LdapContext ldapCtx = ctx;
        
        Object[] filterArgs = {user, sanitizeDN(userDN)};
        boolean referralsExist = true;
        while (referralsExist) {
            NamingEnumeration results = ldapCtx.search(rolesCtxDN, roleFilter, filterArgs, constraints);
            try{
                while (results.hasMore()) {
                    SearchResult sr = (SearchResult) results.next();
                    String dn;
                    if (sr.isRelative()) {
                       dn = canonicalize(sr.getName());
                    } else {
                        dn = sr.getNameInNamespace();
                    }
                    
                    System.out.println(dn);
                    
                    
                    
//                    String[] attrNames = {roleAttributeID};
//                    Attributes result = null;
//                    if (sr.isRelative()) {
//                        result = ldapCtx.getAttributes(quoteDN(dn), attrNames);
//                    } else {
//                        result = getAttributesFromReferralEntity(sr, user, userDN); 
//                    }
                    
//                    System.out.println(results);
                }
                referralsExist = false;
                
            } catch (ReferralException e) {
                ldapCtx = (LdapContext) e.getReferralContext();
            }finally{
                if (results != null)
                   results.close();
             }
        }
    }

    private static Attributes getAttributesFromReferralEntity(SearchResult sr, String... users) {
        return null;
    }

    private static String quoteDN(String dn) {
        if (dn != null && !dn.startsWith("\"") && !dn.endsWith("\"") && dn.indexOf("/") > -1) {
            return "\"" + dn + "\"";
         } else {
            return dn;
         }
    }

    private static String canonicalize(String searchResult) {
      String result = searchResult;
      int len = searchResult.length();

      String appendRolesCtxDN = "" + ("".equals(rolesCtxDN) ? "" : "," + rolesCtxDN);
      if (searchResult.endsWith("\"")){
         result = searchResult.substring(0, len - 1) + appendRolesCtxDN + "\"";
      } else {
         result = searchResult + appendRolesCtxDN;
      }
      return result;
   }

    private static String sanitizeDN(final String dn) {
        if (dn != null && dn.startsWith("\"") && dn.endsWith("\"")) {
            return dn.substring(1, dn.length() - 1);
         } else {
            return dn;
         }
    }

}
