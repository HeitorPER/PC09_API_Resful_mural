package br.ufscar.dc.dsw.mural.dto;

import jakarta.validation.constraints.NotBlank;

public class SendMessageForm {
    @NotBlank
    private String to;
    @NotBlank
    private String message;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("SendMessageForm[to='%s', message='%s']", to, message);
    }
}
