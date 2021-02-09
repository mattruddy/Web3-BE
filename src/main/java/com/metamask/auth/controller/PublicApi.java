package com.metamask.auth.controller;

import com.metamask.auth.model.dto.AuthTokenResponse;
import com.metamask.auth.model.dto.LoginRequest;
import com.metamask.auth.model.dto.PubUserResp;
import com.metamask.auth.model.dto.SignUpRequest;
import com.metamask.auth.service.AuthService;
import com.metamask.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class PublicApi {

    @Autowired private AuthService authService;
    @Autowired private UserService userService;

    // GET
    @GetMapping(value = "/user/{publicAddress}")
    public PubUserResp checkUserExists(@PathVariable("publicAddress") String publicAddress) {
        return userService.getUserNonce(publicAddress);
    }

    // POST
    @PostMapping(value = "/signup")
    public PubUserResp signup(@RequestBody SignUpRequest signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PostMapping(value = "/login")
    public AuthTokenResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
