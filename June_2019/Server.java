package June_2019;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Server {
    public static List<Course> courses = new ArrayList<Course>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(Protocol.serverPort);

        while(true){
            Socket conn = serverSocket.accept();
            System.out.println("new client");
            // PrintWriter printWriter = new PrintWriter(conn.getOutputStream());
            Scanner scanner = new Scanner(conn.getInputStream());

            if(scanner.hasNextLine()){
                String request = scanner.nextLine();
                if(request.startsWith(Protocol.teacherLogin)){
                    System.out.println("new request: " + request);
                    handleTeacherLogin(conn, request);
                }
                else if(request.startsWith(Protocol.boardLogin)){
                    System.out.println("new request: " + request);
                    handleBoardLogin(conn, request);
                }
            }
        }
    }

    private static void handleTeacherLogin(Socket conn, String request){
        String data = request.replace(Protocol.teacherLogin, "");
        String[] parts = data.split(", ");

        String index = parts[0];
        String courseID = parts[1];
        String activityID = parts[2];
        int points = Integer.parseInt(parts[3]);

        Optional<Course> courseOptional = courses.stream().filter(c -> c.getCourseID().equals(courseID)).findFirst();
        if(courseOptional.isPresent()){
            Course course = courseOptional.get();
            Optional<Activity> activityOptional = course.getActivities().stream().filter(a -> a.getActivityID().equals(activityID)).findFirst();
            if(activityOptional.isPresent()){
                Activity activity = activityOptional.get();
                activity.getStudents().add(new Student(index, points));
            }else{
                Activity a = new Activity(activityID);
                a.getStudents().add(new Student(index, points));
                course.getActivities().add(a);
            }
        }else{
            Course c = new Course(courseID);
            Activity a = new Activity(activityID);
            a.getStudents().add(new Student(index, points));
            c.getActivities().add(a);
            courses.add(c);
        }
    }

    private static void handleBoardLogin(Socket conn, String request) throws IOException {
        String data = request.replace(Protocol.boardLogin, "");
        String[] parts = data.split(" ");

        String courseID = parts[0];
        String activityID = parts[1];

        PrintWriter printWriter = new PrintWriter(conn.getOutputStream());

        Optional<Course> courseOptional = courses.stream().filter(c -> c.getCourseID().equals(courseID)).findFirst();
        if(!courseOptional.isPresent()){
            printWriter.println("courseID " + courseID + " ne postoi!");
            printWriter.flush();
            printWriter.println(Protocol.endStudentsList);
            printWriter.flush();
            return;
        }
        Course course = courseOptional.get();

        Optional<Activity> activityOptional = course.getActivities().stream().filter(a -> a.getActivityID().equals(activityID)).findFirst();

        if(!activityOptional.isPresent()){
            printWriter.println("activityID " + activityID + " ne postoi!");
            printWriter.flush();
            printWriter.println(Protocol.endStudentsList);
            printWriter.flush();
            return;
        }
        Activity activity = activityOptional.get();
        printWriter.println(courseID + " " + activityID + ":");
        printWriter.flush();

        List<Student> students = activity.getStudents();
        for(int i = 0; i < students.size(); ++i){
            printWriter.println("\t" + students.get(i));
            printWriter.flush();
        }
        printWriter.println(Protocol.endStudentsList);
        printWriter.flush();
        printWriter.close();
    }
}
