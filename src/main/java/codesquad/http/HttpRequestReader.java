package codesquad.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class HttpRequestReader {

    private static HttpRequestReader instance;

    private HttpRequestReader() {
    }

    public static HttpRequestReader getInstance() {
        if (instance == null) {
            instance = new HttpRequestReader();
        }
        return instance;
    }

    public String readHeader(InputStream inputStream) throws IOException {
        StringBuilder headerText = new StringBuilder();
        while (true) {
            String line = readLine(inputStream);
            headerText.append(line)
                    .append("\r\n");
            if (line.isEmpty()) {
                return headerText.toString();
            }
        }
    }

    private String readLine(InputStream inputStream) throws IOException {
        ByteArrayOutputStream lineOutputStream = new ByteArrayOutputStream();
        int currentByte;
        while ((currentByte = inputStream.read()) != -1) {
            if (currentByte == '\r' && inputStream.read() == '\n') {
                break;
            }
            lineOutputStream.write(currentByte);
        }
        return lineOutputStream.toString();

    }

    public byte[] readBody(InputStream inputStream, int contentLength) throws IOException {
        byte[] body = new byte[contentLength];
        inputStream.readNBytes(body, 0, contentLength);
        return body;
    }
}
