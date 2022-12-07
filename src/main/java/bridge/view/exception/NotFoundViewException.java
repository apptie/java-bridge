package bridge.view.exception;

public class NotFoundViewException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "";

    public NotFoundViewException() {
        super(DEFAULT_MESSAGE);
    }
}
