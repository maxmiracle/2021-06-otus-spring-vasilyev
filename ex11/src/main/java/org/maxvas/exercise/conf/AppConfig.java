package org.maxvas.exercise.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class AppConfig {

    @Bean
    public RouterFunction<ServerResponse> list(
            @Value("classpath:/static/list.html") Resource html) {
        return route(GET("/"), request
                -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> edit(
            @Value("classpath:/static//edit.html") Resource html) {
        return route(GET("/edit"), request
                -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> create(
            @Value("classpath:/static//create.html") Resource html) {
        return route(GET("/create"), request
                -> ok().contentType(MediaType.TEXT_HTML).bodyValue(html)
        );
    }
}



