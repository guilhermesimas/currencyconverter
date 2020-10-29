package com.cortex.currencyconverter.clients.bacen;

import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@Slf4j
@Profile({"!production"})
@RestController
@RibbonClient(value = "bacen", configuration = BacenMockClient.RibbonConfig.class)
public class BacenMockClient {

    @Profile({"!production"})
    @Configuration
    protected static class RibbonConfig {

        @Value("${server.port}")
        int port;

        @Bean
        public ServerList<Server> serverList() {
            log.info("Initializing static server list");
            return new StaticServerList<>(new Server("localhost", port));
        }

    }

    @GetMapping(value="/bc_moeda/rest/converter/{amount}/1/{from}/{to}/{when}")
    ResponseEntity<String> convert(@PathVariable("amount") Float amount,
                                   @PathVariable("from") Integer from,
                                   @PathVariable( "to") Integer to,
                                   @PathVariable("when") String when){
        return ResponseEntity.ok("OK");
    }
}
