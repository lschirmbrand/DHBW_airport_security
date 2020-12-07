package security.employee;

import java.time.LocalDate;

public class Employee {
    protected final int id;
    protected final String name;
    protected final LocalDate birthDate;
    protected IDCard idCard;

    public Employee(int id, String name, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }


    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public IDCard getIdCard() {
        return idCard;
    }

    public void setIdCard(IDCard idCard) {
        this.idCard = idCard;
    }
}
