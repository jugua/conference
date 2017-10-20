
package com.epam.cm.jira;

import java.lang.annotation.*;



@Target(ElementType.METHOD)

@Retention(RetentionPolicy.RUNTIME)
public @interface Jira {
    String [] value();
}
