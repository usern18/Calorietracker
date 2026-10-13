public class DailyLimitExceededException extends DomainException {
    private final double currentTotal;
    private final double dailyLimit;

    public DailyLimitExceededException(String message, double currentTotal, double dailyLimit) {
        super(message);
        this.currentTotal = currentTotal;
        this.dailyLimit = dailyLimit;
    }

    public double getCurrentTotal() {
        return currentTotal;
    }

    public double getDailyLimit() {
        return dailyLimit;
    }
}