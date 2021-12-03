public class LoginForm {
    public String getCredentials() {
        return credentials;
    }

    public String getPassword() {
        return password;
    }

    private final String credentials;
    private final String password;
    public LoginForm(String credentials, String password) {
        this.credentials = credentials;
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("LoginForm{");
        sb.append(", credentials='").append(credentials).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
