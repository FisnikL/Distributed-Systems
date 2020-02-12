package June_2019;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Board {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(Protocol.serverAddress, Protocol.serverPort);

        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        Scanner scanner = new Scanner(socket.getInputStream());

        Random random = new Random();
        String courseID = courseIDs.get(random.nextInt(courseIDs.size()));
        String activityID = activityIDs.get(random.nextInt(activityIDs.size()));

        printWriter.println(Protocol.boardLogin + courseID + " " + activityID);
        printWriter.flush();

        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(line.equals(Protocol.endStudentsList)){
                break;
            }
            System.out.println(line);
        }
    }


    public static List<String> courseIDs = new ArrayList<>();
    public static List<String> activityIDs = new ArrayList<>();

    static{
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
