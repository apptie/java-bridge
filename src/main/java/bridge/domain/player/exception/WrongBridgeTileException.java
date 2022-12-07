package bridge.domain.player.exception;

public class WrongBridgeTileException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 BridgeTile 입니다.";

    public WrongBridgeTileException() {
        super(DEFAULT_MESSAGE);
    }
}
