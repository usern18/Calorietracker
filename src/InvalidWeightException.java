public class InvalidWeightException extends DomainException {
    private final double invalidValue;

    public InvalidWeightException(String message, double invalidValue) {
        super(message, "weightGrams");
        this.invalidValue = invalidValue;
    }

    public double getInvalidValue() {
        return invalidValue;
    }
}