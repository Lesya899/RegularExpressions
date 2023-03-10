package courses.demo;



import courses.model.Day;
import courses.model.CreatingStatisticalReport;
import courses.model.TimelineReport;
import courses.pars.Parser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;


/**
 * Задан log-file с описанием четырёхдневных курсов в следующем виде:
 * 09:20 Введение
 * 11:00 Упражнения
 * 11:15 Перерыв
 * 11:35 Скаляры
 * 12:30 Обеденный перерыв
 * 13:30 Упражнения
 * 14:10 Решения
 * 14:30 Перерыв
 * 14:40 Массивы
 * 15:40 Упражнения
 * 17:00 Решения
 * 17:30 Конец
 *
 * 09:30 Углубленное изучение массивов
 * 10:30 Перерыв
 * 10:50 Упражнения
 *
 *  Каждая строка начинается со времени, за которым следует описание активности. Пустые строки разделяют дни.
 * Некоторые активности представляют собой названия лекций, например "Введение", "Скаляры", "Массивы".
 * Другие - названия определённых повторяющихся отрезков времени: "Упражнения", "Перерыв", "Решения" и т.д.
 * Словом "Конец" отмечается конец дня.
 *
 * Используя регулярные выражения считать данные из файла, а затем сгенерировать два отчёта в двух разных файлах в следующем виде:
 * 1. В виде временных отрезков:
 * 09:20-11:00 Введение
 * 11:00-11:15 Упражнения
 * 11:15-11:35 Перерыв
 *
 *  2. В виде общего времени, потраченного на активности за день, и детализированного описания лекций:
 * Лекции: 210 минут 22%
 * Решения: 95 минут 9%
 * Перерыв: 65 минут 6%
 *
 * ...
 *  Лекции:
 * Введение: 23 минуты 2%
 */

public class Runner {
    public static void main(String[] args) throws IOException {
        Path logFile = Path.of("resources", "log.txt");
        Parser parsFile = new Parser();
        List<Day> logFileDays = parsFile.parseLogFile(logFile);
        TimelineReport timelineReport = new TimelineReport();
        String timeReport = timelineReport.convertAllDaysToString(logFileDays);
        Files.writeString(Path.of("resources", "timeReport.txt"), timeReport, CREATE, TRUNCATE_EXISTING);
        CreatingStatisticalReport creatingStatisticalReport = new CreatingStatisticalReport();
        String statisticReport = creatingStatisticalReport.allDaysConvert(logFileDays);
        Files.writeString(Path.of("resources", "statisticReport.txt"), statisticReport, CREATE, TRUNCATE_EXISTING);
    }
}















