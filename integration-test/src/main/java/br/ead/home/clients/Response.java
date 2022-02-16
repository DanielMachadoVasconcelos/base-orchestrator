package br.ead.home.clients;

import java.util.Map;

public record Response(int statusCode, String body, Map<String, String> headers) {
}
