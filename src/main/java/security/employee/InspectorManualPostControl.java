package security.employee;

import security.passenger.HandBaggage;
import security.police.FederalPoliceOfficer;
import security.scanner.TestStrip;
import security.prohibitetItems.Knife;
import security.scanner.*;

import java.time.LocalDate;

public class InspectorManualPostControl extends Inspector {

    private final BaggageScanner scanner;
    private final Track track01;
    private final Belt belt;
    private final ManualPostControl manualPostControl;

    public InspectorManualPostControl(int id, String name, LocalDate birthDate, boolean isSenior, BaggageScanner scanner) {
        super(id, name, birthDate, isSenior);
        this.scanner = scanner;
        this.track01 = scanner.getTrack01();
        this.belt = scanner.getBelt();
        this.manualPostControl = scanner.getManualPostControl();
    }

    public void knifeFound(int pos) {
        Tray tray = belt.getExit();

        putTrayOnTrack01();

        Knife knife = openBaggageAndRemoveKnife(tray.getBaggage(), pos);

        disposeKnife(knife);
    }

    public void putTrayOnTrack01() {
        Tray tray = belt.getExit();
        belt.setExit(null);

        track01.put(tray);
    }

    public void handOverBaggage(HandBaggage baggage, FederalPoliceOfficer officer) {
        officer.takeBaggage(baggage);
    }

    public void swipe(TestStrip strip, HandBaggage baggage) {
        strip.swipe(baggage);
    }

    public Knife openBaggageAndRemoveKnife(HandBaggage baggage, int pos) {
        String knifeStr = "kn!fe";
        int layerIndex = pos / 10000;
        pos %= 10000;
        String[] content = baggage.getContent();

        String layer = content[layerIndex];

        layer = layer.substring(0, pos) + "-".repeat(knifeStr.length()) + layer.substring(pos + knifeStr.length());

        content[layerIndex] = layer;

        return new Knife();
    }

    public void disposeKnife(Knife knife) {
        scanner.getManualPostControl().getKnifeDisposal().dispose(knife);
    }

    public void putAtScannerExit(Tray tray) {
        track01.remove(tray);
        belt.setExit(tray);
    }

    public boolean analyzeStrip(TestStrip strip) {
        return manualPostControl.getExplosivesTraceDetector().analyzeTestStrip(strip);
    }
}
