package com.zinebbendhiba;

import org.apache.camel.builder.RouteBuilder;

public class TimerRouteBuilder extends RouteBuilder {
    /**
     * Let's configure the Camel routing rules using Java code...
     */
    @Override
    public void configure() throws Exception {
        from("timer:timerTest?period=1000")
                .log("Hello World Camel Timer!");
    }
}
