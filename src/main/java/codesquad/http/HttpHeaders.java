package codesquad.http;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HttpHeaders {

    private final Map<String, List<String>> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public static HttpHeaders empty() {
        return new HttpHeaders();
    }

    public List<String> getValues(String name) {
        if (name == null) {
            return Collections.emptyList();
        }

        List<String> values = this.headers.getOrDefault(name, Collections.emptyList());
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers +
                '}';
    }

}
