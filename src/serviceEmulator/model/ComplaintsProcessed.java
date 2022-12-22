package serviceEmulator.model;

import java.time.LocalDateTime;

/**
 * Класс предназначен для хранения информации о жалобе после созвона:
 * -Порядковый номер клиента с предыдущего лог файла;
 *- Дата и время созвона;
 * - Номер телефона клиента.
 */


public class ComplaintsProcessed {

    private int id;
    private LocalDateTime timeAndData;
    private String numberPhone;

    public ComplaintsProcessed(int id, LocalDateTime timeAndData, String numberPhone) {
        this.id = id;
        this.timeAndData = timeAndData;
        this.numberPhone = numberPhone;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getTimeAndData() {
        return timeAndData;
    }

    public String getNumberPhone() {
        return numberPhone;
    }
}
