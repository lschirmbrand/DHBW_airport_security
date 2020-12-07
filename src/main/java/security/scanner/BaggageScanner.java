package security.scanner;

import security.employee.*;
import security.library.IStringMatching;
import security.passenger.HandBaggage;
import security.police.FederalPoliceOffice;
import security.police.FederalPoliceOfficer;
import security.prohibitetItems.ProhibitedItemType;

import java.util.ArrayList;
import java.util.List;

public class BaggageScanner {

    private BaggageScannerState state;
    private final Configuration configuration;

    private final Belt belt;
    private final Supervision supervision;
    private final ManualPostControl manualPostControl;
    private final OperatingStation operatingStation;
    private final RollerConveyor rollerConveyor;
    private final TrayProvider trayProvider;
    private final Track track01;
    private final Track track02;

    private FederalPoliceOfficer o1;
    private FederalPoliceOfficer o2;
    private FederalPoliceOfficer o3;

    private final FederalPoliceOffice federalPoliceOffice;

    private final List<Record> records;

    public BaggageScanner(Configuration config, FederalPoliceOffice federalPoliceOffice) {
        belt = new Belt();
        supervision = new Supervision(this);
        manualPostControl = new ManualPostControl();
        operatingStation = new OperatingStation(this);
        rollerConveyor = new RollerConveyor();
        trayProvider = new TrayProvider();
        track01 = new Track();
        track02 = new Track();

        records = new ArrayList<>();

        this.federalPoliceOffice = federalPoliceOffice;

        this.configuration = config;

        this.state = BaggageScannerState.SHUTDOWN;
    }

    public void moveBeltForward(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.I) {
            belt.moveForward();
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "moveBeltForward");
        }
    }

    public void moveBeltBackwards(ProfileType authenticatedProfileType) {

        if (authenticatedProfileType == ProfileType.I) {
            belt.moveBackwards();
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "moveBeltForward");
        }
    }

    public void scan(ProfileType authenticatedProfileType) {

        if (state != BaggageScannerState.ACTIVATED) return;

        if (authenticatedProfileType == ProfileType.I) {

            Tray tray = belt.getEntrance();
            HandBaggage baggage = tray.getBaggage();
            state = BaggageScannerState.IN_USE;
            Record record = scan(baggage);
            state = BaggageScannerState.ACTIVATED;

            belt.setEntrance(null);
            if (record.getItemType() == ProhibitedItemType.CLEAN) {
                track02.put(tray);
            } else {
                belt.setExit(tray);
            }
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "scan");
        }
    }

    public Record scan(HandBaggage handBaggage) {
        IStringMatching stringMatching = configuration.getStringMatching();

        int pos = -1;
        ProhibitedItemType itemType = ProhibitedItemType.CLEAN;

        for (int i = 0; i < 5; i++) {
            String layer = handBaggage.getContent()[i];

            pos = stringMatching.search(layer, "kn!fe");
            if (pos != -1) {
                itemType = ProhibitedItemType.KNIFE;
                pos += 10000 * i;
                break;
            }

            pos = stringMatching.search(layer, "glock|7");
            if (pos != -1) {
                itemType = ProhibitedItemType.WEAPON;
                pos += 10000 * i;
                break;
            }

            pos = stringMatching.search(layer, "exp|os!ve");
            if (pos != -1) {
                itemType = ProhibitedItemType.EXPLOSIVE;
                pos += 10000 * i;
                break;
            }
        }


        Record record = new Record(records.size(), itemType, pos);
        records.add(record);
        return record;
    }


    public void alarm(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.I) {
            state = BaggageScannerState.LOCKED;

            requestPoliceOfficers();
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "alarm");
        }
    }

    public void maintenance(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType != ProfileType.T) {
            throw new WrongProfileTypeException(authenticatedProfileType, "maintenance");
        }
    }

    public void report(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType != ProfileType.S) {
            throw new WrongProfileTypeException(authenticatedProfileType, "report");
        }
    }

    public void start(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.S) {
            if (state == BaggageScannerState.SHUTDOWN) {
                state = BaggageScannerState.DEACTIVATED;
            }
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "start");
        }
    }

    public void shutdown(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.S) {
            state = BaggageScannerState.SHUTDOWN;
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "shutdown");
        }
    }

    public void activate(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.I) {
            state = BaggageScannerState.ACTIVATED;
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "activate");
        }
    }

    public void unlock(ProfileType authenticatedProfileType) {
        if (authenticatedProfileType == ProfileType.S) {
            if (state == BaggageScannerState.LOCKED) {
                state = BaggageScannerState.ACTIVATED;
            }
        } else {
            throw new WrongProfileTypeException(authenticatedProfileType, "unlock");
        }
    }

    public void requestPoliceOfficers() {
        o2 = this.federalPoliceOffice.getOfficer(1);
        o3 = this.federalPoliceOffice.getOfficer(2);
    }

    public void officerLeaving(FederalPoliceOfficer officer) {
        if (officer.equals(o2))
            o2 = null;
        if (officer.equals(o3))
            o3 = null;
    }

    public List<Record> getRecords() {
        return records;
    }

    public Record getLatestRecord() {
        return records.get(records.size() - 1);
    }


    public void setEmployees(InspectorRollerConveyor i1, InspectorOperationStation i2, InspectorManualPostControl i3, Supervisor s, FederalPoliceOfficer o1) {
        rollerConveyor.setInspector(i1);
        operatingStation.setInspector(i2);
        manualPostControl.setInspector(i3);

        supervision.setSupervisor(s);

        this.o1 = o1;
        this.o2 = null;
        this.o3 = null;
    }

    public Belt getBelt() {
        return belt;
    }

    public FederalPoliceOfficer getO1() {
        return o1;
    }

    public FederalPoliceOfficer getO2() {
        return o2;
    }

    public FederalPoliceOfficer getO3() {
        return o3;
    }

    public Supervision getSupervision() {
        return supervision;
    }

    public ManualPostControl getManualPostControl() {
        return manualPostControl;
    }

    public OperatingStation getOperatingStation() {
        return operatingStation;
    }


    public RollerConveyor getRollerConveyor() {
        return rollerConveyor;
    }

    public TrayProvider getTrayProvider() {
        return trayProvider;
    }


    public Track getTrack01() {
        return track01;
    }


    public Track getTrack02() {
        return track02;
    }

    public BaggageScannerState getState() {
        return state;
    }
}

