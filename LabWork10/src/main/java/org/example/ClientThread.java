package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.*;

public class ClientThread extends Serializable {
    private Socket socket = null;
    private final ChatService chatService;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private Map<String, List<ChatMessage>> privateChats;

    @Getter
    @Setter
    private boolean isAdmin;

    @Getter
    @Setter
    private Map<Socket, Boolean> isInPrivateChatWithUsers;

    public ClientThread(Socket socket, ChatService chatService) {
        this.socket = socket;
        this.chatService = chatService;
        privateChats = new HashMap<>();
        this.isInPrivateChatWithUsers = new HashMap<>();
        this.chatService.getClients().forEach((socket1, string) -> {
            this.isInPrivateChatWithUsers.put(socket1, false);
        });
        isAdmin = false;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("Hello, client!");
            out.println("Write you username so we can identify yo bitch ass");
            this.username = in.readLine();
            this.chatService.getClients().put(this.socket, username);
            boolean stop = false;
            while (!stop) {
                out.println("Do you want to be admin? Y/N");
                String option = in.readLine();

                if (option.equalsIgnoreCase("y")) {
                    System.out.println(String.format("User: '%s' wants to be an admin. 1 to approve. 2 to decline ", this.username));
                    String answerToAdminRequest = scanner.nextLine();
                    if (answerToAdminRequest.equals("1")) {
                        this.isAdmin = true;
                        out.println(MessageColors.GREEN + "You're admin request was approved. You are admin now" + MessageColors.RESET);
                        break;
                    } else if (answerToAdminRequest.equals("2")) {
                        out.println(MessageColors.RED + "You're admin request was declined." + MessageColors.RESET);
                        break;
                    }
                } else if (option.equalsIgnoreCase("n")) {
                    break;
                }

            }


            while (!stop) {
                out.println("1 - global chat");
                out.println("2 - private chat with someone");
                out.println("4 - exit chat system");
                String option = in.readLine();
                boolean isSystem = false;
                switch (option) {
                    case "1" -> {
                        if (isAdmin) {
                            out.println("Do you want to send messages anonymously as system?  Y/N");
                            String optionToSentMessagesAsSystem = in.readLine();
                            while (true) {
                                if (optionToSentMessagesAsSystem.equalsIgnoreCase("y")) {
                                    isSystem = true;
                                    break;
                                } else if (optionToSentMessagesAsSystem.equalsIgnoreCase("n")) {
                                    isSystem = false;
                                    break;
                                } else {
                                    out.println("Wrong input!");
                                    continue;
                                }
                            }
                        }
                        if(isSystem){
                            this.chatService.sendMessageToGlobalChat(socket, "System", "", TypeOfMessage.JOIN);
                            runGlobalChat(out, in, true);
                            this.chatService.sendMessageToGlobalChat(socket, "System", "", TypeOfMessage.LEAVE);
                        } else {
                            this.chatService.sendMessageToGlobalChat(socket, this.username, "", TypeOfMessage.JOIN);
                            runGlobalChat(out, in, false);
                            this.chatService.sendMessageToGlobalChat(socket, this.username, "", TypeOfMessage.LEAVE);
                        }

                    }
                    case "2" -> runPrivateChat(out, in);
                    case "4" -> {
                        this.chatService.getClients().remove(this.socket);
                        socket.close();
                    }
                    default -> {
                        out.println("Wrong input! Try again");
                        continue;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Socket getUser(PrintWriter out, BufferedReader in) {
        List<Socket> sockets = new ArrayList<>();
        final int[] i = {0};
        this.chatService.getClients().forEach((clientSocket, username) -> {
            if (clientSocket != this.socket) {
                sockets.add(clientSocket);
                out.println(String.format("%s - %s", i[0], username));
                i[0]++;
            }

        });
        while (true) {
            try {
                String userOptionAsString = in.readLine();
                int userOption = Integer.parseInt(userOptionAsString);
                if (userOption < 0 || userOption > this.chatService.getClients().size()) {
                    System.out.println(MessageColors.RED + "Wrong input for option of user. Try Again!" + MessageColors.RESET);
                    continue;
                }
                return sockets.get(userOption);
            } catch (NumberFormatException ex) {
                System.out.println(MessageColors.RED + "Wrong input" + MessageColors.RESET);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void runGlobalChat(PrintWriter out, BufferedReader in, boolean isSystem) throws IOException {
        out.println("Write message or write 'q' to exit to menu");
        while (true) {
            if (isSystem) {
                out.print(MessageColors.BLUE + "SYSTEM:" + MessageColors.RESET);
            } else {
                out.print(this.username + ": ");
            }
            out.flush();
            String clientInput = in.readLine();

            if (clientInput.equals("q")) {
                break;
            }
            if (isSystem) {
                this.chatService.sendMessageToGlobalChat(this.socket, this.username, clientInput, TypeOfMessage.SYSTEM_MESSAGE);
            } else {
                this.chatService.sendMessageToGlobalChat(this.socket, this.username, clientInput, TypeOfMessage.USER_MESSAGE);
            }
        }
    }

    public void runPrivateChat(PrintWriter out, BufferedReader in) throws IOException {
        Socket chosenUser = getUserToPrivateChatWith(out, in);
        this.isInPrivateChatWithUsers.put(chosenUser, true);
        showMessages(out, in, chosenUser);
        while (true) {
            out.print(this.username + ": ");
            out.flush();
            String clientInput = in.readLine();

            if (clientInput.equals("q")) {
                this.isInPrivateChatWithUsers.put(chosenUser, false);
                break;
            }

            this.chatService.sendPrivateMessage(this.socket, chosenUser, new ChatMessage(this.username, clientInput));

        }

    }

    private void showMessages(PrintWriter out, BufferedReader in, Socket chosenUser) {
        String usernameOfOppositeOfPrivateChat = this.chatService.getClients().get(chosenUser);

        var messages = this.privateChats.get(usernameOfOppositeOfPrivateChat);

        if (messages != null) {
            messages.forEach(chatMessage -> {
                out.println(String.format("%s: %s", chatMessage.getAuthor(), chatMessage.getMessage()));
            });
        }

    }

    public Socket getUserToPrivateChatWith(PrintWriter out, BufferedReader in) {
        out.println("Choose the user to private chat with");
//        List<Socket> sockets = new ArrayList<>();
//        final int[] i = {0};
//        this.chatService.getClients().forEach((clientSocket, username) -> {
//            if(clientSocket != this.socket){
//                sockets.add(clientSocket);
//                out.println(String.format("%s - %s", i[0], username));
//                i[0]++;
//            }
//
//        });
//        while(true){
//            try {
//                String userOptionAsString = in.readLine();
//                int userOption = Integer.parseInt(userOptionAsString);
//                if(userOption < 0 || userOption > this.chatService.getClients().size()){
//                    System.out.println(MessageColors.RED + "Wrong input for option of user. Try Again!" + MessageColors.RESET);
//                    continue;
//                }
//                return sockets.get(userOption);
//            } catch (NumberFormatException ex){
//                System.out.println(MessageColors.RED + "Wrong input" + MessageColors.RESET);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }

        return getUser(out, in);
    }
}
