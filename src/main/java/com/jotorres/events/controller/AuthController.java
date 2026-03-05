package com.jotorres.events.controller;

import com.jotorres.events.domain.Role;
import com.jotorres.events.domain.User;
import com.jotorres.events.dto.JwtAuthResponseDto;
import com.jotorres.events.dto.LoginDto;
import com.jotorres.events.dto.RegisterDto;
import com.jotorres.events.mapper.UserMapper;
import com.jotorres.events.repository.RoleRepository;
import com.jotorres.events.repository.UserRepository;
import com.jotorres.events.security.jwt.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new JwtAuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (this.userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
        }

        if (this.userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email already exists.", HttpStatus.BAD_REQUEST);
        }

        Role roles = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("User role does not exist."));

        User user = this.userMapper.registerDtoToUser(registerDto);
        user.setPassword(this.passwordEncoder.encode(registerDto.getPassword()));
        user.setRoles(Collections.singleton(roles));
        this.userRepository.save(user);

        return new ResponseEntity<>("User registered.", HttpStatus.CREATED);
    }
}
