package com.epam.cm.core.restclient;

import com.epam.cm.core.httpclient.DefaultSecureHttpClientSingleton;
import com.epam.cm.core.httpclient.HttpRequest;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Lev_Serba on 2/2/2017.
 */
public class ConfManagRestClient {

    public HttpResponse performGetRequest() throws NoSuchAlgorithmException {
        return HttpRequest.get("http://10.17.132.37:8050").sendAndGetResponse(200);
    }

    public HttpResponse postRequest() throws NoSuchAlgorithmException, IOException {
        HttpRequest.get("http://localhost:8050").sendAndGetResponse(200);
        HttpRequest.post("http://localhost:8050/api/login/")
                .addBasicAuth("speaker@speaker.com", "speaker")
                .addContentType("application/json;charset=UTF-8")
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(200);
         HttpRequest.get("http://localhost:8050/api/user/current")
                .addContentType("application/json;charset=UTF-8")
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(202);

       return HttpRequest.post("http://localhost:8050/api/logout")
                .addContentType("application/json;charset=UTF-8")
                .addHeader("Accept", "application/json;charset=UTF-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(204);
    }

}
