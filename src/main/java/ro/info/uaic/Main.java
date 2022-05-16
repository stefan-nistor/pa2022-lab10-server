package ro.info.uaic;

import socket.SocketManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SocketManager socketManager = new SocketManager();
        socketManager.run();
    }
}