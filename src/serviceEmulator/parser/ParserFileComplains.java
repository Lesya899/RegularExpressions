package serviceEmulator.parser;

import serviceEmulator.model.LinesFileComplaints;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


import static java.util.stream.Collectors.toList;

public class ParserFileComplains {
    private static String regex = "^(?<id>\\d+), (?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})T(?<hour>\\d{2}):(?<minute>\\d{2}):" +
            "(?<seconds>\\d{2}), (?<fullName>[А-Яа-яЁё]+ [А-Яа-яЁё]+), \\+?(375)? ?\\(?(?<codeCity>[\\d]{2})\\)? ?" +
            "(?<nextNumbers1>[\\d]{3})[- ]?(?<nextNumbers2>[\\d]{2})[- ]?(?<nextNumbers3>[\\d]{2}), ?(?<textComplaint>.+)$";

    private static Pattern pattern = Pattern.compile(regex);


    //метод для считывания file и преобразования его в список
    public static List<LinesFileComplaints> readFileAndConvertToList(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file)) {
            return lines.map(pattern::matcher)
                    .filter(Matcher::find)
                    .map(m -> {
                        int id = Integer.parseInt(m.group("id"));
                        int year = Integer.parseInt(m.group("year"));
                        int month = Integer.parseInt(m.group("month"));
                        int day = Integer.parseInt(m.group("day"));
                        int hour = Integer.parseInt(m.group("hour"));
                        int minute = Integer.parseInt(m.group("minute"));
                        int seconds = Integer.parseInt(m.group("seconds"));
                        String fullName = m.group("fullName");
                        String codeCity = m.group("codeCity");
                        String nextNumbers1 = m.group("nextNumbers1");
                        String nextNumbers2 = m.group("nextNumbers2");
                        String nextNumbers3 = m.group("nextNumbers3");
                        String phoneNumber = convertPhoneNumber(codeCity, nextNumbers1, nextNumbers2, nextNumbers3);
                        String textComplaint = m.group("textComplaint");
                        return new LinesFileComplaints(id,LocalDateTime.of(year, month,day,hour,minute,seconds), fullName,phoneNumber,textComplaint);
                    }).collect(toList());
        }

    }

    private static String convertPhoneNumber(String codeCity, String nextNumbers1, String nextNumbers2, String nextNumbers3) {
        return String.format("+375 " + "(" + codeCity + ") " + nextNumbers1 + "-" + nextNumbers2 + "-" + nextNumbers3);
    }
}
