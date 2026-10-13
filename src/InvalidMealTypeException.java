public class InvalidMealTypeException extends DomainException {
    private final int invalidValue;

    public InvalidMealTypeException(String message, int invalidValue) {
        super(message, "mealTypeInt");
        this.invalidValue = invalidValue;
    }

    public int getInvalidValue() {
        return invalidValue;
    }
}