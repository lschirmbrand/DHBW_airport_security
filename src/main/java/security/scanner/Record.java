package security.scanner;

import security.prohibitetItems.ProhibitedItemType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private final int id;
    private final String timestamp;

    private final ProhibitedItemType itemType;
    private final int pos;

    private final String result;

    public Record(int id, ProhibitedItemType itemType, int pos) {
        this.id = id;
        this.timestamp = new SimpleDateFormat("dd.MM.yyyy  HH:mm:ss,SSS").format(new Date());
        this.itemType = itemType;
        this.pos = pos;

        this.result = createResultString(itemType, pos);
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getResult() {
        return result;
    }

    public ProhibitedItemType getItemType() {
        return itemType;
    }

    public int getPos() {
        return pos;
    }

    private String createResultString(ProhibitedItemType itemType, int pos) {
        return switch (itemType) {
            case CLEAN -> "clean";
            case KNIFE -> "prohibited item | knife detected at position " + pos;
            case WEAPON -> "prohibited item | weapon - glock7 detected at position " + pos;
            case EXPLOSIVE -> "prohibited item | explosive detected at position " + pos;
        };
    }
}
