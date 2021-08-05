package lt.addendum.job.interview.task.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lt.addendum.job.interview.task.constants.AuthenticationConstants;
import lt.addendum.job.interview.task.exceptions.HttpErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableEncryptableProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String APPLICATION_PROBLEM = "application/problem-json";
    private static final String AUTH = "Auth";

    @Value("${api.auth.activate.quality}")
    private String path;

    @Value("${api.auth.credentials.user}")
    private String userName;

    @Value("${api.auth.credentials.password}")
    private String password;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(path).authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    var httpErrorResponse = new HttpErrorResponse(AUTH, "auth-002",
                            AuthenticationConstants.MISSING_CREDENTIALS,
                            AuthenticationConstants.MISSING_CREDENTIALS_MSG);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(APPLICATION_PROBLEM);
                    response.getOutputStream().println(objectMapper.writeValueAsString(httpErrorResponse));
                })
                .and()
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    var httpErrorResponse = new HttpErrorResponse(AUTH, "auth-001",
                            AuthenticationConstants.INCORRECT_CREDENTIALS,
                            AuthenticationConstants.INCORRECT_CREDENTIALS_MSG);
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType(APPLICATION_PROBLEM);
                    response.getOutputStream().println(objectMapper.writeValueAsString(httpErrorResponse));
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(userName).password(passwordEncoder().encode(password)).authorities(ADMIN_ROLE);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
