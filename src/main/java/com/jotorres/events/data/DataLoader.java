package com.jotorres.events.data;

import com.jotorres.events.domain.Role;
import com.jotorres.events.domain.User;
import com.jotorres.events.repository.RoleRepository;
import com.jotorres.events.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        Role adminRole = this.roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> {
                   Role newRole = new Role();
                   newRole.setName("ROLE_ADMIN");
                   return this.roleRepository.save(newRole);
                });

        Role userRole = this.roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName("ROLE_USER");
                    return this.roleRepository.save(newRole);
                });

        if (this.userRepository.findByUsername("admin").isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);

            User user = new User();
            user.setName("Administrator");
            user.setUsername("admin");
            user.setEmail("admin@example.com");
            user.setPassword(this.passwordEncoder.encode("secret.123"));
            user.setRoles(roles);

            this.userRepository.save(user);

            System.out.println("Admin user created.");
        }

        if (this.userRepository.findByUsername("torres").isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);

            User user = new User();
            user.setName("Torres");
            user.setUsername("torres");
            user.setEmail("torres@example.com");
            user.setPassword(this.passwordEncoder.encode("secret.456"));
            user.setRoles(roles);

            this.userRepository.save(user);

            System.out.println("Torres user created.");
        }
    }
}
