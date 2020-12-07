package security.employee;

import java.time.LocalDate;

public class Technician  extends Employee {
    public Technician(int id, String name, LocalDate birthDate) {
        super(id, name, birthDate);
    }
}
