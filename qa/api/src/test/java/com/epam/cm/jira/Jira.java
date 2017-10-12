package com.epam.cm.jira;

import java.lang.annotation.*;

/**
 * Created by Kseniia_Semioshko on 10/4/2017.
 */

@Target(ElementType.METHOD)

@Retention(RetentionPolicy.RUNTIME)
public @interface Jira {
    String [] value();
}