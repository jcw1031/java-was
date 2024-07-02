package codesquad.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class HttpRequest {

    private final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final HttpHeaders requestHeaders;
    private final String requestBody;

    protected HttpRequest(HttpHeaders requestHeaders, String requestBody) {
        this.requestHeaders = requestHeaders;
        this.requestBody = requestBody;
    }

    public static HttpRequest fromText(String request) {
        //TODO 문자열 파싱 후 header와 body 분리
        return null;
    }

    public HttpHeaders headers() {
        return Objects.requireNonNullElseGet(this.requestHeaders, HttpHeaders::empty);
    }

    public List<String> getHeaderValues(String headerName) {
        return requestHeaders.getValues(headerName);
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "requestHeaders=" + requestHeaders +
                ", requestBody='" + requestBody + '\'' +
                '}';
    }

}
