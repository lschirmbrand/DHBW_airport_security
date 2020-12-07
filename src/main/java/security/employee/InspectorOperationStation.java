package security.employee;

import security.scanner.BaggageScanner;
import security.scanner.OperatingStation;

import java.time.LocalDate;

public class InspectorOperationStation extends Inspector {

    private final OperatingStation operatingStation;
    private final BaggageScanner scanner;

    public InspectorOperationStation(int id, String name, LocalDate birthDate, boolean isSenior, BaggageScanner scanner) {
        super(id, name, birthDate, isSenior);
        this.operatingStation = scanner.getOperatingStation();
        this.scanner = scanner;
    }

    public void pushRightBtn() {
        operatingStation.rightBtn();
    }

    public void pushLeftBtn() {
        operatingStation.leftBtn();
    }

    public void pushRectBtn() {
        operatingStation.rectBtn();
    }

    public void swipeIDCard() {
        operatingStation.swipeIDCard(idCard);
    }

    public void enterPin(String pin) {
        operatingStation.enterPin(pin);
    }


    public void triggerAlarm() {
        operatingStation.triggerAlarm();
    }

    public void tellI3AboutKnife(int pos) {

        InspectorManualPostControl i3 = scanner.getManualPostControl().getInspector();
        i3.knifeFound(pos);
    }
}
