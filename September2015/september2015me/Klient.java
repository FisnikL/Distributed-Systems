package september2015me;

import java.util.Objects;

public class Klient {
    private String ip;
    private int port;

    public Klient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Klient)) return false;
        Klient klient = (Klient) o;
        return port == klient.port &&
                Objects.equals(ip, klient.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
