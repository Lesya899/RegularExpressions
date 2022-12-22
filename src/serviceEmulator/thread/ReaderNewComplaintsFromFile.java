package serviceEmulator.thread;

//класс для считывания новых жалоб из файла

import serviceEmulator.model.LinesFileComplaints;
import serviceEmulator.model.ComplaintsToCall;
import serviceEmulator.parser.ParserFileComplains;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderNewComplaintsFromFile extends Thread {

    private Path file;
    private ComplaintsToCall complaintsToCall; //список жалоб для созвона

    public ReaderNewComplaintsFromFile(Path file, ComplaintsToCall complaintsToCall) {
        this.file = file;
        this.complaintsToCall = complaintsToCall;
    }

    @Override
    public void run() {
        if (complaintsToCall.getLock().tryLock()) {
            try {
                List<LinesFileComplaints> listAllComplaints = ParserFileComplains.readFileAndConvertToList(file);//считываем файл в список
                int id = complaintsToCall.getMaxSequenceNumber(); //получаем максимальный порядковый номер из списка жалоб для созвона
                List<LinesFileComplaints> resList = listAllComplaints.stream()
                        .filter(complaints -> complaints.getId() > id) //из списка всех жалоб выбираем новые жалобы
                        .collect(Collectors.toList());
                complaintsToCall.addComplaintsToList(resList);
                complaintsToCall.getLock().unlock();
                Thread.sleep(120000);
            } catch (InterruptedException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}