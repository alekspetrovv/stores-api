package com.jumbo.stores.service;


import com.jumbo.stores.dto.LoginUserDto;
import com.jumbo.stores.dto.ReadLoginDto;
import com.jumbo.stores.dto.RegisterUserDto;
import com.jumbo.stores.entity.User;
import com.jumbo.stores.exception.UserAlreadyExistsException;
import com.jumbo.stores.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public void register(RegisterUserDto registerUserDto) {
        if (repository.findByUsername(registerUserDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already in use.");
        }

        User user = modelMapper.map(registerUserDto, User.class);
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        repository.save(user);
    }

    public ReadLoginDto login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );

        User user = repository.findByUsername(loginUserDto.getUsername())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return new ReadLoginDto(token);
    }
}