package security.police;

import security.employee.Employee;
import security.passenger.HandBaggage;
import security.passenger.Passenger;
import security.prohibitetItems.Weapon;
import security.scanner.BaggageScanner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FederalPoliceOfficer extends Employee {
    private final String grade;

    private final FederalPoliceOffice office;

    private final BaggageScanner scanner;

    private final List<HandBaggage> baggages;
    private final List<Passenger> arrestedPassengers;
    private final List<Weapon> confiscatedWeapons;

    public FederalPoliceOfficer(int id, String name, LocalDate birthDate, String grade, FederalPoliceOffice office, BaggageScanner scanner) {
        super(id, name, birthDate);
        this.grade = grade;
        this.office = office;
        baggages = new ArrayList<>();
        arrestedPassengers = new ArrayList<>();
        confiscatedWeapons = new ArrayList<>();

        this.scanner = scanner;
    }

    public void arrest(Passenger passenger) {
        arrestedPassengers.add(passenger);
    }

    public void leave() {
        scanner.officerLeaving(this);
    }

    public Weapon openAndRemoveWeapon(HandBaggage baggage, int pos) {
        String weaponStr = "glock|7";
        int layerIndex = pos / 10000;
        pos %= 10000;
        String[] content = baggage.getContent();

        String layer = content[layerIndex];

        layer = layer.substring(0, pos) + "-".repeat(weaponStr.length()) + layer.substring(pos + weaponStr.length());

        content[layerIndex] = layer;

        return new Weapon();
    }

    public void handOverWeapon(FederalPoliceOfficer o3, Weapon weapon) {
        o3.takeWeapon(weapon);
    }

    public void takeWeapon(Weapon weapon) {
        confiscatedWeapons.add(weapon);
    }

    public void takeBaggage(HandBaggage baggage) {
        baggages.add(baggage);
    }

    public void orderRobotToDestroyBaggage(HandBaggage baggage) {
        office.getRandomRobot().destroy(baggage);
    }

    public List<HandBaggage> getBaggages() {
        return baggages;
    }

    public List<Passenger> getArrestedPassengers() {
        return arrestedPassengers;
    }

    public List<Weapon> getConfiscatedWeapons() {
        return confiscatedWeapons;
    }
}
