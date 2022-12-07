package bridge.domain.player;

import bridge.domain.bridge.BridgeTile;
import bridge.domain.player.exception.WrongBridgeTileException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovingStepHistory {

    private static final String HISTORY_SEPARATOR = " | ";

    private final List<MovingStep> upStepHistory = new ArrayList<>();
    private final List<MovingStep> downStepHistory = new ArrayList<>();

    public void updateHistory(BridgeTile playerStep, boolean movable) {
        upStepHistory.add(MovingStep.findMovingStep(BridgeTile.UP.matches(playerStep), movable));
        downStepHistory.add(MovingStep.findMovingStep(BridgeTile.DOWN.matches(playerStep), movable));
    }

    public String getPlayerTargetTileHistory(BridgeTile targetTile) {
        if (targetTile == BridgeTile.UP) {
            return mapToPlayerStepHistoryLog(upStepHistory);
        }
        if (targetTile == BridgeTile.DOWN) {
            return mapToPlayerStepHistoryLog(downStepHistory);
        }
        throw new WrongBridgeTileException();
    }

    private String mapToPlayerStepHistoryLog(List<MovingStep> targetHistory) {
        return targetHistory.stream().map(MovingStep::toString).collect(Collectors.joining(HISTORY_SEPARATOR));
    }

    public void clear() {
        this.upStepHistory.clear();
        this.downStepHistory.clear();
    }
}
