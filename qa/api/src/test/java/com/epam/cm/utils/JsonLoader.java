package com.epam.cm.utils;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Kseniia_Semioshko on 10/13/2017.
 */
public class JsonLoader {
    public static String asString(String fileName){

        URL url = Resources.getResource(fileName);
        try {
            return Resources.toString(url, Charsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException("Please specify the correct path to json file: ", ex);
        }
    }
}
