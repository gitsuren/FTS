//package com.suru.fts.controller;
//
//import com.suru.fts.actuator.config.CustomEndpoint;
//import com.suru.fts.actuator.config.ListEndPoints;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.endpoint.Endpoint;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class ActuatorController {
//
//    @Autowired
//    ListEndPoints listEndPoints;
//
//    @Autowired
//    CustomEndpoint customEndpoint;
//
//    @GetMapping(value = "listEndPoints")
//    public List<Endpoint> listEndPoints() {
//        return listEndPoints.invoke();
//    }
//
//    @GetMapping(value = "customEndpoint")
//    public List<String> customEndpoint() {
//        return customEndpoint.invoke();
//    }
//
//}
