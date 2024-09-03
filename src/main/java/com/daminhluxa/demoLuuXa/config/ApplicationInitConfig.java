package com.daminhluxa.demoLuuXa.config;

import com.daminhluxa.demoLuuXa.entity.User;
import com.daminhluxa.demoLuuXa.enums.Role;
import com.daminhluxa.demoLuuXa.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Configuration
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    public static final String ADMIN_USER = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
           if(userRepository.findByUsername(ADMIN_USER).isEmpty()) {
               var role = new HashSet<String>();
               role.add(Role.ADMIN.name());
               User user = User.builder()
                       .username(ADMIN_USER)
                       .password(passwordEncoder.encode("admin"))
//                       .roles(role)
                       .build();

               userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
           }
        };
    }
}
