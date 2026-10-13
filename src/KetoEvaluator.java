public class KetoEvaluator extends BaseMealEvaluator {
    @Override
    public String evaluate(Food food) {
        if (isLightPortion(food)) {
            return "Кето-вердикт: Легкий кето-перекус. Чудово підходить для підтримки кетозу.";
        } else {
            return "Кето-вердикт: Велика порція. Перевірте співвідношення білків і жирів.";
        }
    }
}