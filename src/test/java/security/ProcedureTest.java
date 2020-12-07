package security;
import org.junit.jupiter.api.Test;
import security.employee.*;
import security.passenger.HandBaggage;
import security.passenger.Passenger;
import security.police.FederalPoliceOfficer;
import security.prohibitetItems.ProhibitedItemType;
import security.prohibitetItems.Weapon;
import security.scanner.*;
import security.scanner.Record;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public final class ProcedureTest {

    Builder builder;
    BaggageScanner scanner;
    List<Passenger> passengers;
    String[] cleanBaggageContent;
    Supervisor supervisor;
    private final InspectorOperationStation i2;
    private final InspectorRollerConveyor i1;

    private ProcedureTest() {
        builder = new Builder();
        scanner = builder.getScanner();
        passengers = builder.getPassengers();

        supervisor =  scanner.getSupervision().getSupervisor();
        supervisor.swipeIDCard();
        supervisor.enterPIN("0003");
        supervisor.pressPowerBtn();
        i1 = scanner.getRollerConveyor().getInspector();
        i2 = scanner.getOperatingStation().getInspector();
        i2.swipeIDCard();
        i2.enterPin("0001");
    }

    @Test //Test 11
    public void testClean() {
        // Faraz Abbasi;1;-
        Passenger passenger = passengers.get(0);
        assertEquals(passenger.getHandBaggages().length, 1);
        HandBaggage baggage = passenger.getHandBaggages()[0];
        assertArrayEquals(baggage.getContent(), new String[]{
                "-".repeat(10000),
                "-".repeat(10000),
                "-".repeat(10000),
                "-".repeat(10000),
                "-".repeat(10000)
        });

        Tray tray = passenger.takeTray();
        passenger.putInTray(tray, baggage);

        assertSame(tray.getBaggage(), baggage);

        passenger.layOnConveyor(tray);

        assertSame(scanner.getRollerConveyor().getTrays().peek(), tray);

        i1.push();

        assertFalse(scanner.getRollerConveyor().getTrays().contains(tray));
        assertSame(scanner.getBelt().getBeforeScanner(), tray);

        i2.pushRightBtn();

        assertSame(scanner.getBelt().getEntrance(), tray);

        i2.pushRectBtn();

        Record record = scanner.getLatestRecord();

        assertEquals(record.getResult(), "clean");
        assertTrue(scanner.getTrack02().contains(tray));

    }

    @Test //Test 12
    public void testKnife() {
        // Oscar Alonso;1;[K,1,3]
        Passenger passenger = passengers.get(6);

        assertTrue(passenger.getHandBaggages()[0].getContent()[2].contains("kn!fe"));

        Tray tray = passenger.takeTray();
        HandBaggage baggage = passenger.getHandBaggages()[0];
        passenger.putInTray(tray, baggage);

        assertSame(tray.getBaggage(), baggage);

        passenger.layOnConveyor(tray);

        assertSame(scanner.getRollerConveyor().getTrays().peek(), tray);

        i1.push();

        assertFalse(scanner.getRollerConveyor().getTrays().contains(tray));
        assertSame(scanner.getBelt().getBeforeScanner(), tray);

        i2.pushRightBtn();

        assertSame(scanner.getBelt().getEntrance(), tray);

        i2.pushRectBtn();

        Record record = scanner.getLatestRecord();

        assertTrue(record.getResult().startsWith("prohibited item | knife detected at position "));
        assertEquals(record.getItemType(), ProhibitedItemType.KNIFE);

        i2.tellI3AboutKnife(record.getPos());

        assertEquals(scanner.getManualPostControl().getKnifeDisposal().getDisposedKnives().size(), 1);

        InspectorManualPostControl i3 = scanner.getManualPostControl().getInspector();

        i3.putAtScannerExit(tray);

        assertSame(scanner.getBelt().getExit(), tray);
        assertFalse(scanner.getTrack01().contains(tray));

        i2.pushLeftBtn();

        assertSame(scanner.getBelt().getEntrance(), tray);
        assertNull(scanner.getBelt().getExit());

        i2.pushRectBtn();

        Record nextResult = scanner.getLatestRecord();

        assertTrue(scanner.getTrack02().contains(tray));

        assertEquals(nextResult.getResult(), "clean");
    }

    @Test //Test 13
    public void testWeapon() {
        // Margaret Axford;3;[W,2,5]

        Passenger passenger = passengers.get(14);

        HandBaggage baggage = passenger.getHandBaggages()[1];

        assertTrue(baggage.getContent()[4].contains("glock|7"));

        Tray tray = passenger.takeTray();
        passenger.putInTray(tray, baggage);

        passenger.layOnConveyor(tray);

        i1.push();

        i2.pushRightBtn();

        i2.pushRectBtn();

        Record record = scanner.getLatestRecord();

        assertTrue(record.getResult().startsWith("prohibited item | weapon - glock7 detected at position "));
        assertEquals(record.getItemType(), ProhibitedItemType.WEAPON);

        i2.triggerAlarm();

        assertEquals(scanner.getState(), BaggageScannerState.LOCKED);

        FederalPoliceOfficer o1 = scanner.getO1();
        FederalPoliceOfficer o2 = scanner.getO2();
        FederalPoliceOfficer o3 = scanner.getO3();
        assertNotNull(o2);
        assertEquals(o2.getName(), "Toto");
        assertNotNull(o3);
        assertEquals(o3.getName(), "Harry");

        o1.arrest(passenger);
        assertTrue(o1.getArrestedPassengers().contains(passenger));

        InspectorManualPostControl i3 = scanner.getManualPostControl().getInspector();

        i3.putTrayOnTrack01();

        Weapon weapon = o2.openAndRemoveWeapon(baggage, record.getPos());

        o2.handOverWeapon(o3, weapon);

        assertEquals(o3.getConfiscatedWeapons().size(), 1);

        for (HandBaggage b : passenger.getHandBaggages()) {
            o3.takeBaggage(b);
        }

        assertEquals(o3.getBaggages().size(), passenger.getHandBaggages().length);

        o2.leave();
        o3.leave();

        assertNull(scanner.getO2());
        assertNull(scanner.getO3());

        // TODO Betrieb fortsetzen
    }


    @Test //Test 14
    public void testExplosive() {
        // Joaquin Loera;1;[E,1,1]
        Passenger passenger = passengers.get(331);

        assertEquals(passenger.getName(), "Joaquin Loera");

        HandBaggage baggage = passenger.getHandBaggages()[0];

        assertTrue(baggage.getContent()[0].contains("exp|os!ve"));

        Tray tray = passenger.takeTray();
        passenger.putInTray(tray, baggage);
        assertSame(tray.getBaggage(), baggage);

        passenger.layOnConveyor(tray);

        assertTrue(scanner.getRollerConveyor().getTrays().contains(tray));

        i1.push();

        i2.pushRightBtn();

        assertSame(scanner.getBelt().getEntrance(), tray);

        i2.pushRectBtn();

        Record nextResult = scanner.getLatestRecord();

        assertTrue(nextResult.getResult().startsWith("prohibited item | explosive detected at position "));
        assertEquals(nextResult.getItemType(), ProhibitedItemType.EXPLOSIVE);

        i2.triggerAlarm();

        assertEquals(scanner.getState(), BaggageScannerState.LOCKED);

        FederalPoliceOfficer o1 = scanner.getO1();
        FederalPoliceOfficer o2 = scanner.getO2();
        FederalPoliceOfficer o3 = scanner.getO3();
        assertNotNull(o2);
        assertEquals(o2.getName(), "Toto");
        assertNotNull(o3);
        assertEquals(o3.getName(), "Harry");

        o1.arrest(passenger);
        assertTrue(o1.getArrestedPassengers().contains(passenger));

        InspectorManualPostControl i3 = scanner.getManualPostControl().getInspector();

        i3.putTrayOnTrack01();

        TestStrip strip = new TestStrip();
        i3.swipe(strip, baggage);

        boolean testResult = i3.analyzeStrip(strip);
        assertTrue(testResult);

        o2.orderRobotToDestroyBaggage(baggage);
        assertEquals(baggage.getContent().length, 1000);

        for (String fraction:baggage.getContent()) {
            assertEquals(fraction.length(), 50);
        }
    }
}