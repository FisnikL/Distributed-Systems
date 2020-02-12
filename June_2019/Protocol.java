package June_2019;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Protocol {
    public static final int serverPort = 8080;
    public static String serverAddress;
    static {
        try {
            serverAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // TEACHER
    public static final String teacherLogin = "teacherLogin "; // teacherLogin + Index, Course_ID, Activity_ID, Points

    // BOARD
    public static final String boardLogin = "boardLogin "; // boardLogin + courseID activityID
    public static final String endStudentsList = "endStudentsList";
}
