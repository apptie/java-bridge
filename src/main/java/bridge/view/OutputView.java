package bridge.view;

import bridge.dto.output.PrintExceptionDto;
import bridge.dto.output.PrintGameInfoDto;
import bridge.dto.output.PrintMapDto;
import bridge.dto.output.PrintResultDto;

public class OutputView {

    private static final String LINE_FEED = "";

    private OutputView() {
        print(OutputViewMessage.APPLICATION_START.findFullMessage());
        print(LINE_FEED);
    }

    private static class OutputViewSingletonHelper {
        private static final OutputView OUTPUT_VIEW = new OutputView();
    }

    public static bridge.view.OutputView getInstance() {
        return OutputViewSingletonHelper.OUTPUT_VIEW;
    }

    public void printMap(PrintMapDto printMapDto) {
        String upBridgeHistory = printMapDto.getUpBridgeHistory();
        String downBridgeHistory = printMapDto.getDownBridgeHistory();

        print(OutputViewMessage.HISTORY_FORMAT.findFullMessage(upBridgeHistory));
        print(OutputViewMessage.HISTORY_FORMAT.findFullMessage(downBridgeHistory));
        print(LINE_FEED);
    }

    public void printResult(PrintResultDto printResultDto) {
        print(OutputViewMessage.GAME_RESULT_GUIDE.findFullMessage());
        printMap(printResultDto.getPrintMapDto());

        PrintGameInfoDto printGameInfoDto = printResultDto.getPrintGameInfoDto();
        boolean success = printGameInfoDto.isSuccess();
        long tryCount = printGameInfoDto.getTryCount();

        print(OutputViewMessage.GAME_RESULT.findFullMessage(GameSuccessMessage.MapToMessage(success)));
        print(OutputViewMessage.GAME_TRY_COUNT.findFullMessage(tryCount));
    }

    public void printException(PrintExceptionDto printExceptionDto) {
        String exceptionMessage = printExceptionDto.getMessage();

        print(OutputViewMessage.EXCEPTION.findFullMessage(exceptionMessage));
    }

    private void print(String message) {
        System.out.println(message);
    }

    private enum OutputViewMessage {
        APPLICATION_START("다리 건너기 게임을 시작합니다."),
        EXCEPTION("[ERROR] %s"),
        HISTORY_FORMAT("[ %s ]"),
        GAME_RESULT_GUIDE("최종 게임 결과"),
        GAME_RESULT("게임 성공 여부: %s"),
        GAME_TRY_COUNT("총 시도한 횟수: %d");

        private final String baseFormat;

        OutputViewMessage(String baseFormat) {
            this.baseFormat = baseFormat;
        }

        private String findFullMessage(Object... replace) {
            return String.format(baseFormat, replace);
        }
    }

    private enum GameSuccessMessage {
        SUCCESS("성공", true),
        FAILED("실패", false);

        private final String message;
        private final boolean success;

        GameSuccessMessage(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        public static String MapToMessage(boolean success) {
            if (success) {
                return SUCCESS.message;
            }
            return FAILED.message;
        }
    }
}
