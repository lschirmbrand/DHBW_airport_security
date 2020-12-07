package security.scanner;

import security.employee.InspectorRollerConveyor;

import java.util.ArrayDeque;
import java.util.Queue;

public class RollerConveyor {
    private final Queue<Tray> trays;

    InspectorRollerConveyor inspector;

    public RollerConveyor() {
        trays = new ArrayDeque<>();
    }

    public Tray removeFirst() {
        return trays.poll();
    }

    public void add(Tray tray) {
        trays.add(tray);
    }

    public Queue<Tray> getTrays() {
        return trays;
    }

    public InspectorRollerConveyor getInspector() {
        return inspector;
    }

    public void setInspector(InspectorRollerConveyor inspector) {
        this.inspector = inspector;
    }
}
