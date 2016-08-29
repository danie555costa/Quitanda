package com.st.ggviario.client.model;

/**
 * Created by xdata on 8/9/16.
 */
public class Computers implements CharSequence
{
    String userName;
    String ip;
    boolean connected;

    public Computers(String userName, String ip) {
        this.userName = userName;
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public String getIp() {
        return ip;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int beginIndex, int endIndex) {
        return this.toString().subSequence(beginIndex, endIndex);
    }

    @Override
    public String toString() {
        return this.userName;
    }
}
