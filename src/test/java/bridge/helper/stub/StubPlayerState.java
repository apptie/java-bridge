package bridge.helper.stub;

import bridge.domain.bridge.BridgeTile;
import bridge.domain.player.PlayerState;
import java.util.List;

public class StubPlayerState extends PlayerState {

    private static final int MIN_BRIDGE_SIZE = 3;

    private final int stubPosition;

    public StubPlayerState(int stubPosition) {
        this.stubPosition = stubPosition;
    }

    @Override
    public BridgeTile findPlayerPositionTile(final List<BridgeTile> bridgeTiles) {
        return bridgeTiles.get(stubPosition);
    }

    @Override
    public boolean isSuccessful(int endPositionOfBridge) {
        if (stubPosition < MIN_BRIDGE_SIZE) {
            return false;
        }
        return stubPosition == endPositionOfBridge;
    }
}
