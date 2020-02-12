package June_2019;

import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String activityID;
    private List<Student> students;

    public Activity(String activityID) {
        this.activityID = activityID;
        this.students = new ArrayList<>();
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
