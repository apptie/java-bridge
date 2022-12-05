package bridge.domain.game;

import java.util.Arrays;

public enum GameCommand {
    RETRY(GameCommandConst.GAME_RETRY, GameStatus.GAME_PLAY),
    EXIT(GameCommandConst.GAME_QUIT, GameStatus.GAME_EXIT);

    private final String command;
    private final GameStatus gameStatus;

    GameCommand(String command, GameStatus gameStatus) {
        this.command = command;
        this.gameStatus = gameStatus;
    }

    public static GameStatus findNextGameOver(String command) {
        return Arrays.stream(GameCommand.values())
                .filter(gameCommand -> gameCommand.command.equals(command))
                .map(gameCommand -> gameCommand.gameStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format(GameCommandExceptionMessage.INVALID_GAME_COMMAND,
                                GameCommandConst.GAME_RETRY, GameCommandConst.GAME_RETRY)));
    }

    private static class GameCommandConst {
        private static final String GAME_RETRY = "R";
        private static final String GAME_QUIT = "Q";
    }

    private static class GameCommandExceptionMessage {
        private static final String INVALID_GAME_COMMAND = "게임 시도 여부는 %s(재시도) 혹은 %s(종료)만을 선택할 수 있습니다.";
    }
}
