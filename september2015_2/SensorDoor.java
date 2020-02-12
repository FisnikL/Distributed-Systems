package september2015_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SensorDoor {
    public static DatagramSocket ds;
    public static int numberOfStudents = 0;
    public static void main(String[] args) throws IOException {
        ds = new DatagramSocket(); // For testing we will use 8081
        System.out.println("Sensor Door is listening on Port " + ds.getLocalPort());
        loginRequest();
        handleCurrentNumOfStudents();
        // logoutRequest();
    }

    private static void loginRequest(){
        byte[] buf = Protocol.sensorDoorLogin.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress, Protocol.serverPort);
        try {
            ds.send(dp);
            System.out.println("Login request sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void logoutRequest(){
        byte[] buf = Protocol.sensorDoorLogout.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress, Protocol.serverPort);
        try {
            ds.send(dp);
            System.out.println("Logout request sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleCurrentNumOfStudents() throws IOException {
        while(true){
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress, Protocol.serverPort);
            ds.receive(dp);
            System.out.println("new request");
            String request = new String(dp.getData(), 0, dp.getLength());
            if(request.equals(Protocol.studentIn)){
                numberOfStudents++;
                System.out.println("numOfStudents++");
                sendCurrentNumOfStudents();
            }
            else if(request.equals(Protocol.studentOut)){
                numberOfStudents--;
                System.out.println("numOfStudents--");
                sendCurrentNumOfStudents();
            }else{
                // Prati greshka
            }
        }
    }

    private static void sendCurrentNumOfStudents() throws IOException {
        byte[] buf = (Protocol.currentNumOfStudents + Integer.toString(numberOfStudents)).getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress, Protocol.serverPort);
        ds.send(dp);
        System.out.println("Current number of students (" + numberOfStudents +") sent");
    }
}
