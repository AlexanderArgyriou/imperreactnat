package com.argyriou.imperreactnat.netty;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import reactor.netty.resources.LoopResources;

/**
 * Configure a Single thread event loop which kills threads upon jvm exit daemon=true
 * This refers to the pool netty uses in order to handle incoming requests
 * <p>
 * Do not try this at home :P just for demonstration purposes.
 * Threads should be equal to the number of physical cores
 */
@Configuration
public class EventLoopConfig implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {
    @Override
    public void customize(NettyReactiveWebServerFactory factory) {
        factory.addServerCustomizers(httpServer -> httpServer.tcpConfiguration(tcp ->
                tcp.runOn(LoopResources.create("custom-event-loop", 1, true))));
    }
}