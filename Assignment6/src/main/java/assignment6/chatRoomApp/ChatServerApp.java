package assignment6.chatRoomApp;

/**
 * Represents the chat room application which helps to set up chat room
 */
public class ChatServerApp {

  /**
   * Main method which helps to start the chat room application
   *
   * @param args arguments
   */
  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer();
    chatServer.start();
  }
}
