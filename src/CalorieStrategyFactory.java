public class CalorieStrategyFactory {
    public static CalorieCalculationStrategy getStrategy(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Strategy type cannot be null");
        }
        switch (type.toLowerCase()) {
            case "mifflin":
            case "miffstjeor":
                return new MiffStJeorStrategy();
            case "harris":
            case "harrisbenedict":
                return new HarrisBenedictStrategy();
            default:
                throw new IllegalArgumentException("Unknown strategy type: " + type);
        }
    }
}