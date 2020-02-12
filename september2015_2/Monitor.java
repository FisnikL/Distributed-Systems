package september2015_2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

public class Monitor {

    private static DatagramSocket ds;
    private static List<String> sensors = new ArrayList();

    public static void main(String[] args) throws IOException {
        ds = new DatagramSocket();

        requestForListOfLoggedInSensors();

        MonitorWorker monitorWorker = new MonitorWorker(ds, sensors);
        monitorWorker.start();

    }

    private static void requestForListOfLoggedInSensors() throws IOException {
        byte[] buf = Protocol.sensorDoorsListRequest.getBytes();
        DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress, Protocol.serverPort);
        ds.send(dp);
        handleListOfLoggedInSensorsResponse();
    }

    private static void handleListOfLoggedInSensorsResponse() throws IOException {
        while(true){
            byte[] buf = new byte[256];
            DatagramPacket dp = new DatagramPacket(buf, buf.length);
            ds.receive(dp);

            String response = new String(dp.getData(), 0, dp.getLength());
            if(response.equals(Protocol.sensorDoorsListResponseEnd)){
                break;
            }
            sensors.add(response);
        }
    }
}

class MonitorWorker extends Thread{
    private List<String> sensors;
    private DatagramSocket ds;

    public MonitorWorker(DatagramSocket ds, List<String> sensors){
        this.sensors = sensors;
        this.ds = ds;
    }

    @Override
    public void run() {
        int i = 0;
        while(true){
            byte[] buf = (Protocol.numOfStudentsInRoom + sensors.get(i)).getBytes();

            DatagramPacket dp = new DatagramPacket(buf, buf.length, Protocol.serverAddress,  Protocol.serverPort);

            try {
                ds.send(dp);

                buf = new byte[256];
                dp = new DatagramPacket(buf, buf.length);
                ds.receive(dp);

                String response = new String(dp.getData(), 0, dp.getLength());
                if(response.contains(Protocol.sensorDoesntExistAnymore)){
                    // SKIP
                }else{
                    System.out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            i = (i+1) % sensors.size();
        }
    }
}
