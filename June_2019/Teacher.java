package June_2019;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Teacher {
    public static void main(String[] args) throws IOException {
        for(int i = 0; i < 20; ++i){
            TeacherWorker tw = new TeacherWorker();
            tw.start();
        }
    }
}

class TeacherWorker extends Thread {

    private Socket socket;
    private PrintWriter printWriter;
    private Scanner scanner;
    private Random random;

    public TeacherWorker() throws IOException {
        socket = new Socket(Protocol.serverAddress, Protocol.serverPort);
        printWriter = new PrintWriter(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
        random = new Random();
    }

    @Override
    public void run() {


        for (int i = 0; i < 20; ++i) {
            String index = indexes.get(random.nextInt(indexes.size()));
            String courseID = courseIDs.get(random.nextInt(courseIDs.size()));
            String activityID = activityIDs.get(random.nextInt(activityIDs.size()));
            int points = random.nextInt(101);

            String data = index + ", " + courseID + ", " + activityID + ", " + points;

            printWriter.println(Protocol.teacherLogin + data);
            printWriter.flush();
        }
    }

    public static List<String> indexes = new ArrayList<>();
    public static List<String> courseIDs = new ArrayList<>();
    public static List<String> activityIDs = new ArrayList<>();

    static{
        indexes.add("index1");
        indexes.add("index2");
        indexes.add("index3");
        indexes.add("index4");
        indexes.add("index5");

        courseIDs.add("courseID1");
        courseIDs.add("courseID2");
        courseIDs.add("courseID3");
        courseIDs.add("courseID4");
        courseIDs.add("courseID5");

        activityIDs.add("activityID1");
        activityIDs.add("activityID2");
        activityIDs.add("activityID3");
    }

}
