package serviceEmulator.thread;

/**
 * класс предназначен для созвона по жалобе клиента, содержит поля:
 * -список жалоб;
 * - файл для записи информации после обработки
 */


import serviceEmulator.model.ComplaintsProcessed;
import serviceEmulator.model.LinesFileComplaints;
import serviceEmulator.model.ComplaintsToCall;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class Dispatchers extends Thread {
    private String nameDispatcher;
    private Path processedComplaintsFile;
    private ComplaintsToCall listComplaints;


    public Dispatchers(String nameDispatcher, Path processedComplaintsFile, ComplaintsToCall listComplaints) {
        this.nameDispatcher = nameDispatcher;
        this.processedComplaintsFile = processedComplaintsFile;
        this.listComplaints = listComplaints;
    }

    @Override
    public void run() {
        while (!listComplaints.getListComplaints().isEmpty()) {
            if (listComplaints.getLock().tryLock()) {
                try {
                    LinesFileComplaints linesFileComplaints = listComplaints.takeComplaintIntoProcessing(); //берем жалобу в обработку
                    ComplaintsProcessed listComplaintsProcessed = new ComplaintsProcessed(linesFileComplaints.getId(), LocalDateTime.now(), linesFileComplaints.getPhoneNumber());
                    String dataAndTime = listComplaintsProcessed.getTimeAndData().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_DATE_TIME);
                    CharSequence report = String.join(", ", String.valueOf(listComplaintsProcessed.getId()), dataAndTime, listComplaintsProcessed.getNumberPhone()) +
                            System.lineSeparator();
                    Files.writeString(processedComplaintsFile, report, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    System.out.println("Диспетчер " + nameDispatcher + " обработал жалобу с порядковым номером " + listComplaintsProcessed.getId());
                    listComplaints.getLock().unlock();
                    Thread.sleep(5000);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}