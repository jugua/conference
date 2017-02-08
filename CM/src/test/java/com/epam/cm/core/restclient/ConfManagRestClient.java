package com.epam.cm.core.restclient;

import com.epam.cm.core.httpclient.DefaultSecureHttpClientSingleton;
import com.epam.cm.core.httpclient.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class ConfManagRestClient {
 /*   public HttpResponse performGetRequest() {
        return HttpRequest.get("http://10.17.132.37:8050").addBasicAuth("login", "password")
                .addContentType("enum").addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().getCookieStore().getCookies().stream().filter(c -> c.getName().equals("crtp-toke")).findFirst().get().getValue()).sendAndGetResponse(200);
    }*/
 public HttpResponse performGetRequest() {
     return HttpRequest.get("http://10.17.132.37:8050").addBasicAuth("login", "password")
             .addContentType("enum").addTokenHeader(DefaultSecureHttpClientSingleton
                     .getInstance().getCookieStore()
                     .getCookies().stream().filter(c -> c.getName( )
                     .equals("crtp-toke")).findFirst().get()
                     .getValue()).sendAndGetResponse(200);
 }
}
