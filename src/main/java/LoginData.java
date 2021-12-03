import java.util.Map;

public class LoginData {
    private final Map<String, String> cookies;

    public Map<String, String> getCookies() {
        return cookies;
    }

    public LoginData(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginData{");
        sb.append("cookies=").append(cookies);
        sb.append('}');
        return sb.toString();
    }
}
