package ru.bolnik.oauth2.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.bolnik.oauth2.backend.security.exception.OAuth2ExceptionHandler;
import ru.bolnik.oauth2.backend.utils.KCRoleConverter;

import java.util.Arrays;

@Configuration // данный класс будет считан как конфиг для spring контейнера
@EnableWebSecurity // включает механизм защиты адресов, которые настраиваются в SecurityFilterChain
@EnableGlobalMethodSecurity(prePostEnabled = true) // включение механизма для защиты методов по ролям

// исключить авто конфигурация для подключения к БД
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})

public class SpringSecurityConfig {

    @Value("${client.url}")
    private String clientURL; // клиентский URL

    // создается спец. бин, который отвечает за настройки запросов по http (метод вызывается автоматически) Spring контейнером
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // конвертер для настройки spring security
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // подключаем конвертер ролей
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KCRoleConverter());


        // все сетевые настройки
        http.authorizeRequests()
                .antMatchers("/admin/*").hasRole("admin") // CRUD для работы с пользователями
                .antMatchers("/user/*").hasRole("user") // действия самого пользователям (регистрация и пр.)
                .anyRequest().authenticated() // остальной API будет доступен только аутентифицированным пользователям
                .and()

                .csrf().disable() // отключаем встроенную защиту от CSRF атак, т.к. используем свою, из OAUTH2
                .cors()// разрешает выполнять OPTIONS запросы от клиента (preflight запросы) без авторизации
                .and()


                // добавляем новые настройки, не связанные с предыдущими

                .oauth2ResourceServer() // включаем защиту OAuth2 для данного backend
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter) // добавляем конвертер ролей из JWT в Authority (Role)

                .and()

                .authenticationEntryPoint(new OAuth2ExceptionHandler());

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(clientURL));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
