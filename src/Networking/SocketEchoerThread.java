package Networking;

import java.net.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.io.*;

import javafx.scene.text.Text;
import application.Model;

public class SocketEchoerThread {
	private Socket textSocket;
	private CustomServer server;
	public static ArrayBlockingQueue<String> channel = new ArrayBlockingQueue<String>(20, true);
	private Model model;
	//private CustomServer server;
	
	public SocketEchoerThread(Socket socket, CustomServer server, Model model) {
		this.model = model;
		this.server = server;
		this.textSocket = socket;
	}
	
	public void run() throws InterruptedException {
		try {
            BufferedReader responses =  new BufferedReader (new InputStreamReader(textSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(textSocket.getOutputStream());

            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){
            	System.out.println("Not ready");
            }
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
            System.out.println("From: " + textSocket.getInetAddress() + ": " + sb);
            channel.put(sb.toString());
            Text text = new Text(sb.toString());
            model.receiveMessage(text);
        
            writer.print(sb);
            writer.flush();
            server.listen();
            //textSocket.close(); 
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
	}
}
