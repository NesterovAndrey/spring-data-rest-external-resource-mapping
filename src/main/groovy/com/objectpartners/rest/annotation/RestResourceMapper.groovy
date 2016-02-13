package com.objectpartners.rest.annotation

import com.objectpartners.rest.resource.RestResourceContext

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target([ElementType.FIELD])
@Retention(RetentionPolicy.RUNTIME)
public @interface RestResourceMapper {
    boolean external() default false
    RestResourceContext context() default RestResourceContext.LOCAL
    String path() default ""
    String[] params() default []
    String apiKey() default ""
    String resolveToProperty() default ""
}
