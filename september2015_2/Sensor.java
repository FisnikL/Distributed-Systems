package september2015_2;

import java.net.InetAddress;
import java.util.Objects;

public class Sensor {
    private InetAddress address;
    private int port;
    private int numOfStudents;

    public Sensor(InetAddress address, int port) {
        this.address = address;
        this.port = port;
        this.numOfStudents = 0;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getNumOfStudents() {
        return numOfStudents;
    }

    public void setNumOfStudents(int numOfStudents) {
        this.numOfStudents = numOfStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sensor)) return false;
        Sensor sensor = (Sensor) o;
        return port == sensor.port &&
                address.equals(sensor.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }

    @Override
    public String toString() {
        return address + " " + port;
    }
}
