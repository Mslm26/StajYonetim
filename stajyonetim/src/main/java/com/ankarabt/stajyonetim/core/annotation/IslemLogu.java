package com.ankarabt.stajyonetim.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
// Sadece program çalışırken.
@Retention(RetentionPolicy.RUNTIME)
public @interface IslemLogu {

    String islemAdi();
}