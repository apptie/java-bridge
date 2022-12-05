package bridge.domain.bridge;

import bridge.domain.bridge.exception.WrongGeneratorException;
import java.util.Arrays;

public enum BridgeTile {
    DOWN(BridgeTileConst.PLAYER_MOVE_DOWN, BridgeTileConst.DOWN_TILE_VALUE),
    UP(BridgeTileConst.PLAYER_MOVE_UP, BridgeTileConst.UP_TILE_VALUE);

    private final String command;
    private final int value;

    BridgeTile(String command, int value) {
        this.command = command;
        this.value = value;
    }

    public static BridgeTile findTile(String command) {
        return Arrays.stream(BridgeTile.values())
                .filter(tile -> tile.command.equals(command))
                .findAny()
                .orElseThrow(() ->
                    new IllegalArgumentException(String.format(
                            BridgeTileExceptionMessage.INVALID_PLAYER_MOVE,
                            BridgeTileConst.PLAYER_MOVE_UP, BridgeTileConst.PLAYER_MOVE_DOWN)));
    }

    public static String mapToCommand(BridgeTile targetTile) {
        if (targetTile == BridgeTile.DOWN) {
            return BridgeTile.DOWN.command;
        }
        if (targetTile == BridgeTile.UP) {
            return BridgeTile.UP.command;
        }
        throw new WrongGeneratorException(BridgeTileConst.UP_TILE_VALUE, BridgeTileConst.DOWN_TILE_VALUE);
    }

    private static class BridgeTileConst {
        private static final String PLAYER_MOVE_UP = "U";
        private static final String PLAYER_MOVE_DOWN = "D";
        private static final int DOWN_TILE_VALUE = 0;
        private static final int UP_TILE_VALUE = 1;
    }

    private static class BridgeTileExceptionMessage {
        private static final String INVALID_PLAYER_MOVE = "이동할 칸은 %s(위) 혹은 %s(아래)만을 선택할 수 있습니다.";
    }
}
