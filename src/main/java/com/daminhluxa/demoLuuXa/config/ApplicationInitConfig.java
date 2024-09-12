package com.daminhluxa.demoLuuXa.config;

import com.daminhluxa.demoLuuXa.constant.PredefinedRole;
import com.daminhluxa.demoLuuXa.entity.Role;
import com.daminhluxa.demoLuuXa.entity.User;
import com.daminhluxa.demoLuuXa.repository.RoleRepository;
import com.daminhluxa.demoLuuXa.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
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

    @NonFinal
    static final String ADMIN_USER = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";


    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver"
    )
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
           if(userRepository.findByUsername(ADMIN_USER).isEmpty()) {
               roleRepository.save(Role.builder()
                               .name(PredefinedRole.USER_ROLE)
                               .description("User Role")
                       .build());
               Role adminRole = roleRepository.save(Role.builder()
                       .name(PredefinedRole.ADMIN_ROLE)
                       .description("Admin Role")
                       .build());

               var role = new HashSet<Role>();
               role.add(adminRole);
               User user = User.builder()
                       .username(ADMIN_USER)
                       .password(passwordEncoder.encode(ADMIN_PASSWORD))
                       .roles(role)
                       .build();

               userRepository.save(user);
                log.warn("admin user has been created with default password: admin");
           }
        };
    }
}
