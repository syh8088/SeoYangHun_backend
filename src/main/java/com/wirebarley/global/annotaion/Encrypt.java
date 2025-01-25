package com.wirebarley.global.annotaion;

import com.wirebarley.global.model.enums.EncryptType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Encrypt {
    EncryptType type() default EncryptType.PASSWORD;
}
