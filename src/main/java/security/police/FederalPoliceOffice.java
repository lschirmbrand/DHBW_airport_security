package security.police;

import java.util.Random;

public class FederalPoliceOffice {
    private FederalPoliceOfficer[] officers;
    private final Robot[] robots;

    public FederalPoliceOffice() {
        robots = new Robot[3];
        for (int i = 0; i < 3; i++) {
            robots[i] = new Robot();
        }
    }

    public void setOfficers(FederalPoliceOfficer[] officers) {
        this.officers = officers;
    }

    public FederalPoliceOfficer getOfficer(int i) {
        return officers[i];
    }

    public Robot getRandomRobot() {
        return robots[new Random().nextInt(3)];
    }
}
