package nl.fontys.micro;

public class Account {

    private final int id;
    private final String username;
    private final String encryptedPassword;

    public Account(int id, String username) {
        this(id, username, null);
    }

    public Account(int id, String username, String encryptedPassword) {
        this.id = id;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}
