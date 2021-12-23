package me.segomin.school.config

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.httpBasic(Customizer.withDefaults())
            .authorizeRequests{ authz ->
                authz.antMatchers(HttpMethod.GET, "/about").permitAll()
                    .antMatchers(HttpMethod.POST,"/about").hasRole("PRINCIPAL")
                    .antMatchers("/scores/**").hasRole("TEACHER")
                    .anyRequest().authenticated()
            }
        return http.build()
    }
}