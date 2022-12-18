package model;

import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;
import static java.util.stream.Collectors.joining;

public class CreatingStatisticalReport {


    //представление строк отчета в определенном формате
    private String linesRepresentationReport(String activityName, long activityTime, long allTime) {
        long percentActivity = (long) ((double) activityTime / allTime * 100);
        return String.format("%s: %d минут %d%%", activityName, activityTime, percentActivity);
    }

    //метод для расчета общего времени, потраченного на активности за день
    private String totalTimeActivityDay(Day day) {
        Map<Activity, Long> timeActivities = new EnumMap<>(Activity.class);
        List<LinesLogFile> lines = day.getLines();
        for (int i = 0; i < lines.size() - 1; i++) {
            long activityDuration = ChronoUnit.MINUTES.between(lines.get(i).getTime(), lines.get(i + 1).getTime());
            timeActivities.merge(Activity.findActivity(lines.get(i).getActivity()), activityDuration, Long::sum);
        }
        long allTimeActivity = timeActivities.values().stream().mapToLong(Long::longValue).sum();
        return timeActivities.entrySet().stream()
                .map(entry -> linesRepresentationReport(entry.getKey().getNameActivity(), entry.getValue(), allTimeActivity))
                .collect(joining(System.lineSeparator()));
    }

    //метод для детализированного описания лекций
    private String detailedDescriptionOfLectures(Day day) {
        Map<String, Long> mapLectures = new HashMap<>();
        AtomicLong allTimeActivity = new AtomicLong();
        List<LinesLogFile> lines = day.getLines();
        for (int i = 0; i < lines.size() - 1; i++) {
            long activityDuration = ChronoUnit.MINUTES.between(lines.get(i).getTime(), lines.get(i+1).getTime());
            allTimeActivity.addAndGet(activityDuration);
            if (Activity.findActivity(lines.get(i).getActivity()) == Activity.LECTURES) {
                mapLectures.put(lines.get(i).getActivity(), activityDuration);
            }
        }
        return Stream.concat(Stream.of("Лекции:"), mapLectures.entrySet().stream()
                        .map(x -> linesRepresentationReport(x.getKey(), x.getValue(), allTimeActivity.get())))
                .collect(joining(System.lineSeparator()));
    }

    public String allDaysConvert(List<Day> days) {
        return days.stream()
                .flatMap(day -> Stream.of(totalTimeActivityDay(day), detailedDescriptionOfLectures(day)))
                .collect(joining(System.lineSeparator() + System.lineSeparator()));
    }
}
