package codesquad.http;

public record HttpCookie(String name, String value, String path) {

    public String toCookieString() {
        return name + "=" + value + "; Path=" + path;
    }

}
