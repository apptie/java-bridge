package bridge.domain.bridge.exception;

public class WrongGeneratorException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "다리를 생성하는 generator는 %d(위 칸) 또는 %d(아래 칸)만을 생성할 수 있습니다.";

    public WrongGeneratorException(int upValue, int downValue) {
        super(String.format(DEFAULT_MESSAGE, upValue, downValue));
    }
}
