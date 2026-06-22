package br.ufscar.dc.dsw.mural.controllers;

import br.ufscar.dc.dsw.mural.dto.ListedMessage;
import br.ufscar.dc.dsw.mural.dto.SendMessageForm;
import br.ufscar.dc.dsw.mural.entities.Message;
import br.ufscar.dc.dsw.mural.repositories.MessageRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mensagens")
public class PostarController {

    private static final Logger logger = LoggerFactory.getLogger(PostarController.class);
    private final MessageRepository messageRepository;

    public PostarController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @PostMapping
    public ResponseEntity<ListedMessage> post(
            @RequestBody @Valid SendMessageForm form,
            Authentication authentication) {
        logger.info("POST /api/mensagens - {}", form);

        String from = authentication.getName();

        if (from.equals(form.getTo())) {
            return ResponseEntity.badRequest().build();
        }

        var message = new Message();
        message.setFrom(from);
        message.setTo(form.getTo());
        message.setMessage(form.getMessage());
        messageRepository.save(message);

        return ResponseEntity.ok(new ListedMessage(
                message.getFrom(),
                message.getTo(),
                message.getMessage(),
                message.getTimestamp()
        ));
    }
}
