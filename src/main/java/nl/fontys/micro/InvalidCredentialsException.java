package nl.fontys.micro;

public class InvalidCredentialsException extends Exception {

    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}
