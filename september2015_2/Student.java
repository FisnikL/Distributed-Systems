package september2015_2;

import java.io.IOException;
import java.net.*;
import java.util.Random;

public class Student {
    public static void main(String[] args) throws IOException {

        DatagramSocket ds = new DatagramSocket();

        InetAddress sensorDoorAddress = InetAddress.getLocalHost();
        int sensorDoorPort = 58769;

        Random random = new Random();

        for(int i = 0; i < 10; ++i){

            int rand = random.nextInt(3);

            if(rand < 2){
                byte[] buf = Protocol.studentIn.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, sensorDoorAddress, sensorDoorPort);
                ds.send(dp);
            }else{
                byte[] buf = Protocol.studentOut.getBytes();
                DatagramPacket dp = new DatagramPacket(buf, buf.length, sensorDoorAddress, sensorDoorPort);
                ds.send(dp);
            }
        }
    }
}
