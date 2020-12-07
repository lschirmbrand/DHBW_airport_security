package security.employee;

import java.time.LocalDate;

public class Inspector extends Employee {
    private final boolean isSenior;

    public Inspector(int id, String name, LocalDate birthDate, boolean isSenior) {
        super(id, name, birthDate);
        this.isSenior = isSenior;
    }
}
