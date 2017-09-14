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

    public HttpResponse forgotPwEmptyMail() throws NoSuchAlgorithmException, IOException {
        HttpResponse response1 = HttpRequest.get("http://10.17.132.37:8050").sendAndGetResponse(200);
        return HttpRequest.post("http://10.17.132.37:8050/api/forgot-password")
                .addContentType("application/json;charset=utf-8")
                .addHeader("Accept", "application/json;charset=utf-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(400);
    }

    public HttpResponse forgotPw() throws NoSuchAlgorithmException, IOException {
        HttpResponse response1 = HttpRequest.get("http://10.17.132.37:8050").sendAndGetResponse(200);
        return HttpRequest.post("http://10.17.132.37:8050/api/forgot-password")
                .addContentType("application/json;charset=utf-8")
                .addHeader("Accept", "application/json;charset=utf-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())

                .sendAndGetResponse(200);
    }

    public HttpResponse loginAndLogout() throws NoSuchAlgorithmException, IOException {
        HttpResponse response1 = HttpRequest.get("http://10.17.132.37:8050").sendAndGetResponse(200);
        HttpResponse response2 = HttpRequest.post("http://10.17.132.37:8050/api/login")
                .addBasicAuth("speaker@speaker.com", "speaker")
                .addContentType("application/json;charset=utf-8")
                .addHeader("Accept", "application/json;charset=utf-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(200);
        HttpResponse response3 = HttpRequest.get("http://10.17.132.37:8050/api/user/current")
                .addContentType("application/json;charset=utf-8")
                .addHeader("Accept", "application/json;charset=utf-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())

                .sendAndGetResponse(202);

       return HttpRequest.post("http://10.17.132.37:8050/api/logout")
                .addContentType("application/json;charset=utf-8")
                .addHeader("Accept", "application/json;charset=utf-8")
                .addTokenHeader(DefaultSecureHttpClientSingleton.getInstance().
                        getCookieStore().getCookies()
                        .stream()
                        .filter(c -> c.getName().contains("XSRF-TOKEN"))
                        .findFirst()
                        .get().getValue())
                .sendAndGetResponse(204);
    }

}
