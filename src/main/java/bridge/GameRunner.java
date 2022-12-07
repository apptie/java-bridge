package bridge;

import bridge.controller.GameController;
import bridge.domain.game.GameStatus;
import bridge.view.IOViewResolver;
import bridge.view.InputView;
import bridge.view.OutputView;

public final class GameRunner {

    private GameRunner() {
    }

    public static void run(GameStatus gameStatus) {
        IOViewResolver ioViewResolver = new IOViewResolver(InputView.getInstance(), OutputView.getInstance());
        GameController controller = new GameController(ioViewResolver);

        while (gameStatus.playable()) {
            gameStatus = controller.process(gameStatus);
        }
    }
}
