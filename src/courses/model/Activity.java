package courses.model;

import java.util.Arrays;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum Activity {
    LECTURES("Лекции"),
    EXERCISES("Упражнения"),
    REST("Перерыв"),
    ANSWER("Решения"),
    LUNCH("Обеденный перерыв"),
    END("Конец");

    private final String nameActivity;

    private static final Map<String, Activity> MAP_ACTIVITY = Arrays.stream(values())
            .collect(toMap(Activity::getNameActivity, identity()));

    Activity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public static Activity findActivity(String name) {
        return MAP_ACTIVITY.getOrDefault(name, LECTURES);
    }

    public String getNameActivity() {
        return nameActivity;
    }
}
