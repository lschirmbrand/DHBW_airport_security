package security.scanner;

import security.library.BruteForce;
import security.library.IStringMatching;

public class ExplosivesTraceDetector {
    private final IStringMatching stringMatching = new BruteForce();

    public boolean analyzeTestStrip(TestStrip strip) {
        for (String row : strip.getContent()) {
            if (stringMatching.search(row, "exp") != -1) {
                return true;
            }
        }
        return false;
    }
}

