package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MultiThreadedServer {
    static boolean stop = false;
    static Map<Socket, String> clientSockets;
    static List<ClientThread> clientThreads;

    public static void main(String[] args) {
        clientSockets = new HashMap<>();
        clientThreads = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(8124)) {
            ChatService chatService = new ChatService(clientSockets, clientThreads);
            while (!stop) {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");
                ClientThread clientThread = new ClientThread(clientSocket, chatService);

                for (ClientThread thread : clientThreads) {
                    clientSockets.forEach((socket, string) -> {
                        thread.getIsInPrivateChatWithUsers().put(socket, false);
                    });
                }

                clientThreads.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
