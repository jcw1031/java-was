package codesquad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        log.debug("Listening for connection on port 8080 ....");

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                log.debug("Client connected");

                OutputStream clientOutput = clientSocket.getOutputStream();
                clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
                clientOutput.write("Content-Type: text/html\r\n".getBytes());
                clientOutput.write("\r\n".getBytes());
                clientOutput.write("<h1>Hello</h1>\r\n".getBytes());
                clientOutput.flush();
            }
        }
    }

}
