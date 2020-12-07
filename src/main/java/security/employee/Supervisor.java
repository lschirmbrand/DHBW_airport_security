package security.employee;

import security.scanner.BaggageScanner;
import security.scanner.Supervision;

import java.time.LocalDate;

public class Supervisor extends Employee {
    private final boolean isSenior;
    private final boolean isExecutive;

    private final Supervision supervision;

    public Supervisor(int id, String name, LocalDate birthDate, boolean isSenior, boolean isExecutive, BaggageScanner scanner) {
        super(id, name, birthDate);
        this.isSenior = isSenior;
        this.isExecutive = isExecutive;
        this.supervision = scanner.getSupervision();
    }

    public void unlock() {
        supervision.unlock();
    }

    public void pressPowerBtn() {
        supervision.pressPowerBtn();
    }

    public void swipeIDCard() {
        supervision.readCard(idCard);
    }

    public void enterPIN(String pin) {
        supervision.validatePin(pin);
    }

    public boolean isSenior() {
        return isSenior;
    }

    public boolean isExecutive() {
        return isExecutive;
    }
}
