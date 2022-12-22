package serviceEmulator.model;

//класс предназначен для хранения списка жалоб для созвона

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ComplaintsToCall {

    private final Lock lock = new ReentrantLock();

    private List<LinesFileComplaints> listComplaints = new ArrayList<>();
    private int maxSequenceNumber; //максимальный порядковый номер

    public ComplaintsToCall(List<LinesFileComplaints> listComplaints) {
        addComplaintsToList(listComplaints);
    }

    public void addComplaintsToList(List<LinesFileComplaints> list) {
        listComplaints.addAll(list);
        maxSequenceNumber = list.stream()
                .map(LinesFileComplaints::getId)
                .reduce(maxSequenceNumber, Math::max);
    }

    public LinesFileComplaints takeComplaintIntoProcessing() {
        return listComplaints.remove(0);
    }

    public Lock getLock() {
        return lock;
    }

    public List<LinesFileComplaints> getListComplaints() {
        return listComplaints;
    }

    public int getMaxSequenceNumber() {
        return maxSequenceNumber;
    }
}
