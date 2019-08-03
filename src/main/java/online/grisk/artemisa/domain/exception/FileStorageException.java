package online.grisk.artemisa.domain.exception;

public class FileStorageException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 8680280633835789879L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}