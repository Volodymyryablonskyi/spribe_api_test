package com.spribe.yablonskyi.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryTransformer implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation a, Class c, Constructor k, Method m) {
        if (a.getRetryAnalyzerClass() == null) {
            a.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }

}
