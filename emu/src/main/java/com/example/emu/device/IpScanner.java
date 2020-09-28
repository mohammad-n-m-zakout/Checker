package com.example.emu.device;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IpScanner {
    private final static String TAG = IpScanner.class.getSimpleName();

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void startScan(final OnScanListener listener) {
        final List<String> ipList = new ArrayList<>();
        final Map<String, String> map = new HashMap<>();

        String hostIP = getHostIP();
        int lastIndexOf = hostIP.lastIndexOf(".");
        final String substring = hostIP.substring(0, lastIndexOf + 1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                DatagramPacket dp = new DatagramPacket(new byte[0], 0, 0);
                DatagramSocket socket;
                try {
                    socket = new DatagramSocket();
                    int position = 2;
                    while (position < 255) {
                        Log.e(TAG, "run: udp-" + substring + position);
                        dp.setAddress(InetAddress.getByName(substring + position));
                        socket.send(dp);
                        position++;
                        if (position == 125) {
                            socket.close();
                            socket = new DatagramSocket();
                        }
                    }
                    socket.close();
                    execCatForArp(listener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void execCatForArp(final OnScanListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Map<String, String> map = new HashMap<>();
                    Process exec = Runtime.getRuntime().exec("cat proc/net/arp");
                    InputStream is = exec.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Log.e(TAG, "run: " + line);
                        if (!line.contains("00:00:00:00:00:00") && !line.contains("IP")) {
                            String[] split = line.split("\\s+");
                            map.put(split[3], split[0]);
                        }
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.scan(map);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i(TAG, "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }


    public interface OnScanListener {
        void scan(Map<String, String> resultMap);
    }

}
