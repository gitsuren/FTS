package com.suru.fts.actuator.config;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class MyInfoContributor implements InfoContributor{

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("FTS_System",
                Collections.singletonMap("version", "0.0.1"));
    }

}
