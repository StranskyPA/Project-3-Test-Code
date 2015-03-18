package Networking;

import java.net.*;
import java.io.*;

import application.Controller;
import packet.Depacketizer;
import packet.Packet;
import packet.Packetizer;

public class DatagramSocketEchoerThread {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Depacketizer depacketer;
	private Packetizer packeter;
	private Controller c;
	private final static int PACKETSIZE = 512;
	
	public DatagramSocketEchoerThread(DatagramSocket newSocket) {
		socket = newSocket;
		packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);;
		packeter = new Packetizer(PACKETSIZE);
		depacketer = new Depacketizer(c);
	}
	
	public void run() {
		getNewPacket();
		System.out.println("Accepted Packet From: " + socket.getInetAddress());
		Packet newPacket = new Packet(packet);
		depacketer.depacketize(newPacket);
		
		if (packeter.hasNextPacket()) {
			try {
				socket.send(packeter.getNextPacket().makeDatagramPacket());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getNewPacket() {
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
