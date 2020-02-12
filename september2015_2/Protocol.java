package september2015_2;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Protocol {
    public static final int serverPort = 8080;
    public static InetAddress serverAddress;

    static {
        try {
            serverAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    // SensorDoor
    public static final String sensorDoorLogin = "sensorDoorLogin";
    public static final String sensorDoorLogout = "sensorDoorLogout";
    public static final String currentNumOfStudents = "currentNumOfStudents "; // currentNumOfStudents NUMBER

    // Student
    public static final String studentIn = "studentIn";
    public static final String studentOut = "studentOut";

    // Monitor
    public static final String sensorDoorsListRequest = "sensorDoorsListRequest";
    public static final String sensorDoorsListResponseEnd = "sensorDoorsListResponseEnd";
    public static final String numOfStudentsInRoom = "numOfStudentsInRoom ";
    public static final String sensorDoesntExistAnymore = "sensorDoesntExistAnymore";

}
