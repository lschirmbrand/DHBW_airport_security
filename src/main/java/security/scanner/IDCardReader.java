package security.scanner;

import security.employee.IDCard;
import security.employee.ProfileType;
import security.library.AESEncryption;

import java.time.LocalDate;

public class IDCardReader {

    private ProfileType acceptedProfileType;
    private IDCard acceptedCard;
    private String decryptedPin;

    private int attemptsLeft;

    public boolean readCard(IDCard card) {

        if (LocalDate.now().isAfter(card.getValidUntil())) {
            acceptedProfileType = ProfileType.NONE;
            decryptedPin = "";
            return false;
        }

        String magnetStripe = card.getMagnetStripe();

        char typeChar = magnetStripe.charAt(3);

        ProfileType type = switch (typeChar) {
            case 'I' -> ProfileType.I;
            case 'S' -> ProfileType.S;
            case 'O' -> ProfileType.O;
            case 'T' -> ProfileType.T;
            case 'K' -> ProfileType.K;
            default -> throw new UnknownProfileTypeException(typeChar);
        };

        if (type == ProfileType.K || type == ProfileType.O) return false;
        acceptedProfileType = type;
        acceptedCard = card;
        String encryptedPin = magnetStripe.substring(7, magnetStripe.length() - 3);

        decryptedPin = AESEncryption.decrypt(encryptedPin, "key");
        attemptsLeft = 3;

        return true;
    }

    public ProfileType validatePIN(String pin) {
        if (acceptedProfileType == ProfileType.NONE) return ProfileType.NONE;

        if (!pin.equals(decryptedPin)) {
            attemptsLeft--;
            if (attemptsLeft == 0) {
                acceptedCard.setLocked(true);
                acceptedProfileType = ProfileType.NONE;
                acceptedCard = null;
                decryptedPin = "";
            }
            return ProfileType.NONE;
        }

        return acceptedProfileType;
    }
}
