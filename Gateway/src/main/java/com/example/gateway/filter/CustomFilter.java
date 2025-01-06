package com.example.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
   import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
   import org.springframework.http.server.reactive.ServerHttpRequest;
   import org.springframework.stereotype.Component;

   @Component
   public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

       public CustomFilter() {
           super(Config.class);
       }

       @Override
       public GatewayFilter apply(Config config) {
           return (exchange, chain) -> {
               ServerHttpRequest request = exchange.getRequest().mutate()
                       .header("X-Custom-Header", config.getHeader()).build();

               return chain.filter(exchange.mutate().request(request).build());
           };
       }

       public static class Config {
           private String header;

           public String getHeader() {
               return header;
           }

           public void setHeader(String header) {
               this.header = header;
           }
       }
   }
   