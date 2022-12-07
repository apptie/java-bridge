package bridge.helper.common;

import bridge.domain.bridge.Bridge;

public abstract class CommonBridgeField extends CommonStubBridgeGeneratorField {

    protected Bridge bridge = new Bridge(3, stubGenerator);
}
