package security.scanner;

import security.library.IStringMatching;

public class Configuration {
    private final IStringMatching stringMatching;

    public Configuration(IStringMatching stringMatching) {
        this.stringMatching = stringMatching;
    }

    public IStringMatching getStringMatching() {
        return stringMatching;
    }
}
