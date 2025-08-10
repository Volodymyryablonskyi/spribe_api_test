package com.spribe.yablonskyi.constants;

import com.spribe.yablonskyi.config.ApplicationConfig;

public class Constants {

    public static final String BASE_URI = ApplicationConfig.getBaseUri();
    public static final int THREADS = ApplicationConfig.getThreadPoolSize();

}
