package security.scanner;

public class UnknownProfileTypeException extends RuntimeException {
    public UnknownProfileTypeException(char profileChar) {
        super("Unknown ProfileType: " + profileChar);
    }
}
