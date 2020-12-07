package security.scanner;

import security.employee.ProfileType;

public class WrongProfileTypeException extends RuntimeException {
    public WrongProfileTypeException(ProfileType type, String function) {
        super("ProfileType " + type.name() + " is not allowed to perform " + function);
    }
}
