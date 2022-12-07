package bridge.domain.player;

public enum MovingStep {
    CORRECT_MOVE("O"),
    WRONG_MOVE("X"),
    NOT_MOVE(" ");

    private final String movingMessage;

    MovingStep(final String movingMessage) {
        this.movingMessage = movingMessage;
    }

    public static MovingStep findMovingStep(boolean isTargetBridgeTile, boolean movable) {
        if (!isTargetBridgeTile) {
            return NOT_MOVE;
        }
        if (movable) {
            return CORRECT_MOVE;
        }
        return WRONG_MOVE;
    }

    @Override
    public String toString() {
        return movingMessage;
    }
}
