package br.ead.home.clients;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

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

    public static String createBasicAuthHeader(String userName, String password) {
        if (StringUtils.isEmpty(userName)) {
            return null;
        }
        String auth = userName + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

}
