package org.example;

import lombok.Getter;

import javax.annotation.processing.Messager;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    @Getter
    private volatile Map<Socket, String> clients;
    private final List<ClientThread> clientThreads;
    private volatile Map<Socket, String> activeClientsInGlobalChat;

    public ChatService(Map<Socket, String> clients, List<ClientThread> clientThreads){
        this.clients = clients;
        this.clientThreads = clientThreads;
        activeClientsInGlobalChat = new HashMap<>();
    }

    public synchronized void sendMessageToGlobalChat(Socket sender, String username, String message, TypeOfMessage typeOfMessage){
        if(sender == null) return;

        try {
            PrintWriter senderOut = new PrintWriter(sender.getOutputStream(), true);
            switch (typeOfMessage){
                case JOIN:
                    this.activeClientsInGlobalChat.put(sender, username);
                    System.out.println(String.format(MessageColors.GREEN + "\n%s has joined the chat!" + MessageColors.RESET, username));
                    senderOut.println(String.format(MessageColors.GREEN + "\n%s has joined the chat!" + MessageColors.RESET, username));
                    this.activeClientsInGlobalChat.forEach((socket, string) -> {
                        if(socket != sender) {
                            PrintWriter receiverOut = null;
                            try {
                                receiverOut = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            receiverOut.println(String.format(MessageColors.GREEN + "\n%s has joined the chat!" + MessageColors.RESET, username));
                        }
                    });
                    break;
                case USER_MESSAGE:
                    System.out.println(String.format("%s: %s", username, message));
                    activeClientsInGlobalChat.forEach((socket, string) -> {
                        if(socket != sender) {
                            PrintWriter receiverOut = null;
                            try {
                                receiverOut = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            receiverOut.println(String.format("%s: %s", username, message));
                        }
                    });
                    break;
                case SYSTEM_MESSAGE:
                    System.out.println(String.format("System: %s", message));
                    activeClientsInGlobalChat.forEach((socket, string) -> {
                        if(socket != sender) {
                            PrintWriter receiverOut = null;
                            try {
                                receiverOut = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            receiverOut.println(String.format(MessageColors.BLUE + "System: %s" + MessageColors.RESET, message));
                        }
                    });
                    break;
                case LEAVE:
                    this.activeClientsInGlobalChat.remove(sender);
                    System.out.println(String.format(MessageColors.RED + "\n%s has left the chat!" + MessageColors.RESET, username));
                    senderOut.println(String.format(MessageColors.RED + "\n%s has left the chat!" + MessageColors.RESET, username));
                    activeClientsInGlobalChat.forEach((socket, string) -> {
                        if(socket != sender) {
                            PrintWriter receiverOut = null;
                            try {
                                receiverOut = new PrintWriter(socket.getOutputStream(), true);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            receiverOut.println(String.format(MessageColors.RED + "\n%s has left the chat!" + MessageColors.RESET, username));
                        }
                    });
                    break;
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendPrivateMessage(Socket fromClient, Socket toClient, ChatMessage message){
        String usernameFrom = this.clients.get(fromClient);
        String usernameTo = this.clients.get(toClient);

        ClientThread clientThreadFromSendMessage = this.clientThreads.stream().filter(clientThread -> clientThread.getUsername().equals(usernameFrom)).toList().get(0);
        ClientThread clientThreadToSendMessage = this.clientThreads.stream().filter(clientThread -> clientThread.getUsername().equals(usernameTo)).toList().get(0);

        if(clientThreadToSendMessage.getPrivateChats().get(usernameFrom) == null){
            clientThreadToSendMessage.getPrivateChats().put(usernameFrom, new ArrayList<>());
            clientThreadFromSendMessage.getPrivateChats().put(usernameTo, new ArrayList<>());
        }

        var syka = clientThreadToSendMessage.getIsInPrivateChatWithUsers();
        var d = syka.get(toClient);
        var s = syka.get(fromClient);
        if(d != null){
            if(d){
                try {
                    PrintWriter outTo = new PrintWriter(toClient.getOutputStream(), true);
                    outTo.println(String.format("%s: %s", usernameFrom, message.getMessage()));
                    //clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
                    //clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);
                } catch (IOException ex){
                    throw new RuntimeException();
                }
            } else {
//                clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
//                clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);
            }
            clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
            clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);

            return;
        }

        if(s != null){
            if(s){
                try {
                    PrintWriter outTo = new PrintWriter(toClient.getOutputStream(), true);
                    outTo.println(String.format("%s: %s", usernameFrom, message.getMessage()));
//                    clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
//                    clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);
                } catch (IOException ex){
                    throw new RuntimeException();
                }
            } else {
//                clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
//                clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);
            }
            clientThreadToSendMessage.getPrivateChats().get(usernameFrom).add(message);
            clientThreadFromSendMessage.getPrivateChats().get(usernameTo).add(message);
        }
    }

}
