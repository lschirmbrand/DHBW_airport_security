package security.scanner;

import security.passenger.HandBaggage;

import java.util.Arrays;
import java.util.Random;

public class TestStrip {
    private final String[] content;

    public TestStrip() {
        content = new String[10];
        Arrays.fill(content, "p".repeat(30));
    }

    public String[] getContent() {
        return content;
    }

    public void swipe(HandBaggage baggage) {
        int index = new Random().nextInt(27);

        content[new Random().nextInt(10)] = "p".repeat(index) + "exp" + "p".repeat(30 - index);
    }
}
