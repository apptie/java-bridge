package bridge.dto.output;

import bridge.domain.bridge.BridgeTile;
import bridge.domain.player.Player;

public class PrintMapDto {

    private final String upBridgeHistory;
    private final String downBridgeHistory;

    public PrintMapDto(Player player) {
        this.upBridgeHistory = player.getPlayerTargetTileHistory(BridgeTile.UP);
        this.downBridgeHistory = player.getPlayerTargetTileHistory(BridgeTile.DOWN);
    }

    public String getUpBridgeHistory() {
        return upBridgeHistory;
    }

    public String getDownBridgeHistory() {
        return downBridgeHistory;
    }
}
