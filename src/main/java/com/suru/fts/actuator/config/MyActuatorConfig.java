package com.suru.fts.actuator.config;

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

import java.util.Collection;


@Configuration
@Import({
        EndpointAutoConfiguration.class, PublicMetricsAutoConfiguration.class, HealthIndicatorAutoConfiguration.class
})
public class MyActuatorConfig {

    @Bean
    public EndpointHandlerMapping endpointHandlerMapping(Collection<? extends MvcEndpoint> endpoints) {
        return new EndpointHandlerMapping(endpoints);
    }

    @Bean
    public EndpointMvcAdapter metricsEndPoint(MetricsEndpoint delegate) {
        return new EndpointMvcAdapter(delegate);
    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public MetricsEndpoint metricsEndpoint() {
//        List<PublicMetrics> publicMetrics = new ArrayList<PublicMetrics>();
//        if (this.publicMetrics != null) {
//            for (PublicMetrics pm : this.publicMetrics) {
//                if (!(pm instanceof SystemPublicMetrics)) {
//                    publicMetrics.add(pm);
//                }
//            }
//        }
//        Collections.sort(publicMetrics, AnnotationAwareOrderComparator.INSTANCE);
//        return new MetricsEndpoint(publicMetrics);
//    }
}