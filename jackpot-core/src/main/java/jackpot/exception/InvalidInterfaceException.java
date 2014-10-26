package jackpot.exception;

public class InvalidInterfaceException extends RuntimeException {
    public InvalidInterfaceException() {
    }

    public InvalidInterfaceException(String message) {
        super(message);
    }

    public InvalidInterfaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidInterfaceException(Throwable cause) {
        super(cause);
    }
}
