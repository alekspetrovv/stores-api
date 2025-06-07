package com.jumbo.stores.controller;

import com.jumbo.stores.dto.LoginUserDto;
import com.jumbo.stores.dto.ReadLoginDto;
import com.jumbo.stores.dto.RegisterUserDto;
import com.jumbo.stores.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        authenticationService.register(registerUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ReadLoginDto> login(@Valid @RequestBody LoginUserDto loginUserDto) {
        return new ResponseEntity<>(authenticationService.login(loginUserDto), HttpStatus.OK);
    }
}
