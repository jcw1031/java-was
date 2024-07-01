package codesquad;

import codesquad.utils.ResourcesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class HttpRequestHandler implements Runnable {

    private final Logger log = LoggerFactory.getLogger(HttpRequestHandler.class);

    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void handle() throws IOException {
        log.debug("Client connected");

        String data = ResourcesReader.readResource("static/index.html");
        try {
            OutputStream clientOutput = socket.getOutputStream();
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("Content-Type: text/html\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write(data.getBytes());
            clientOutput.flush();
            clientOutput.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            socket.close();
        }
    }

}
