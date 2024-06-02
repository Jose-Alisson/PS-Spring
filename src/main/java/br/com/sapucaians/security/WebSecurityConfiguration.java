package br.com.sapucaians.security;

import br.com.sapucaians.detail.AccountDetailService;
import br.com.sapucaians.filter.AccountFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private AccountFilter filter;

    @Autowired
    private AccountDetailService detailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors(cors -> cors.configurationSource(configurationSource())).csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(request -> {
            request.anyRequest().permitAll();
            request.requestMatchers("/api/account/create").permitAll();
            request.requestMatchers("/api/account/login").permitAll();
            request.requestMatchers("/api/account/isExist").permitAll();
            request.requestMatchers("/api/addresses/isDelivery").permitAll();
            request.requestMatchers("/api/product/search").permitAll();
            request.requestMatchers("/api/product/{id}").permitAll();
            request.requestMatchers("/api/product/offset/{offset}").permitAll();

        }).addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).sessionManagement(sManager -> {
            sManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        return http.build();
    }

//    @Bean
//    protected CorsConfigurationSource configurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        var corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        corsConfiguration.setAllowedMethods(List.of("*"));
//
//        source.registerCorsConfiguration("/**", corsConfiguration);
//
//        return source;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(detailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    protected CorsConfigurationSource configurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        var configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");

        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}