package security.scanner;

import security.prohibitetItems.Knife;

import java.util.ArrayList;
import java.util.List;

public class KnifeDisposal {

    public final List<Knife> disposedKnives = new ArrayList<>();

    public void dispose(Knife knife) {
        disposedKnives.add(knife);
    }

    public List<Knife> getDisposedKnives() {
        return disposedKnives;
    }
}
