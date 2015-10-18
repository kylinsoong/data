package com.weibo.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class OAuth2Procedure {
    
    static String clientId = "3876415151";
    
    static String clientSecret = "5e4f21ac2bd6596689fe16d8215a24e5";
    
    static String redirectUri="https://api.weibo.com/oauth2/default.html";

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        
        // 1. authorization
        System.out.println("Open your broswer, access below URL to execute authorization:");
        System.out.println("https://api.weibo.com/oauth2/authorize?client_id=" + clientId + "&response_type=code&redirect_uri=" + redirectUri + "&forcelogin=true");
        System.out.println();
        
        // 2. get AccessToken
        System.out.println("Input code in above authorization result URL:");
        String code = new Scanner(System.in).next();
        getAccessToken(code);
        
        // 3. post a Weibo
        System.out.println("Input accessToken from above token");
        String accessToken = new Scanner(System.in).next();
        updateStatus("Teiid(http://teiid.jboss.org/) is a data virtualization system that allows applications to use data from multiple, heterogenous data stores.", accessToken);
    }
    
    private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        trustAllCerts[0] = new X509TrustManager(){

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
    
    static void getAccessToken(String code) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {

        String url="https://api.weibo.com/oauth2/access_token";
        String parameters = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=authorization_code" + "&redirect_uri=" + redirectUri + "&code=" + code;
        postUrl(url, parameters);
    }

    private static void postUrl(String url, String parameters) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {

        System.out.println("Post URL: " + url + "?" + parameters);
        trustAllHttpsCertificates();
        URLConnection conn = new URL(url).openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
        out.write(parameters);
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line = null;
        System.out.println("Response:");
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }
        System.out.println();
    }
    
    static void updateStatus(String text, String accessToken) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {

        String url = "https://api.weibo.com/2/statuses/update.json";
        String parameters = "status=" + text + "&access_token=" + accessToken;
        postUrl(url, parameters);
    }

}
