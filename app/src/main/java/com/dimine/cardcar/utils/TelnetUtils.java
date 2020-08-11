package com.dimine.cardcar.utils;

import android.util.Log;

import org.apache.commons.net.telnet.TelnetClient;

import java.io.BufferedInputStream;
import java.io.PrintStream;
import java.net.SocketException;

/**
 * date   : 2019/7/10 13:54
 * desc   :Telnet操作类
 * version: 1.0
 */
public class TelnetUtils {
    private static final String TAG = TelnetUtils.class.getSimpleName();
    private TelnetClient client;
    private BufferedInputStream bis;
    private PrintStream ps;
    private char prompt = '$';
    private GetMessageListener mListener;

    public TelnetUtils() {
        client = new TelnetClient();
    }

    /**
     * 连接及登录
     *
     * @param ip       目标主机IP
     * @param port     端口号（Telnet 默认 23）
     * @param user     登录用户名
     * @param password 登录密码
     */
    public int connect(String ip, int port, String user, String password) {
        try {
            client.connect(ip, port);
            bis = new BufferedInputStream(client.getInputStream());
            ps = new PrintStream(client.getOutputStream());
            this.prompt = "admin".equals(user) ? '#' : '$';
            login(user, password);
        } catch (SocketException e) {
            Log.d("TelnetManager","SocketException"+e.getMessage());
            e.printStackTrace();
            return -1;
        } catch (Exception e) {
             Log.d("TelnetManager","Exception"+e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 登录
     *
     * @param user
     * @param password
     */
    public void login(String user, String password) {
        read("login:");
        write(user);
        read("Password:");
        write(password);
        read(prompt + " ");
    }

    /**
     * 读取返回来的数据
     *
     * @param info
     * @return
     */
    public String read(String info) {
        try {
            char lastChar = info.charAt(info.length() - 1);
            StringBuffer sb = new StringBuffer();
            char ch = (char) bis.read();
            while (true) {
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(info)) {
                        if (mListener != null) {
                            mListener.onMessage(sb.toString());
                        }
                        return sb.toString();
                    }
                }
                ch = (char) bis.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写指令
     *
     * @param instruction
     */
    public void write(final String instruction) {
        try {
            ps.println(instruction);
            ps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向目标发送命令字符串
     *
     * @param command
     * @return
     */
    public String sendCommand(String command) {
        try {
            write(command);
            return read(prompt + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        try {
            if (client != null) {
                client.disconnect();
            }
            if (bis != null) {
                bis.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface GetMessageListener {
        void onMessage(String info);
    }

    public void setListener(GetMessageListener mListener) {
        this.mListener = mListener;
    }
}
