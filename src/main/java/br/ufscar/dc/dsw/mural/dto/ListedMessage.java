package br.ufscar.dc.dsw.mural.dto;

public record ListedMessage(
        String from,
        String to,
        String message,
        String timestamp
) {}