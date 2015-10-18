package com.weibo.sample;

import static com.weibo.sample.OAuth2Procedure.updateStatus;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * This need execute authorization in 'OAuth2Procedure'
 * 
 * @author kylin
 *
 */
public class PostWeibo {

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, MalformedURLException, IOException {

//        String authCode = "9a66d4f8ce22e8c892f4aac45deb4a47";
        
//        getAccessToken(authCode);
        
        String token = "2.00PZtDyBBfC2OE91c7a884547pNg4E";
        
//        System.out.println("Input accessToken from above token");
//        String accessToken = new Scanner(System.in).next();
        updateStatus("Teiid(http://teiid.jboss.org/) is a data virtualization system that allows applications to use data from multiple, heterogenous data stores.", token);
    }

}
