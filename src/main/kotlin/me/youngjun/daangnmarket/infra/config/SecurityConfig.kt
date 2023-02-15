package me.youngjun.daangnmarket.infra.config

import me.youngjun.daangnmarket.infra.exception.JwtAccessDeniedHandler
import me.youngjun.daangnmarket.infra.exception.JwtAuthenticationEntryPoint
import me.youngjun.daangnmarket.infra.jwt.JwtAuthenticationFilter
import me.youngjun.daangnmarket.infra.jwt.JwtTokenProvider
import me.youngjun.daangnmarket.infra.jwt.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val userDetailsService: UserDetailsServiceImpl,
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .httpBasic().disable() // REST API 이므로 기본 설정 안함 (기본 설정은 비인증 시 login form 으로 이동)
            .csrf().disable() // REST API 이므로 csrf 보안 필요 없음
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JTW 기반이기 때문에 세션 필요 없음
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)
            .and()
            .authorizeRequests() // Request 권한
            .antMatchers(
                "/api/v1/member",
                "/api/v1/login",
                "/api/v1/area/list",
                "/h2-console/**",
                "/favicon.ico"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .headers().frameOptions().sameOrigin() // for h2 i-frame
            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManagerBean(http: HttpSecurity): AuthenticationManager? {
        val authenticationManagerBuilder = http.getSharedObject(
            AuthenticationManagerBuilder::class.java
        )
        authenticationManagerBuilder.userDetailsService<UserDetailsService>(userDetailsService)
            .passwordEncoder(passwordEncoder())
        return authenticationManagerBuilder.build()
    }

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}
