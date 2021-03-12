//package com.hacettepe.rehabsoft.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//
//@Configuration
//public class WebSocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
//    @Override
//    protected void configureInbound(final MessageSecurityMetadataSourceRegistry messages) {
//        messages.anyMessage().authenticated()
//                .simpDestMatchers("/socket").hasAnyRole("ROLE_DOCTOR","ROLE_USER")
//        ;
//    }
//
////    @Override
////    protected boolean sameOriginDisabled() {
////        return true;
////    }
//}
