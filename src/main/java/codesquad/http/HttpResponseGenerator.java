package codesquad.http;

public class HttpResponseGenerator {

    public HttpResponse sendOK(byte[] body, MediaType mediaType, HttpRequest httpRequest) {
        return generate(StatusCode.OK, body, mediaType, httpRequest);
    }

    public HttpResponse sendNotFound(HttpRequest httpRequest) {
        return generate(StatusCode.NOT_FOUND, "<h1>404 Not Found</h1>".getBytes(), MediaType.TEXT_HTML, httpRequest);
    }

    private HttpResponse generate(StatusCode statusCode, byte[] body, MediaType mediaType, HttpRequest httpRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.addValue("Content-Type", mediaType.getValue());
        return new HttpResponse(statusCode, httpRequest.httpVersion(), httpHeaders, body);
    }

}
