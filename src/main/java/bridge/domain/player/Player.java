package bridge.domain.player;

import bridge.domain.bridge.Bridge;
import bridge.domain.bridge.BridgeTile;

public class Player {

    private final MovingStepHistory movingStepHistory;
    private final PlayerState playerState;

    public Player(int size) {
        validateBridgeSize(size);

        movingStepHistory = new MovingStepHistory();
        playerState = new PlayerState();
    }

    private void validateBridgeSize(int size) {
        if (!isValidRangeSize(size)) {
            throw new IllegalArgumentException(PlayerExceptionMessage.INVALID_BRIDGE_SIZE.massage);
        }
    }

    private boolean isValidRangeSize(int size) {
        return Bridge.MIN_BRIDGE_SIZE <= size && size <= Bridge.MAX_BRIDGE_SIZE;
    }

    public boolean move(Bridge bridge, BridgeTile playerStep) {
        boolean movable = bridge.calculatePlayerMoving(playerStep, playerState);

        playerState.move(movable);
        movingStepHistory.updateHistory(playerStep, movable);
        return movable;
    }

    public String getPlayerTargetTileHistory(BridgeTile targetTile) {
        return movingStepHistory.getPlayerTargetTileHistory(targetTile);
    }

    public boolean isSuccessful(Bridge bridge) {
        return playerState.isSuccessful(bridge.findEndOfBridgePosition());
    }

    public void preparedNextPlay() {
        playerState.preparedNextPlay();
        movingStepHistory.clear();
    }

    public long getTryCount() {
        return playerState.getTryCount();
    }

    private enum PlayerExceptionMessage {
        INVALID_BRIDGE_SIZE("다리 길이는 %d부터 %d 사이의 숫자여야 합니다.",
                PlayerExceptionMessageConst.MIN_BRIDGE_SIZE,
                PlayerExceptionMessageConst.MAX_BRIDGE_SIZE);

        private final String massage;

        PlayerExceptionMessage(String baseMessage, Object... replaces) {
            this.massage = String.format(baseMessage, replaces);
        }

        private static class PlayerExceptionMessageConst {
            private static final int MIN_BRIDGE_SIZE = 3;
            private static final int MAX_BRIDGE_SIZE = 20;
        }
    }

}
