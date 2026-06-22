package br.ufscar.dc.dsw.mural.controllers;

import br.ufscar.dc.dsw.mural.dto.CadastroForm;
import br.ufscar.dc.dsw.mural.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class CadastroController {

    private static final Logger log = LoggerFactory.getLogger(CadastroController.class);
    private final UserRepository userRepository;

    public CadastroController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody CadastroForm form) {
        log.info("POST /api/usuarios - {}", form.username());
        userRepository.save(form.username(), form.password(), "USER");
        return ResponseEntity.status(201).build();
    }
}