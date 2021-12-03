import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MoexLogin {
    private static final String DOWNLOAD_LINK = "https://iss.moex.com/user-download/private/BONDS/OrderLog20211101.zip";

    public static void main(String[] args) throws URISyntaxException, IOException {
        if (args.length != 2) {
            throw new IllegalStateException("Email and password should be provided as arguments");
        }
        LoginService loginService = new LoginService();
        LoginData data = loginService.login(new LoginForm(args[0], args[1]));
        Connection.Response resp = Jsoup.connect(DOWNLOAD_LINK)
                .method(Connection.Method.GET)
                .cookies(data.getCookies())
                .ignoreContentType(true)
                .maxBodySize(0)
                .execute();

        System.out.println("Downloading file from " + DOWNLOAD_LINK + ". File size: " + resp.header("Content-Length") 
                + ". File type: " + resp.header("Content-Type"));
        String path = resp.url().getPath();
        String fileName = path.substring(path.lastIndexOf('/') + 1);
        final Path filePath = Paths.get(fileName);
        Files.write(filePath, resp.bodyAsBytes(), StandardOpenOption.CREATE);
        System.out.println("Files saved to " + filePath.toAbsolutePath().getFileName());
    }
}
