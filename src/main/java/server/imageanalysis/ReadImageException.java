package server.imageanalysis;

public class ReadImageException extends Exception {

    public ReadImageException(String message) {
        super(message);
    }

    public ReadImageException(String message, Throwable cause) {
        super(message, cause);
    }

}
