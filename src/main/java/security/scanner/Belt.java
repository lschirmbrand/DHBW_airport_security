package security.scanner;

public class Belt {
    private Tray beforeScanner;
    private Tray entrance;
    private Tray exit;


    public Belt() {
    }

    public void moveForward() {
        entrance = beforeScanner;
        beforeScanner = null;
    }

    public void moveBackwards() {
        entrance = exit;
        exit = null;
    }

    public Tray getBeforeScanner() {
        return beforeScanner;
    }

    public void setBeforeScanner(Tray beforeScanner) {
        this.beforeScanner = beforeScanner;
    }

    public Tray getEntrance() {
        return entrance;
    }

    public void setEntrance(Tray entrance) {
        this.entrance = entrance;
    }

    public Tray getExit() {
        return exit;
    }

    public void setExit(Tray exit) {
        this.exit = exit;
    }
}
