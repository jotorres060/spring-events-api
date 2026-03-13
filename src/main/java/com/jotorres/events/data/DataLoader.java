package com.jotorres.events.data;

import com.jotorres.events.domain.Category;
import com.jotorres.events.domain.Role;
import com.jotorres.events.domain.Speaker;
import com.jotorres.events.domain.User;
import com.jotorres.events.repository.CategoryRepository;
import com.jotorres.events.repository.RoleRepository;
import com.jotorres.events.repository.SpeakerRepository;
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
    private final CategoryRepository categoryRepository;
    private final SpeakerRepository speakerRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        this.createUserData();
        this.createCategoryData();
        this.createSpeakerData();
    }

    private void createUserData() {
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

    private void createCategoryData() {
        if (!this.categoryRepository.existsByName("AI")) {
            Category ai = new Category(null, "AI", "Artificial Intelligence");
            this.categoryRepository.save(ai);
        }

        if (!this.categoryRepository.existsByName("Software Architecture")) {
            Category softwareArch = new Category(null, "Software Architecture", "Cloud Software Architecture");
            this.categoryRepository.save(softwareArch);
        }

        if (!this.categoryRepository.existsByName("Web Development")) {
            Category webDevelopment = new Category(null, "Web Development", "Modern Web Development");
            this.categoryRepository.save(webDevelopment);
        }
    }

    private void createSpeakerData() {
        if (!this.speakerRepository.existsByEmail("john.doe@example.com")) {
            Speaker johnDoe = new Speaker(null, "John Doe", "john.doe@example.com", "A cool person", new HashSet<>());
            this.speakerRepository.save(johnDoe);
        }

        if (!this.speakerRepository.existsByEmail("jane.smith@example.com")) {
            Speaker janeSmith = new Speaker(null, "Jane Smith", "jane.smith@example.com", "A creative person", new HashSet<>());
            this.speakerRepository.save(janeSmith);
        }
    }
}
