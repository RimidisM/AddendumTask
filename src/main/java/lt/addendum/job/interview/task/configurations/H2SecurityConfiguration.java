//package lt.addendum.job.interview.task.configurations;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Profile("local") // to make sure it is active only if h2 profile is active
//@Configuration
//@ConditionalOnProperty( //to make sure it is active if console is enabled
//        value="spring.h2.console.enabled",
//        havingValue = "true",
//        matchIfMissing = false)
//@Order(1)
//public class H2SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // this may not be required, depends on your app configuration
//        http.authorizeRequests()
//                // we need config just for console, nothing else
//                .antMatchers("/h2_console/**").permitAll();
//        // this will ignore only h2-console csrf, spring security 4+
//        http.csrf().ignoringAntMatchers("/h2-console/**");
//        //this will allow frames with same origin which is much more safe
//        http.headers().frameOptions().sameOriginin();
//    }
//}