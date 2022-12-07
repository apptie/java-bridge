package bridge.controller;

import bridge.BridgeRandomNumberGenerator;
import bridge.domain.bridge.BridgeGame;
import bridge.domain.bridge.exception.WrongGeneratorException;
import bridge.domain.game.GameStatus;
import bridge.domain.player.exception.WrongBridgeTileException;
import bridge.dto.controller.ExitDto;
import bridge.dto.controller.MoveDto;
import bridge.dto.controller.RetryDto;
import bridge.dto.input.ReadBridgeSizeDto;
import bridge.dto.input.ReadGameCommandDto;
import bridge.dto.input.ReadMovingDto;
import bridge.dto.output.PrintExceptionDto;
import bridge.dto.output.PrintResultDto;
import bridge.view.IOViewResolver;
import bridge.view.exception.NotFoundViewException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class GameController {

    private static final String APPLICATION_EXCEPTION_MESSAGE = "애플리케이션 구성에 문제가 발생했습니다.";

    private final IOViewResolver ioViewResolver;
    private final Map<GameStatus, Supplier<GameStatus>> gameStatusMappings;
    private BridgeGame bridgeGame;

    public GameController(final IOViewResolver ioViewResolver) {
        this.ioViewResolver = ioViewResolver;
        gameStatusMappings = new EnumMap<>(GameStatus.class);

        initGameStatusMappings();
    }

    private void initGameStatusMappings() {
        gameStatusMappings.put(GameStatus.MAKE_BRIDGE, this::makeBridge);
        gameStatusMappings.put(GameStatus.GAME_PLAY, this::gamePlay);
        gameStatusMappings.put(GameStatus.GAME_OVER, this::gameOver);
        gameStatusMappings.put(GameStatus.GAME_EXIT, this::gameExit);
    }

    public GameStatus process(GameStatus gameStatus) {
        try {
            return gameStatusMappings.get(gameStatus).get();
        } catch (IllegalArgumentException e) {
            return processGameException(e.getMessage(), gameStatus);
        } catch (WrongGeneratorException | NotFoundViewException | WrongBridgeTileException e) {
            return processApplicationException(e.getMessage());
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
            return processApplicationException(APPLICATION_EXCEPTION_MESSAGE);
        }
    }

    private GameStatus processGameException(String message, final GameStatus gameStatus) {
        ioViewResolver.outputViewResolve(new PrintExceptionDto(message));
        return gameStatus;
    }

    private GameStatus processApplicationException(String message) {
        System.out.println(message);
        return GameStatus.APPLICATION_EXIT;
    }

    private GameStatus makeBridge() {
        ReadBridgeSizeDto inputDto = ioViewResolver.inputViewResolve(ReadBridgeSizeDto.class);
        BridgeRandomNumberGenerator generator = new BridgeRandomNumberGenerator();
        bridgeGame = new BridgeGame(inputDto.getSize(), generator);

        return GameStatus.GAME_PLAY;
    }

    private GameStatus gamePlay() {
        ReadMovingDto dto = ioViewResolver.inputViewResolve(ReadMovingDto.class);
        MoveDto moveDto = bridgeGame.move(dto);

        ioViewResolver.outputViewResolve(moveDto.getPrintMapDto());
        return moveDto.getNextGameStatus();
    }

    private GameStatus gameOver() {
        ReadGameCommandDto inputDto = ioViewResolver.inputViewResolve(ReadGameCommandDto.class);
        RetryDto retryDto = bridgeGame.retry(inputDto);

        return retryDto.getNextGameStatus();
    }

    private GameStatus gameExit() {
        ExitDto exitDto = bridgeGame.exit();

        ioViewResolver.outputViewResolve(new PrintResultDto(exitDto));
        return GameStatus.APPLICATION_EXIT;
    }
}
