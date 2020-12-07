package security.scanner;

import security.employee.IDCard;
import security.employee.InspectorOperationStation;
import security.employee.ProfileType;

public class OperatingStation {

    private final BaggageScanner scanner;
    private InspectorOperationStation inspector;
    private final IDCardReader idCardReader;
    private ProfileType authenticatedProfileType;


    public OperatingStation(BaggageScanner scanner) {
        this.scanner = scanner;
        this.idCardReader = new IDCardReader();
        this.authenticatedProfileType = ProfileType.NONE;
    }

    public void triggerAlarm() {
        scanner.alarm(authenticatedProfileType);
    }

    public void rightBtn() {
        scanner.moveBeltForward(authenticatedProfileType);
    }

    public void leftBtn() {
        scanner.moveBeltBackwards(authenticatedProfileType);
    }

    public void rectBtn() {
        scanner.scan(authenticatedProfileType);
    }

    public InspectorOperationStation getInspector() {
        return inspector;
    }

    public void setInspector(InspectorOperationStation inspector) {
        this.inspector = inspector;
    }

    public void swipeIDCard(IDCard idCard) {
        idCardReader.readCard(idCard);
    }

    public void enterPin(String pin) {

        authenticatedProfileType = idCardReader.validatePIN(pin);
        if (authenticatedProfileType != ProfileType.NONE) {
            scanner.activate(authenticatedProfileType);
        }
    }
}
