package com.trade.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class AppConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//      here we need to specify which end point need to be protected and which one wide listed
        http.authorizeHttpRequests(Authorize-> Authorize.requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
//                here add filter
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())

                //why this cors config ? whenever
                // we connect frontend with backend brower will throw error to avoid this error we need to provide this configurationSource
//                if other domain or website try to acess our backend then throw error(configurationSource)
                .cors(cors -> cors.configurationSource(configurationSource()));

        return http.build();
    }


    private CorsConfigurationSource configurationSource() {
        return null;
    }

}
