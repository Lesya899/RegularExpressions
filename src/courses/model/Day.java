package courses.model;

import java.util.ArrayList;
import java.util.List;

//Класс Day содержит список строк со временем и активностью

public class Day {

    private final List<LinesLogFile> lines = new ArrayList<>();

    public void addLine(LinesLogFile line) {
        lines.add(line);
    }

    public List<LinesLogFile> getLines() {
        return lines;
    }
}
