package security.employee;

import security.scanner.BaggageScanner;
import security.scanner.Belt;
import security.scanner.RollerConveyor;
import security.scanner.Tray;

import java.time.LocalDate;

public class InspectorRollerConveyor extends Inspector {
    private final RollerConveyor rollerConveyor;
    private final Belt belt;

    public InspectorRollerConveyor(int id, String name, LocalDate birthDate, boolean isSenior, BaggageScanner scanner) {
        super(id, name, birthDate, isSenior);
        this.rollerConveyor = scanner.getRollerConveyor();
        this.belt = scanner.getBelt();
    }

    public void push() {

        Tray t = rollerConveyor.removeFirst();
        belt.setBeforeScanner(t);
    }
}
