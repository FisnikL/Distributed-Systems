package June_2019;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String courseID;
    private List<Activity> activities;

    public Course(String courseID) {
        this.courseID = courseID;
        this.activities = new ArrayList<>();
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
