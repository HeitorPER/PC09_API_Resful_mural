package br.ufscar.dc.dsw.mural.controllers;

import br.ufscar.dc.dsw.mural.dto.ListedMessage;
import br.ufscar.dc.dsw.mural.repositories.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class VerMensagensController {

    private final Logger logger = LoggerFactory.getLogger(VerMensagensController.class);
    private final MessageRepository messageRepository;

    public VerMensagensController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public ResponseEntity<List<ListedMessage>> verMensagens() {
        logger.info("GET /api/mensagens");
        var listedMessages = new ArrayList<ListedMessage>();
        messageRepository.getMessages().forEach(m ->
                listedMessages.add(new ListedMessage(m.getFrom(), m.getTo(), m.getMessage(), m.getTimestamp()))
        );
        return ResponseEntity.ok(listedMessages);
    }
}