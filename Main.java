/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omandotkom
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private final int PORT = 15049;
    private final int TIMEOUT = 10000;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Main m = new Main();

        if (args.length == 1) {
            try {
                m.init(args[0]);
            } catch (UnknownHostException ex) {
                m.msg(ex.getMessage());
            }
        } else {
            //error user must pass server or client param
            m.msg("Kesalahan, untuk lewatkan parameter server atau client pada program.");
            System.exit(-1);
        }

    }

    private void init(String type) throws UnknownHostException {
        msg("Package Sender Test by Khalid Abdurrahman");
        InetAddress inetAddress = InetAddress.getLocalHost();
        switch (type) {
            case "client":
                System.out.println("IP Address:- " + getCurrentEnvironmentNetworkIp());
                System.out.println("Host Name:- " + inetAddress.getHostName());
                client();
                break;
            case "server":
                server();
                break;
            default:
                msg("Mode tidak diketahui.");
        }
    }

    private void msg(String m) {
        //karena system.out kepanjangan
        System.out.println(m);
    }

    private void client() {
        String packet = "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";
        msg("String     : " + packet.substring(0, 10) + "..." + packet.substring(packet.length() - 10, packet.length()));
        msg("Panjang    : " + packet.length());
        System.out.print("IP Server  : ");
        String ip = System.console().readLine();
        msg("Menyambungkan ke " + ip + ":" + PORT + " T/O : " + TIMEOUT / 1000 + " detik");
        //bikin objek socket connector baru
        Socket socket = new Socket();
        InetSocketAddress isa = new InetSocketAddress(ip, PORT);

        try {
            socket.connect(isa, TIMEOUT);
            msg("Berhasil terhubung.");

            //persiapan mengirim data
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(outputStream);

            //mengirim data
            msg("Mengirim string...");
            dos.writeUTF(packet);
            dos.flush();
            dos.close();
            msg("Selesai mengirim string.");
            socket.close();
        } catch (IOException ex) {
            msg(ex.getMessage());
        }
    }

    private void server() {
        try {
            msg("Server mode listening on " + getCurrentEnvironmentNetworkIp() + ":" + PORT);
            String ip = getCurrentEnvironmentNetworkIp();
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            String packet = dataInputStream.readUTF();
            msg("String diterima");
            msg("String     : " + packet.substring(0, 10) + "..." + packet.substring(packet.length() - 10, packet.length()));
            msg("Panjang    : " + packet.length());
            socket.close();
            serverSocket.close();
        } catch (IOException ex) {
            msg(ex.getMessage());
        }
    }

    public static String getCurrentEnvironmentNetworkIp() {
        String currentHostIpAddress = null;
        if (currentHostIpAddress == null) {
            Enumeration<NetworkInterface> netInterfaces = null;
            try {
                netInterfaces = NetworkInterface.getNetworkInterfaces();

                while (netInterfaces.hasMoreElements()) {
                    NetworkInterface ni = netInterfaces.nextElement();
                    Enumeration<InetAddress> address = ni.getInetAddresses();
                    while (address.hasMoreElements()) {
                        InetAddress addr = address.nextElement();
                        //                      log.debug("Inetaddress:" + addr.getHostAddress() + " loop? " + addr.isLoopbackAddress() + " local? "
                        //                            + addr.isSiteLocalAddress());
                        if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress()
                                && !(addr.getHostAddress().indexOf(":") > -1)) {
                            currentHostIpAddress = addr.getHostAddress();
                        }
                    }
                }
                if (currentHostIpAddress == null) {
                    currentHostIpAddress = "127.0.0.1";
                }

            } catch (SocketException e) {
//                log.error("Somehow we have a socket error acquiring the host IP... Using loopback instead...");
                currentHostIpAddress = "127.0.0.1";
            }
        }
        return currentHostIpAddress;
    }

}
