package bridge.view;

import bridge.dto.input.ReadBridgeSizeDto;
import bridge.dto.input.ReadGameCommandDto;
import bridge.dto.input.ReadMovingDto;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final int MIN_BRIDGE_SIZE = 3;
    private static final int MAX_BRIDGE_SIZE = 20;
    private static final int COMMAND_LENGTH = 1;
    private static final int INPUT_CHAR_INDEX = 0;
    private static final String LINE_FEED = "";

    private InputView() {
    }

    private static class InputViewSingletonHelper {
        private static final InputView INPUT_VIEW = new InputView();
    }

    public static InputView getInstance() {
        return InputViewSingletonHelper.INPUT_VIEW;
    }

    public ReadBridgeSizeDto readBridgeSize() {
        print(GuideMessage.MAKE_BRIDGE.message);
        int inputBridgeSize = processInputBridgeSize();

        print(LINE_FEED);
        return new ReadBridgeSizeDto(inputBridgeSize);
    }

    private int processInputBridgeSize() {
        try {
            int playerInput = Integer.parseInt(processPlayerInput());
            validateBridgeSize(playerInput);
            return playerInput;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BRIDGE_SIZE.message);
        }
    }

    private void validateBridgeSize(int size) {
        if (!isValidRangeSize(size)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BRIDGE_SIZE.message);
        }
    }

    private boolean isValidRangeSize(int size) {
        return MIN_BRIDGE_SIZE <= size && size <= MAX_BRIDGE_SIZE;
    }

    public ReadMovingDto readMoving() {
        print(GuideMessage.GAME_PLAY.message);

        String playerInput = processPlayerInput();

        if (isUpperCase(playerInput)) {
            return new ReadMovingDto(playerInput);
        }
        throw new IllegalArgumentException(ExceptionMessage.INVALID_PLAYER_MOVE.message);
    }

    public ReadGameCommandDto readGameCommand() {
        print(GuideMessage.GAME_RETRY.message);

        String playerInput = processPlayerInput();

        if (isUpperCase(playerInput)) {
            return new ReadGameCommandDto(playerInput);
        }
        throw new IllegalArgumentException(ExceptionMessage.INVALID_GAME_COMMAND.message);
    }

    private boolean isUpperCase(String input) {
        return input.length() == COMMAND_LENGTH && Character.isUpperCase(input.charAt(INPUT_CHAR_INDEX));
    }

    private String processPlayerInput() {
        return Console.readLine();
    }

    private void print(String message) {
        System.out.println(message);
    }

    private enum GuideMessage {
        MAKE_BRIDGE("다리의 길이를 입력해주세요."),

        GAME_PLAY("이동할 칸을 선택해주세요. (위: %s, 아래: %s)",
                MessageConst.PLAYER_MOVE_UP,
                MessageConst.PLAYER_MOVE_DOWN),

        GAME_RETRY("게임을 다시 시도할지 여부를 입력해주세요. (재시도: %s, 종료: %s)",
                MessageConst.GAME_RETRY,
                MessageConst.GAME_QUIT);

        private final String message;

        GuideMessage(String baseMessage, Object... replaces) {
            this.message = String.format(baseMessage, replaces);
        }
    }

    private enum ExceptionMessage {
        INVALID_BRIDGE_SIZE("다리 길이는 %d부터 %d 사이의 숫자여야 합니다.",
                MIN_BRIDGE_SIZE, MAX_BRIDGE_SIZE),

        INVALID_PLAYER_MOVE("이동할 칸은 %s(위) 혹은 %s(아래)만을 선택할 수 있습니다.",
                MessageConst.PLAYER_MOVE_UP,
                MessageConst.PLAYER_MOVE_DOWN),

        INVALID_GAME_COMMAND("게임 시도 여부는 %s(재시도) 혹은 %s(종료)만을 선택할 수 있습니다.",
                MessageConst.GAME_RETRY,
                MessageConst.GAME_QUIT);

        private final String message;

        ExceptionMessage(String baseMessage, Object... replaces) {
            this.message = String.format(baseMessage, replaces);
        }
    }

    private static class MessageConst {
        private static final String PLAYER_MOVE_UP = "U";
        private static final String PLAYER_MOVE_DOWN = "D";
        private static final String GAME_RETRY = "R";
        private static final String GAME_QUIT = "Q";
    }
}
