package ba.bitcamp.rqg.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Generator {

	//Server class
	
	public static final int port = 1717;
	public static final String password = "youshallnotpass";
	
	public static void startServer() throws IOException
	{
		//Creating connection
		ServerSocket generator = new ServerSocket(port);
		try {
			while (true)
			{ 
				System.out.println("Waiting...");
				Socket user = generator.accept();
				InputStream in = user.getInputStream();
				OutputStream out = user.getOutputStream();
				int i = 1;
				//chat is an object with functions for sending and receiving messages
				
				SocketCommunication chat = new SocketCommunication(in, out);
				String userPassword = chat.receiveMessage();
				
				//Password check. If password is true, generator can send message
				
				if(userPassword.equals(password))
				{
					while (i<25)
					{
						
						//In loop, generator sends message first, and receives message from user
						//Every message is from quotes.txt file
						
						String homeDir = System.getProperty("user.home");
						String path = homeDir + File.separator + "workspace" + File.separator + "RQG" + File.separator + 
								"src" + File.separator + "ba" + File.separator + "bitcamp" + File.separator + "rqg" + 
								File.separator + "server" + File.separator + "Files" + File.separator + "quotes.txt";
						File file = new File(path);
						String generatorMessage = chat.getMessage(file, i);
						System.out.println("Server: " + generatorMessage);
						chat.sendMessage(generatorMessage);
						String userMessage = chat.receiveMessage();
						System.out.println("User: " + userMessage);
						i += 2;
					}
				}
				
				//If password is incorrect, connection is closed. 
				//Every wrong password is saved in auth_log.txt file
				
				else
				{
					chat.writeToFile("auth_log.txt", userPassword);
					System.out.println("Password is not correct!");
					generator.close();
					break;
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		//Function startServer is called in main function
		
		try {
			startServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
