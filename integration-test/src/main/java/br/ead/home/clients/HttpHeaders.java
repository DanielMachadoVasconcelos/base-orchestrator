package br.ead.home.clients;

public final class HttpHeaders {

    public static final String CORRELATION_ID = "Correlation-Id";
    public static final String IDEMPOTENCY_KEY = "Idempotency-Key";
    public static final String ORIGINATOR_SOURCE = "Originator-Source";
    public static final String ORIGINATOR_USER = "Originator-User";
    public static final String CLIENT_TIMEOUT = "Client-Timeout";
    public static final String REQUEST_TIMEOUT = "Request-Timeout";

    public static final String UID_HEADER = "uid";

    private HttpHeaders() {
    }
}
