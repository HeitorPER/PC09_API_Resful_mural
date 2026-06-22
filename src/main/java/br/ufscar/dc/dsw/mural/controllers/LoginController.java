package br.ufscar.dc.dsw.mural.controllers;

import br.ufscar.dc.dsw.mural.config.JwtService;
import br.ufscar.dc.dsw.mural.dto.LoginRequestForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestForm form) {
        log.info("POST /api/auth/login - {}", form.username());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(form.username(), form.password()));
        return ResponseEntity.ok(jwtService.generateToken(form.username()));
    }
}
