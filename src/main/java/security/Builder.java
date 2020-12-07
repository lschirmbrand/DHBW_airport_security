package security;

import security.employee.*;
import security.library.BoyerMoore;
import security.passenger.HandBaggage;
import security.passenger.Passenger;
import security.police.FederalPoliceOffice;
import security.police.FederalPoliceOfficer;
import security.prohibitetItems.ProhibitedItemType;
import security.scanner.BaggageScanner;
import security.scanner.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Builder {

    private final BaggageScanner scanner;

    private List<Passenger> passengers;
    private final FederalPoliceOffice office;

    public Builder() {
        Configuration config = new Configuration(new BoyerMoore());

        office = new FederalPoliceOffice();

        scanner = new BaggageScanner(config, office);

        initPassengersFromFile("passenger_baggage.txt");
        initEmployees();
    }

    private void initPassengersFromFile(String filename) {

        passengers = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        try {
            InputStream io = getClass().getClassLoader().getResourceAsStream(filename);
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(io));
            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        passengers = lines.stream().map(this::getPassenger).collect(Collectors.toList());
    }

    private Passenger getPassenger(String line) {
        // extract values from line
        int semiIndex = line.indexOf(";");
        String name = line.substring(0, semiIndex);

        line = line.substring(semiIndex + 1);
        semiIndex = line.indexOf(";");
        int baggageCount = Integer.parseInt(line.substring(0, semiIndex));

        String prohibitedList = line.substring(semiIndex + 1);

        ProhibitedItemType[][] prohibitedItems = extractProhibitedItems(prohibitedList, baggageCount);


        HandBaggage[] baggages = new HandBaggage[baggageCount];
        for (int i = 0; i < baggageCount; i++) {

            String[] content = createBaggageContent(prohibitedItems[i]);

            baggages[i] = new HandBaggage(content);
        }

        return new Passenger(name, baggages, this.scanner);
    }


    private ProhibitedItemType[][] extractProhibitedItems(String prohibitedList, int baggageCount) {
        ProhibitedItemType[][] prohibitedItems = new ProhibitedItemType[baggageCount][5];

        for (int i = 0; i < baggageCount; i++) {
            for (int j = 0; j < 5; j++) {
                prohibitedItems[i][j] = ProhibitedItemType.CLEAN;
            }
        }

        if (prohibitedList.equals("-")) {
            return prohibitedItems;
        }

        prohibitedList = prohibitedList.substring(1, prohibitedList.length() - 1);
        String[] itemStrings = prohibitedList.split(";");

        for (String itemString : itemStrings) {
            String[] elements = itemString.split(",");

            ProhibitedItemType type = switch (elements[0]) {
                case "K" -> ProhibitedItemType.KNIFE;
                case "W" -> ProhibitedItemType.WEAPON;
                case "E" -> ProhibitedItemType.EXPLOSIVE;
                default -> throw new IllegalStateException("Unexpected value: " + elements[0]);
            };

            int baggageIndex = Integer.parseInt(elements[1]) - 1;
            int layerIndex = Integer.parseInt(elements[2]) - 1;

            prohibitedItems[baggageIndex][layerIndex] = type;
        }

        return prohibitedItems;

    }

    private String[] createBaggageContent(ProhibitedItemType[] prohibitedItems) {
        String[] content = new String[5];

        for (int i = 0; i < 5; i++) {
            ProhibitedItemType item = prohibitedItems[i];

            String knifeString = "kn!fe";
            String weaponString = "glock|7";
            String explosivesString = "exp|os!ve";

            String str = switch (item) {
                case KNIFE -> knifeString;
                case WEAPON -> weaponString;
                case EXPLOSIVE -> explosivesString;
                case CLEAN -> "";
            };

            int sum = 10000 - str.length();
            int first = new Random().nextInt(sum);

            content[i] = "-".repeat(first) + str + "-".repeat(sum - first);
        }

        return content;
    }

    private void initEmployees() {
        InspectorRollerConveyor i1 = new InspectorRollerConveyor(0, "Clint Eastwood", LocalDate.of(1930, 5, 31), true, scanner);
        IDCard cardI1 = new IDCard(IDCardType.STAFF, ProfileType.I, "0000");
        i1.setIdCard(cardI1);

        InspectorOperationStation i2 = new InspectorOperationStation(1, "Natalie Portman", LocalDate.of(1981, 6, 9), false, scanner);
        IDCard cardI2 = new IDCard(IDCardType.STAFF, ProfileType.I, "0001");
        i2.setIdCard(cardI2);

        InspectorManualPostControl i3 = new InspectorManualPostControl(2, "Bruce Willis", LocalDate.of(1955, 3, 19), true, scanner);
        IDCard cardI3 = new IDCard(IDCardType.STAFF, ProfileType.I, "0002");
        i3.setIdCard(cardI3);

        Supervisor s = new Supervisor(3, "Jodie Foster", LocalDate.of(1962, 11, 19), false, false, scanner);
        IDCard cardS = new IDCard(IDCardType.STAFF, ProfileType.S, "0003");
        s.setIdCard(cardS);

        FederalPoliceOfficer o1 = new FederalPoliceOfficer(4, "Wesley Snipes", LocalDate.of(1962, 7, 31), "", office, scanner);
        IDCard card01 = new IDCard(IDCardType.EXTERNAL, ProfileType.O, "0004");
        s.setIdCard(card01);

        FederalPoliceOfficer o2 = new FederalPoliceOfficer(5, "Toto", LocalDate.of(1969, 1, 1), "", office, scanner);
        IDCard card02 = new IDCard(IDCardType.EXTERNAL, ProfileType.O, "0005");
        s.setIdCard(card02);

        FederalPoliceOfficer o3 = new FederalPoliceOfficer(6, "Harry", LocalDate.of(1969, 1, 1), "", office, scanner);
        IDCard cardO3 = new IDCard(IDCardType.EXTERNAL, ProfileType.O, "0006");
        s.setIdCard(cardO3);

        Technician t = new Technician(7, "Jason Statham", LocalDate.of(1967,7, 26));
        IDCard cardT = new IDCard(IDCardType.EXTERNAL, ProfileType.S, "0007");
        s.setIdCard(cardT);

        Employee k = new Employee(8, "Jason Clarke", LocalDate.of(1969, 7, 17));
        IDCard cardK = new IDCard(IDCardType.STAFF, ProfileType.K, "0008");
        s.setIdCard(cardS);

        office.setOfficers(new FederalPoliceOfficer[]{o1, o2, o3});

        scanner.setEmployees(i1, i2, i3, s, o1);
    }

    public BaggageScanner getScanner() {
        return scanner;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }
}
