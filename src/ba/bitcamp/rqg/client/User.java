package ba.bitcamp.rqg.client;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import ba.bitcamp.rqg.server.SocketCommunication;;

public class User {
	
	//Client class
	
	public static final String host = "localhost";
	public static final int port = 1717;
			
	public static void connect()
	{
		//Connecting to server
		
		Scanner input = new Scanner(System.in);
		try {
			@SuppressWarnings("resource")
			Socket user = new Socket(host, port);
			InputStream in = user.getInputStream();
			OutputStream out = user.getOutputStream();
			
			//chat is an object with functions for sending and receiving messages
			
			SocketCommunication chat = new SocketCommunication(in, out);
			System.out.println("Password: \n");
			String userMessage = input.nextLine();
			chat.sendMessage(userMessage);
			int i=2;
			while (i<25)
			{
				//In loop, user receives message from generator, and than sends message.
				//Every message is from quotes.txt file
				
				String generatorMessage = chat.receiveMessage();
				System.out.println("Server: " + generatorMessage);
				String homeDir = System.getProperty("user.home");
				String path = homeDir + File.separator + "workspace" + File.separator + "RQG" + File.separator + 
						"src" + File.separator + "ba" + File.separator + "bitcamp" + File.separator + "rqg" + 
						File.separator + "server" + File.separator + "Files" + File.separator + "quotes.txt";
				File file = new File(path);
				userMessage = chat.getMessage(file, i);
				System.out.println("User:" + userMessage);
				chat.sendMessage(userMessage);
				i += 2;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		connect();
	}

}
