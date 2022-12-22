package serviceEmulator.model;

//Класс для создания новых жалоб

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class FactoryNewComplaints  {

    private int countComplaintsInFile = 14;


    public void createNewComplaints(Path file, int countNewComplaints){
        Scanner sc = new Scanner(System.in);
        int count = 1;
        while (count <= countNewComplaints){
            System.out.println("Введите данные в следующем формате: " +
                    " фамилия и имя, " +
                    " номер телефона," +
                    " суть жалобы");
                if (sc.hasNextLine()) {
                    String str = sc.nextLine();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                    String timeAndData = LocalDateTime.now().format(formatter);
                    String newComplaints = String.format("%d, %s, %s\n", ++countComplaintsInFile, timeAndData, str);
                    try{
                        Files.writeString(file, newComplaints, StandardOpenOption.APPEND);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    count++;
                } else {
                    System.out.println("Данные введены в неверном формате");
                    break;
                }
        }
    }
}





