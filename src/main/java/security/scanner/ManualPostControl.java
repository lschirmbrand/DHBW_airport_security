package security.scanner;

import security.employee.InspectorManualPostControl;

public class ManualPostControl {

    private InspectorManualPostControl inspector;
    private final ExplosivesTraceDetector explosivesTraceDetector = new ExplosivesTraceDetector();
    private final KnifeDisposal knifeDisposal = new KnifeDisposal();

    public InspectorManualPostControl getInspector() {
        return inspector;
    }

    public void setInspector(InspectorManualPostControl inspector) {
        this.inspector = inspector;
    }

    public KnifeDisposal getKnifeDisposal() {
        return knifeDisposal;
    }

    public ExplosivesTraceDetector getExplosivesTraceDetector() {
        return explosivesTraceDetector;
    }
}
