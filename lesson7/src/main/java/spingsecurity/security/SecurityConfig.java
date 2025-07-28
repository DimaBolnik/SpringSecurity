package spingsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().requestMatchers("/h2-console/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Следующий метод необходим для отключения CSRF-токена
        // В REST-приложениях принимающих JSON он не нужен
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // Следующие две строки DSL отключат использование сессий
                // Т.к. у нас REST, то сервер должен быть Stateless
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Это разделяет различные секции Lambda DSL и настройками на url’ы
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/company/**", "/user/**").authenticated()
                                        .requestMatchers("/info").permitAll()
//                                .requestMatchers("/**").denyAll() // по умолчанию все запрешено
                )
                // А данный метод включает дефолтные настройки HTTP Basic аутентификации
                .httpBasic(withDefaults())
                // Возвращает объект SecurityFilterChain
                .build();
    }
}