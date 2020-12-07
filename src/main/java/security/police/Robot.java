package security.police;

import security.passenger.HandBaggage;

public class Robot {
    public void destroy(HandBaggage baggage) {
        String old = String.join("", baggage.getContent());
        String[] destroyedContent = new String[1000];

        for (int i = 0; i < destroyedContent.length; i++) {
            destroyedContent[i] = old.substring(i * 50, (i + 1) * 50);
        }
        baggage.setContent(destroyedContent);
    }
}
