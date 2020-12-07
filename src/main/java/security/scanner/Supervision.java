package security.scanner;

import security.employee.IDCard;
import security.employee.ProfileType;
import security.employee.Supervisor;

public class Supervision {

    private final BaggageScanner scanner;
    private Supervisor supervisor;
    private final IDCardReader reader;
    private ProfileType authenticatedProfileType;

    public Supervision(BaggageScanner scanner) {
        reader = new IDCardReader();
        this.scanner = scanner;
        this.authenticatedProfileType = ProfileType.NONE;
    }

    public void pressPowerBtn() {
        if (scanner.getState() == BaggageScannerState.SHUTDOWN) {
            scanner.start(authenticatedProfileType);
        } else {
            scanner.shutdown(authenticatedProfileType);
        }
    }

    public void unlock() {
        scanner.unlock(authenticatedProfileType);
    }

    public void readCard(IDCard card) {
        reader.readCard(card);
    }

    public void validatePin(String pin) {
        authenticatedProfileType = reader.validatePIN(pin);
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
}
