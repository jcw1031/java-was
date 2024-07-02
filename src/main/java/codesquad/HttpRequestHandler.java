package codesquad;

import codesquad.http.HttpRequest;
import codesquad.utils.ResourcesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            log.debug("Client connected");

            String data = ResourcesReader.readResource("static/index.html");
            printHttpRequest();

            try (OutputStream clientOutput = socket.getOutputStream()) {
                clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
                clientOutput.write("Content-Type: text/html\r\n".getBytes());
                clientOutput.write("\r\n".getBytes());
                clientOutput.write(data.getBytes());
                clientOutput.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void printHttpRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder request = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            request.append(line)
                    .append(System.lineSeparator());
        }
        HttpRequest httpRequest = HttpRequest.fromText(request.toString());
        log.debug("httpRequest = {}", httpRequest);
    }

}
