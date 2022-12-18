package pars;


import model.Day;
import model.LinesLogFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;

public class Parser {
    private String empty = "";
    private String regex = "^(?<hour>\\d{2}):(?<minute>\\d{2}) (?<activity>.+)$";
    private Pattern pattern = Pattern.compile(regex);


    //метод для преобразования каждой строки файла в формат: LocalTime time, String activity
    private Optional<LinesLogFile> convertingLineLogFile(String line) {
        return Optional.of(line) //возвращает Optional с указанным текущим ненулевым значением
                .map(pattern::matcher)
                .filter(Matcher::find)
                .map(m -> {
                    int hour = Integer.parseInt(m.group("hour"));
                    int minute = Integer.parseInt(m.group("minute"));
                    String activity = m.group("activity");
                    return new LinesLogFile(LocalTime.of(hour, minute), activity);
                });
    }

    //метод для считывания log-file и преобразования его в список
    private List<LinesLogFile> readFileAndConvertToList(Path logFile) throws IOException {
        try (Stream<String> lines = Files.lines(logFile)) {
            return lines.filter(not(empty::equals))
                    .map(this::convertingLineLogFile)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(toList());
        }
    }

    //считываем файл и формируем список дней с активностями
    public List<Day> parseLogFile(Path logFile) throws IOException {
        List<LinesLogFile> linesLogFile = readFileAndConvertToList(logFile);
        List<Day> resultList = new LinkedList<>();
        Day day = new Day();
        String str = "Конец";
        for (LinesLogFile line : linesLogFile) {
            day.addLine(line);
            if(line.getActivity().equals(str)) {
                resultList.add(day);
                day = new Day();
            }
        }
            return resultList;
    }

}
