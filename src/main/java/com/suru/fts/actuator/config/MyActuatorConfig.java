package com.suru.fts.actuator.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.PublicMetricsAutoConfiguration;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.EndpointHandlerMapping;
import org.springframework.boot.actuate.endpoint.mvc.EndpointMvcAdapter;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Collection;

//@Component
@Configuration
//@EnableWebMvc
@Import({
        EndpointAutoConfiguration.class, PublicMetricsAutoConfiguration.class, HealthIndicatorAutoConfiguration.class
})

//@Import({ApplicationContext.class, EndpointWebMvcAutoConfiguration.class,
//        ManagementServerPropertiesAutoConfiguration.class, EndpointAutoConfiguration.class,
//        HealthIndicatorAutoConfiguration.class})
public class MyActuatorConfig {

    @Bean
    //@Autowired
    public EndpointHandlerMapping endpointHandlerMapping(Collection<? extends MvcEndpoint> endpoints) {
        return new EndpointHandlerMapping(endpoints);
    }

    @Bean
    //@Autowired
    public EndpointMvcAdapter metricsEndPoint(MetricsEndpoint delegate) {
        return new EndpointMvcAdapter(delegate);
    }
}