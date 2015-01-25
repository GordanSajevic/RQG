package ba.bitcamp.rqg.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;

public class SocketCommunication {
	
	InputStream in;
	OutputStream out;
	
	/**
	 * Constructor with two parameters
	 * @param in
	 * @param out
	 */
	
	public SocketCommunication(InputStream in, OutputStream out) 
	{
		this.in = in;
		this.out = out;
	}
	
	/**
	 * Method for sending messages
	 * @param message
	 */
	
	public void sendMessage(String message)
	{
		byte[] bytes = message.getBytes();
		try {
			out.write(bytes.length);
			out.write(bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for receiving messages
	 * @return
	 */
	
	public String receiveMessage()
	{
		StringBuilder sb = new StringBuilder();
		try {
			int length = in.read();
			byte[] buffer = new byte[length];
			int num = 0;
			while((num += in.read(buffer)) >= 0)
			{
				sb.append(new String(buffer).replaceAll("\\s+", " "));
				if (num >= length)
				{
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	/**
	 * Method for writing message into file
	 * @param name
	 * @param str
	 * @throws IOException
	 */
	
	public void writeToFile(String name, String str) throws IOException
	{
		Date date = new Date();
		Timestamp currentTime = new Timestamp(date.getTime());
		String time = currentTime.toString(); 
		String homeDir = System.getProperty("user.home");
		String path = homeDir + File.separator + "workspace" + File.separator + "RQG" + File.separator + 
				"src" + File.separator + "ba" + File.separator + "bitcamp" + File.separator + "rqg" + 
				File.separator + "server" + File.separator + "Files" + File.separator + name;
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		out = new DataOutputStream(fos);
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append(time + "\t" + str);
		bw.close();
		fw.close();
	}
	
	/**
	 * Function returns quote on random line (1-25)
	 * @param file
	 * @return
	 * @throws IOException
	 */
	
	public String getMessage(File file, int num) throws IOException 
	{
	//	int num = (int) (Math.random() * 25 + 1);
		String str = "";
		FileInputStream fis = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		for (int i = 0; i < num; i++) 
		{
			br.readLine();
		}
		str = br.readLine().toString();
		br.close();
		return str;
	}

}
