package serviceEmulator.demo;


import serviceEmulator.model.ComplaintsToCall;
import serviceEmulator.parser.ParserFileComplains;
import serviceEmulator.thread.Dispatchers;
import serviceEmulator.thread.ReaderNewComplaintsFromFile;
import serviceEmulator.model.FactoryNewComplaints;
import serviceEmulator.util.ThreadUtil;
import java.io.IOException;
import java.nio.file.Path;


/**
 * Эмулятор сервиса поддержки.
 * Все жалобы клиентов хранятся в хронологическом порядке в виде текстового лог-файла следующего формата (столбцы разделены через запятую):
 * - Порядковый номер клиента
 * - Дата и время звонка в ISO формате
 * - Фамилия и Имя клиента
 * - Телефон клиента
 * - Текст жалобы
 *
 * Каждая новая жалоба идет с новой строки в лог-файле.
 * Например:
 * 1, 2021-09-13T10:15:30, Иванов Иван, +375 29 999 78 90, Не включается свет
 * 2, 2021-12-22T11:38:16, Петров Петр, +375257777765, Почему опять не работает интернет?
 * 3, 2021-12-28T06:55:24, Петров Петр, 333652193, Кто-то оборвал мне телефонный кабель
 *
 * Задача:
 * С какой-то периодичностью (например, раз в 2 минуты) считывать все новые записи из лог-файла и отправлять их
 * диспетчерам для созвона с клиентами (ограничить количество диспетчеров, например, 2-3).
 *
 * Созвон длится какое-то фиксированное время (например, 3-5 секунд), после чего он записывается в другой лог-файл в виде:
 * - Порядковый номер клиента с предыдущего лог файла
 * - Дата и время созвона
 * - Номер телефона клиента
 *
 * Например:
 * 2, 2022-01-04 04:15, +375 (25) 777-77-65
 * 1, 2022-01-04 04:30, +375 (29) 999-78-90
 * 3, 2022-01-04 04:45, +375 (33) 365-21-93
 *
 * Номера телефонов могут быть представлены по-разному, поэтому привести их к одному формату: +375 (29) 999-78-90.
 */

public class EmulatorRunner {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path fileComplaints = Path.of("resources","complaints.txt");
        Path fileCompletedComplaints = Path.of("resources", "completedComplaints.txt");
        ComplaintsToCall complaintsToCall = new ComplaintsToCall(ParserFileComplains.readFileAndConvertToList(fileComplaints));
        FactoryNewComplaints newComplaints = new FactoryNewComplaints();
        newComplaints.createNewComplaints(fileComplaints, 2);
        ReaderNewComplaintsFromFile readNewComplaintsFromFile =  new ReaderNewComplaintsFromFile(fileComplaints, complaintsToCall);
        Dispatchers firstDispatchers = new Dispatchers("Евгений", fileCompletedComplaints, complaintsToCall);
        Dispatchers secondDispatchers = new Dispatchers("Андрей", fileCompletedComplaints, complaintsToCall);
        ThreadUtil.startThreads(firstDispatchers, secondDispatchers, readNewComplaintsFromFile);
        ThreadUtil.joinThreads(firstDispatchers, secondDispatchers, readNewComplaintsFromFile);
    }
}
