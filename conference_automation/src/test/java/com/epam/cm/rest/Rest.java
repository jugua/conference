package com.epam.cm.rest;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.epam.cm.core.restclient.ConfManagRestClient;

import net.serenitybdd.junit.runners.SerenityRunner;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class Rest {

    ConfManagRestClient rest = new ConfManagRestClient();

    @Test
    public void loginRest() throws IOException, NoSuchAlgorithmException {
        rest.loginAndLogout();
    }

    @Test
    public void forgotPwEmptyMail() throws NoSuchAlgorithmException, IOException {
        rest.forgotPwEmptyMail();
    }

    @Test
    public void forgotPwValidData()throws NoSuchAlgorithmException, IOException{
        rest.forgotPw();
    }


}
