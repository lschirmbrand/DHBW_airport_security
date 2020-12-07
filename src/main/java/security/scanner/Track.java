package security.scanner;

import java.util.ArrayDeque;
import java.util.Queue;

public class Track {

    private final Queue<Tray> trays;

    public Track() {
        trays = new ArrayDeque<>();
    }

    public void put(Tray tray) {
        trays.add(tray);
    }

    public void remove(Tray tray) {
        trays.remove(tray);
    }

    public boolean contains(Tray tray) {
        return trays.contains(tray);
    }
}
