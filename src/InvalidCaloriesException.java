public class InvalidCaloriesException extends DomainException {
    private final double invalidValue;

    public InvalidCaloriesException(String message, double invalidValue) {
        super(message, "caloriesPer100g");
        this.invalidValue = invalidValue;
    }

    public double getInvalidValue() {
        return invalidValue;
    }
}