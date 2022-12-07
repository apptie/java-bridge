package bridge.domain.player;

import bridge.domain.bridge.BridgeTile;
import java.util.List;

public class PlayerState {

    private static final int MIN_BRIDGE_SIZE = 3;
    private static final int START_POSITION = 0;
    private static final long START_TRY_COUNT = 1L;

    private int position;
    private long tryCount;

    public PlayerState() {
        this.position = START_POSITION;
        this.tryCount = START_TRY_COUNT;
    }

    public void move(boolean movable) {
        if (movable) {
            position++;
        }
    }

    public void preparedNextPlay() {
        position = START_POSITION;
        tryCount++;
    }

    public boolean isSuccessful(int endPositionOfBridge) {
        if (position < MIN_BRIDGE_SIZE) {
            return false;
        }
        return endPositionOfBridge == position;
    }

    public BridgeTile findPlayerPositionTile(List<BridgeTile> bridgeTiles) {
        return bridgeTiles.get(position);
    }

    public long getTryCount() {
        return tryCount;
    }
}
