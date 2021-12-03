import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private static final String LOGIN_URL = "https://passport.moex.com/login";
    private static final String CERT_COOKIE = "MicexPassportCert";

    LoginData login(LoginForm loginForm) throws IOException {
        Connection.Response resp = Jsoup.connect(LOGIN_URL)
                .method(Connection.Method.GET)
                .execute();
        Document doc = resp.parse();
        Element form = doc.selectFirst("form");
        String authToken = form.select("[name=\"authenticity_token\"]").attr("value");

        Connection.Response loginResp = Jsoup.connect(LOGIN_URL)
                .method(Connection.Method.POST)
                .data("authenticity_token", authToken)
                .data("user[credentials]", loginForm.getCredentials())
                .data("user[password]", loginForm.getPassword())
                .header("Content-Type", "application/x-www-form-urlencoded")
                .cookies(resp.cookies())
                .followRedirects(false)
                .execute();
        final String cert = loginResp.headers("Set-Cookie")
                .stream()
                .filter(cookie -> cookie.startsWith(CERT_COOKIE))
                .map(cookie -> cookie.split(";")[0].split("="))
                .filter(parts -> parts.length > 1)
                .findFirst()
                .map(parts -> parts[1])
                .orElseThrow(IllegalStateException::new);
        Map<String, String> cookies = new HashMap<>(loginResp.cookies());
        cookies.put(CERT_COOKIE, cert);
        return new LoginData(cookies);
    }
}
