package september2015_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<Sensor> sensors = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        DatagramSocket ds = new DatagramSocket(Protocol.serverPort);
        System.out.println("Server listening on port " + ds.getLocalPort());
        while(true){
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);
            System.out.println("new client");
            String request = new String(dp.getData(), 0, dp.getLength());

            if(request.equals(Protocol.sensorDoorLogin)){
                System.out.println("sensor login request");
                handleSensorDoorLogin(dp);
            }
            else if(request.equals(Protocol.sensorDoorLogout)){
                System.out.println("sensor logout request");
                handleSensorDoorLogout(dp);
            }
            else if(request.startsWith(Protocol.currentNumOfStudents)){
                System.out.println("sensor currentNumOfStudents data: " + request);
                handleCurrentNumOfStudents(dp);
            }
            else if(request.startsWith(Protocol.sensorDoorsListRequest)){
                System.out.println("sensorDoorsListRequest");
                handleSensorDoorsListRequest(ds, dp);
            }
            else if(request.startsWith(Protocol.numOfStudentsInRoom)){
                System.out.println("numOfStudentsInRoom request");
                handleNumOfStudentsInRoom(ds, dp);
            }
        }
    }

    private static void handleSensorDoorLogin(DatagramPacket dp){
        InetAddress address = dp.getAddress();
        int port = dp.getPort();
        Sensor sensor = new Sensor(address, port);
        sensors.add(sensor);
        System.out.println("Sensor [" + sensor + "] added");
    }

    private static void handleSensorDoorLogout(DatagramPacket dp){
        InetAddress address = dp.getAddress();
        int port = dp.getPort();
        Sensor sensor = new Sensor(address, port);
        boolean removed = sensors.remove(sensor);
        if(removed){
            System.out.println("[" + sensor + "] removed");
        }else{
            System.out.println("Removing failed");
        }
    }

    private static void handleCurrentNumOfStudents(DatagramPacket dp){
        InetAddress address = dp.getAddress();
        int port = dp.getPort();

        Sensor sensor = new Sensor(address, port);
        int index = sensors.indexOf(sensor);
        if(index != -1){
            String request = new String(dp.getData(), 0, dp.getLength());
            int numOfStudents = Integer.parseInt(request.replace(Protocol.currentNumOfStudents, ""));
            Sensor s = sensors.get(index);
            s.setNumOfStudents(numOfStudents);
        }else{
            // Greshka
        }
    }

    private static void handleSensorDoorsListRequest(DatagramSocket ds, DatagramPacket dp) throws IOException {
        InetAddress address = dp.getAddress();
        int port = dp.getPort();

        for(Sensor sensor: sensors){
            byte[] buf = sensor.toString().getBytes();
            DatagramPacket dp1 = new DatagramPacket(buf, buf.length, address, port);
            ds.send(dp1);
        }
        byte[] buf = Protocol.sensorDoorsListResponseEnd.getBytes();
        DatagramPacket dp1 = new DatagramPacket(buf, buf.length, address, port);
        ds.send(dp1);
    }

    private static void handleNumOfStudentsInRoom(DatagramSocket ds, DatagramPacket dp) throws IOException {
        InetAddress address = dp.getAddress();
        int port = dp.getPort();

        String request = new String(dp.getData(), 0, dp.getLength());
        String sensor = request.replace(Protocol.numOfStudentsInRoom, "");
        String[] parts = sensor.split(" ");

        InetAddress add = InetAddress.getLocalHost(); // parts[0];
        int p = Integer.parseInt(parts[1]);

        Sensor s = new Sensor(add, p);
        int index = sensors.indexOf(s);

        if(index == -1){
            byte[] buf = Protocol.sensorDoesntExistAnymore.getBytes();
            DatagramPacket dp1 = new DatagramPacket(buf, buf.length, address, port);
            ds.send(dp1);
        }else{
            Sensor sensor1 = sensors.get(index);
            byte[] buf = (sensor1.getAddress() + " " + sensor1.getPort() + " " + sensor1.getNumOfStudents()).getBytes();
            DatagramPacket dp1 = new DatagramPacket(buf, buf.length, address, port);
            ds.send(dp1);
        }
    }
}
