package kol1_2018_secondTry;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Protocol {
    public static final int centralServerPort = 8080;
    public static String centralServerAddress;

    static {
        try {
            centralServerAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    // KVSServer
    public static final String loginKVSServer = "loginKVSServer "; // loginKVSServer + listeningPort;
    public static final String logoutKVSServer = "logoutKVSServer "; // logoutKVSServer + KVSServerKey;
    public static final String requestForRandomKVSServer = "requestForRandomKVSServer "; // logoutKVSServer + KVSServerKey
    public static final String requestForKeyValuePairsList = "requestForKeyValuePairsList";

    // Client
    public static final String randomKVSServerForClient = "randomKVSServerForClient";
    public static final String keyValuePairFromKVSServerRequest = "keyValuePairFromKVSServerRequest "; // keyValuePairFromKVSServerRequest + keyValuePair
}
