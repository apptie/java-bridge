package bridge.view;

import bridge.dto.output.PrintExceptionDto;
import bridge.dto.output.PrintGameInfoDto;
import bridge.dto.output.PrintMapDto;
import bridge.dto.output.PrintResultDto;

public class OutputView {

    private static final String LINE_FEED = "";
    private static final String SUCCESS = "성공";
    private static final String FAILED = "실패";

    private OutputView() {
    }

    private static class OutputViewSingletonHelper {
        private static final OutputView OUTPUT_VIEW = new OutputView();
    }

    public static bridge.view.OutputView getInstance() {
        return bridge.view.OutputView.OutputViewSingletonHelper.OUTPUT_VIEW;
    }

    public void printMap(PrintMapDto printMapDto) {
        String upBridgeHistory = printMapDto.getUpBridgeHistory();
        String downBridgeHistory = printMapDto.getDownBridgeHistory();

        print(OutputViewMessage.HISTORY_FORMAT.getFullMessage(upBridgeHistory));
        print(OutputViewMessage.HISTORY_FORMAT.getFullMessage(downBridgeHistory));
        print(LINE_FEED);
    }

    public void printResult(PrintResultDto printResultDto) {
        print(OutputViewMessage.GAME_RESULT_GUIDE.getFullMessage());
        printMap(printResultDto.getPrintMapDto());

        PrintGameInfoDto printGameInfoDto = printResultDto.getPrintGameInfoDto();
        boolean success = printGameInfoDto.isSuccess();
        long tryCount = printGameInfoDto.getTryCount();

        print(OutputViewMessage.GAME_RESULT.getFullMessage(MapToMessageFromSuccess(success)));
        print(OutputViewMessage.GAME_TRY_COUNT.getFullMessage(tryCount));
    }

    public void printException(PrintExceptionDto printExceptionDto) {
        String exceptionMessage = printExceptionDto.getMessage();

        print(OutputViewMessage.EXCEPTION.getFullMessage(exceptionMessage));
    }

    private String MapToMessageFromSuccess(boolean success) {
        if (success) {
            return SUCCESS;
        }
        return FAILED;
    }

    private void print(String message) {
        System.out.println(message);
    }

    private enum OutputViewMessage {
        EXCEPTION("[ERROR] %s"),
        HISTORY_FORMAT("[ %s ]"),
        GAME_RESULT_GUIDE("최종 게임 결과"),
        GAME_RESULT("게임 성공 여부: %s"),
        GAME_TRY_COUNT("총 시도한 횟수: %d");

        private final String baseFormat;

        OutputViewMessage(String baseFormat) {
            this.baseFormat = baseFormat;
        }

        private String getFullMessage(Object... replace) {
            return String.format(baseFormat, replace);
        }
    }
}
