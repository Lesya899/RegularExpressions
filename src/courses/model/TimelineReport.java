package courses.model;


import java.util.List;
import static java.util.stream.Collectors.joining;
public class TimelineReport {


    //метод для преобразования списка активностей  в строку формата: 09:20-11:00 Введение
    private String convertDayToString(Day day) {
        StringBuilder convertStr = new StringBuilder();
        List<LinesLogFile> lines = day.getLines();
        for (int i = 0; i < lines.size() - 1; i++) {
            convertStr.append(lines.get(i).getTime().toString()).append("-")
                            .append(lines.get(i + 1).getTime().toString())
                            .append(" ").append(lines.get(i).getActivity())
                            .append(System.lineSeparator()); //метод System.lineSeparator() возвращает строку с символами перевода строки

        }
        return convertStr.toString();
    }

    //преобразовываем списки активностей для каждого дня в строки
    public String convertAllDaysToString(List<Day> dayLogFile) {
        return dayLogFile.stream()
                .map(this::convertDayToString)
                .collect(joining(System.lineSeparator()));
    }
}
