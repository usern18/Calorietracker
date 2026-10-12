public class DomainException extends Exception {
    private final String fieldName;

    public DomainException(String message, String fieldName) {
        super(message);
        this.fieldName = fieldName;
    }

    public DomainException(String message) {
        this(message, null);
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String getMessage() {
        if (fieldName != null) {
            return super.getMessage() + " [поле: " + fieldName + "]";
        }
        return super.getMessage();
    }
}