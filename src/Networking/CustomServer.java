package Networking;

import java.net.*;
import java.io.*;

import application.Main;
import application.Model;

public class CustomServer extends Thread{
	private DatagramSocket socket;
	//private DatagramPacket packet;
	private ServerSocket textSocket;
	//private final static int PACKETSIZE = 512;
	Main main = new Main();
	Model model;
	
	public CustomServer(int port, Main main, Model model) throws IOException {
		this.model = model;
		this.main = main;
		textSocket = new ServerSocket(port);
		socket = new DatagramSocket(port);
		//packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
		System.out.println("Server IP address: " + socket.getLocalSocketAddress());
	}
	
	public void listen() throws IOException, InterruptedException {
		for (;;) {
			System.out.println("Attempting to Start Server");
			
			Socket s = textSocket.accept();
			SocketEchoerThread textEchoer = new SocketEchoerThread(s, this, model);
			System.out.println("Accepted Socket Connection From: " + s.getInetAddress());
			textEchoer.run();
			
			DatagramSocketEchoerThread packetEchoer = new DatagramSocketEchoerThread(socket);
			System.out.println("Accepted DatagramSocket Connection From: " + socket.getInetAddress());
			packetEchoer.run();
		}
	}
	
	public void run() {
		try {
			System.out.println("Running");
			listen();
		}catch (IOException e) {
			System.out.println("Caught exception");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			textSocket.close();
			listen();
			System.out.println("Re-using?");
		} catch (IOException e) {
			System.out.println("Caught exception 2222");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public static void main(String[] args) throws IOException, InterruptedException {
		CustomServer s = new CustomServer(8888);
		s.listen();
	}
	*/
}
