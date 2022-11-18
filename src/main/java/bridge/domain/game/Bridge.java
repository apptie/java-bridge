package bridge.domain.game;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.utils.common.BridgeConst;
import bridge.utils.message.ExceptionMessageUtils;
import java.util.List;
import java.util.stream.Collectors;

public class Bridge {

    private final List<BridgeTile> bridge;

    public Bridge(int size, BridgeNumberGenerator generator) {
        validateBridgeSize(size);

        List<String> bridgeCommand = makeBridgeCommand(size, generator);

        this.bridge = bridgeCommand
                .stream()
                .map(BridgeTile::findTile)
                .collect(Collectors.toList());
    }

    private void validateBridgeSize(int size) {
        if (!isValidRangeSize(size)) {
            throw new IllegalArgumentException(ExceptionMessageUtils.WRONG_BRIDGE_SIZE.getMessage());
        }
    }

    private boolean isValidRangeSize(int size) {
        return BridgeConst.MIN_BRIDGE_SIZE <= size && size <= BridgeConst.MAX_BRIDGE_SIZE;
    }

    private List<String> makeBridgeCommand(int size, BridgeNumberGenerator generator) {
        BridgeMaker bridgeMaker = new BridgeMaker(generator);

        return bridgeMaker.makeBridge(size);
    }

    public boolean calculatePlayerMoving(BridgeTile playerStep, int playerPosition) {
        return bridge.get(playerPosition) == playerStep;
    }

    public boolean isEnd(int playerPosition) {
        return bridge.size() == playerPosition;
    }
}
