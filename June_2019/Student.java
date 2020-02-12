package June_2019;

public class Student {
    private String index;
    private int points;

    public Student(String index, int points) {
        this.index = index;
        this.points = points;
    }

    @Override
    public String toString() {
        return index + " " + points;
    }
}
