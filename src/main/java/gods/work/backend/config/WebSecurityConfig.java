package gods.work.backend.config;

import gods.work.backend.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    private final String[] allowedUrls = {"/", "/swagger-ui/**", "/api/login", "/api/signup", "/api/token"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HttpSecurity httpSecurity) throws Exception {
        http.csrf().disable();

        http.headers().frameOptions().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/api/token", "/api/login", "/api/trainer/signup").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

//        http.authorizeRequests()
//                .requestMatchers("/api/login").permitAll()
//                .requestMatchers("/api/signup").permitAll()
//                .requestMatchers(PathRequest.toH2Console()).permitAll()
//                .anyRequest().authenticated()
//                .and().addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
