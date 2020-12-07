package security.scanner;

import java.util.Stack;

public class TrayProvider {
    private final Stack<Tray> trayStack;

    public TrayProvider() {
        trayStack = new Stack<>();
        for (int i = 0; i < 25; i++) {
            trayStack.push(new Tray());
        }
    }

    public void put(Tray tray) {
        trayStack.push(tray);
    }

    public Tray take() {
        return trayStack.pop();
    }
}
