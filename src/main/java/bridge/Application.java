package bridge;

import bridge.domain.game.GameStatus;

public class Application {

    public static void main(String[] args) {
        GameRunner.run(GameStatus.MAKE_BRIDGE);
    }
}
