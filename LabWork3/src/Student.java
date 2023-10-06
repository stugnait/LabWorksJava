public class Student {

    public String name;
    public int course;
    public int avgMark;

    public int getAvgMark() {
        return avgMark;
    }

    public Student(String name, int course, int marks) {
        this.name = name;
        this.course = course;
        this.avgMark = marks;
    }

    public int getCourse() {
        return course;
    }
}
