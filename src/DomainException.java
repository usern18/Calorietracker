public class DomainException extends Exception {
    private final String timestamp;

    public DomainException(String message) {
        super(message);
        this.timestamp = java.time.LocalDateTime.now().toString();
    }

    public String getTimestamp() {
        return timestamp;
    }
}