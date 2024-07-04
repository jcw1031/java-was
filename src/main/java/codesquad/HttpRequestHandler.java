package codesquad;

import codesquad.http.HttpRequest;
import codesquad.http.parser.HttpHeadersParser;
import codesquad.http.parser.HttpRequestParser;
import codesquad.http.MediaType;
import codesquad.http.parser.QueryParametersParser;
import codesquad.resource.Resource;
import codesquad.resource.ResourcesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Optional;

public class HttpRequestHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final Socket socket;
    private final HttpHeadersParser headersParser = new HttpHeadersParser();
    private final QueryParametersParser queryParametersParser = new QueryParametersParser();
    private final HttpRequestParser requestParser = new HttpRequestParser(headersParser, queryParametersParser);

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
        log.debug("Client connected");
    }

    @Override
    public void run() {
        try {
            HttpRequest httpRequest = requestParser.parse(readHttpRequest());
            log.debug("httpRequest = {}", httpRequest);

            Optional<Resource> readResource = ResourcesReader.readResource("static" + httpRequest.uri());
            try (OutputStream clientOutput = socket.getOutputStream()) {
                readResource.ifPresentOrElse(resource -> sendOK(clientOutput, resource),
                        () -> sendNotFound(clientOutput));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void sendOK(OutputStream clientOutput, Resource resource) {
        String resourceExtension = resource.getExtension();
        MediaType mediaType = MediaType.find(resourceExtension)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 지원하지 않는 파일 형식입니다."));

        try {
            clientOutput.write(("HTTP/1.1 200 OK" + "\r\n").getBytes());
            clientOutput.write(("Content-Length: " + resource.getContentLength() + "\r\n").getBytes());
            clientOutput.write(("Content-Type: " + mediaType.getValue() + "\r\n").getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write(resource.getContent());
            clientOutput.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void sendNotFound(OutputStream clientOutput) {
        try {
            clientOutput.write(("HTTP/1.1 404 Not Found" + "\r\n").getBytes());
            clientOutput.write(("Content-Type: text/html" + "\r\n").getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("<h1>404 Not Found</h1>".getBytes());
            clientOutput.flush();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private String readHttpRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder request = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            request.append(line)
                    .append("\r\n");
        }
        return request.toString();
    }

}
