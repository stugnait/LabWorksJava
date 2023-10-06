import java.util.List;

public class Group {
    public List<Student> studentList;
    public int avgMark;

    public Group(List<Student> studentList, int avgMark) {
        this.studentList = studentList;
        this.avgMark = avgMark;
    }

    public int getAvgMark() {
        return avgMark;
    }
}
