package com.epam.cm.core.httpclient;


/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class DefaultSecureHttpClientSingleton {
    private static DefaultSecureHttpClient defaultSecureHttpClientInstance;

    private DefaultSecureHttpClientSingleton(){
    }

    public static synchronized DefaultSecureHttpClient getInstance() {
        if (defaultSecureHttpClientInstance == null)
            defaultSecureHttpClientInstance = new DefaultSecureHttpClient();
        return defaultSecureHttpClientInstance;
    }


}
