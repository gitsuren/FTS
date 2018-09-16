//package com.suru.fts.actuator.config;
//
//
//import org.springframework.boot.actuate.health.HealthEndpoint;
//import org.springframework.boot.actuate.info.InfoEndpoint;
//import org.springframework.boot.actuate.metrics.MetricsEndpoint;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//
//import java.util.Collection;
//
//
//@Configuration
//@Import({
//        EndpointAutoConfigurat.class,
//        PublicMetricsAutoConfiguration.class,
//        HealthIndicatorAutoConfiguration.class
//})
//public class MyActuatorConfig {
//
//    @Bean
//    public EndpointHandlerMapping endpointHandlerMapping(Collection<? extends MvcEndpoint> endpoints) {
//        return new EndpointHandlerMapping(endpoints);
//    }
//
//    @Bean
//    public EndpointMvcAdapter metricsEndPoint(MetricsEndpoint delegate) {
//        return new EndpointMvcAdapter(delegate);
//    }
//
//    @Bean
//    public EndpointMvcAdapter healthEndPoint(HealthEndpoint delegate) {
//        return new EndpointMvcAdapter(delegate);
//    }
//
//    @Bean
//    public EndpointMvcAdapter infoEndPoint(InfoEndpoint delegate) {
//        return new EndpointMvcAdapter(delegate);
//    }
////
////    @Bean
////    @ConditionalOnMissingBean
////    public MetricsEndpoint metricsEndpoint() {
////        List<PublicMetrics> publicMetrics = new ArrayList<PublicMetrics>();
////        if (this.publicMetrics != null) {
////            for (PublicMetrics pm : this.publicMetrics) {
////                if (!(pm instanceof SystemPublicMetrics)) {
////                    publicMetrics.add(pm);pm
////                }
////            }
////        }
////        Collections.sort(publicMetrics, AnnotationAwareOrderComparator.INSTANCE);
////        return new MetricsEndpoint(publicMetrics);
////    }
//}