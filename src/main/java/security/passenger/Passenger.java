package security.passenger;

import security.scanner.BaggageScanner;
import security.scanner.Tray;

public class Passenger {
    private final String name;
    private final HandBaggage[] handBaggages;

    private final BaggageScanner scanner;

    public Passenger(String name, HandBaggage[] handBaggages, BaggageScanner scanner) {
        this.name = name;
        this.handBaggages = handBaggages;
        this.scanner = scanner;
    }

    public String getName() {
        return name;
    }

    public HandBaggage[] getHandBaggages() {
        return handBaggages;
    }

    public Tray takeTray() {
        return scanner.getTrayProvider().take();
    }

    public void putInTray(Tray tray, HandBaggage baggage) {
        tray.setBaggage(baggage);
    }

    public void layOnConveyor(Tray tray) {
        scanner.getRollerConveyor().add(tray);
    }
}
