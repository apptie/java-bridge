package bridge.domain.bridge;

import bridge.BridgeMaker;
import bridge.BridgeNumberGenerator;
import bridge.domain.player.PlayerState;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Bridge {

    public static final int MIN_BRIDGE_SIZE = 3;
    public static final int MAX_BRIDGE_SIZE = 20;

    private final List<BridgeTile> bridgeTiles;

    public Bridge(int size, BridgeNumberGenerator generator) {
        validateBridgeSize(size);

        List<String> bridgeCommand = makeBridgeCommand(size, generator);

        this.bridgeTiles = bridgeCommand
                .stream()
                .map(BridgeTile::findTile)
                .collect(Collectors.toList());
    }

    private void validateBridgeSize(int size) {
        if (!isValidRangeSize(size)) {
            throw new IllegalArgumentException(BridgeExceptionMessage.INVALID_BRIDGE_SIZE.findFullMessage(
                    MIN_BRIDGE_SIZE, MAX_BRIDGE_SIZE));
        }
    }

    private boolean isValidRangeSize(int size) {
        return MIN_BRIDGE_SIZE <= size && size <= MAX_BRIDGE_SIZE;
    }

    private List<String> makeBridgeCommand(int size, final BridgeNumberGenerator generator) {
        BridgeMaker bridgeMaker = new BridgeMaker(generator);

        return bridgeMaker.makeBridge(size);
    }

    public boolean calculatePlayerMoving(BridgeTile playerStep, PlayerState playerState) {
        return playerState.findPlayerPositionTile(Collections.unmodifiableList(bridgeTiles)) == playerStep;
    }

    public int findEndOfBridgePosition() {
        return bridgeTiles.size();
    }

    private enum BridgeExceptionMessage {
        INVALID_BRIDGE_SIZE("다리 길이는 %d부터 %d 사이의 숫자여야 합니다.");

        private final String baseMessage;

        BridgeExceptionMessage(String baseMessage) {
            this.baseMessage = baseMessage;
        }

        public String findFullMessage(Object... replaces) {
            return String.format(baseMessage, replaces);
        }
    }
}
