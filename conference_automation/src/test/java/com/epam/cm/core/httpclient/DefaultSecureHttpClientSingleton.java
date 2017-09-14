package com.epam.cm.core.httpclient;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class DefaultSecureHttpClientSingleton {
    private static DefaultSecureHttpClient defaultSecureHttpClientInstance;

    private DefaultSecureHttpClientSingleton(){
    }

    public static synchronized DefaultSecureHttpClient getInstance() throws IOException, NoSuchAlgorithmException {
        if (defaultSecureHttpClientInstance == null)
            defaultSecureHttpClientInstance = new DefaultSecureHttpClient();
        return defaultSecureHttpClientInstance;
    }
}
