package security.employee;

import security.library.AESEncryption;

import java.time.LocalDate;

public class IDCard {

    private static int nextId = 0;

    private final int id;
    private final LocalDate validUntil;
    private final String magnetStripe;
    private boolean isLocked;
    private final IDCardType type;

    public IDCard(IDCardType cardType, ProfileType profileType, String pin) {
        this.id = nextId++;
        this.validUntil = LocalDate.now().plusYears(1);
        this.isLocked = false;

        String encryptedPin = AESEncryption.encrypt(pin, "key");
        this.magnetStripe = "***" + profileType.name() + "***" + encryptedPin + "***";

        this.type = cardType;
    }

    public int getId() {
        return id;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public String getMagnetStripe() {
        return magnetStripe;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public IDCardType getType() {
        return type;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
