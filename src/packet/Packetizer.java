package packet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Packetizer {
	private PriorityQueue<Packet> packetsOut;
	private int packetSize;
	
	public Packetizer(int packetSize) {
		packetsOut = new PriorityQueue<Packet>();
		this.packetSize = packetSize;
	}
	
	public boolean hasNextPacket() {
		return !packetsOut.isEmpty();
	}
	
	public Packet getNextPacket() {
		return packetsOut.remove();
	}
	
	public void packetize(File f) {
		FileReader reader;
		int numParts = (int)(f.length()/(packetSize-Packet.HEADER_SIZE));
		try {
			reader = new FileReader(f);
			byte[] data = new byte[packetSize];
			
			String name = f.getName();
			data = name.getBytes();
			packetsOut.add(new Packet(f.hashCode(), 0, numParts, name.length(), data));
			
			for(int i=1; i <= numParts; i++) {
				char[] readerBuffer = new char[packetSize];
				int size = reader.read(readerBuffer);
				data = new String(readerBuffer).getBytes();
				
				if(size != -1)
					packetsOut.add(new Packet(f.hashCode() , i, numParts, data.length, data));
				else
					packetsOut.add(new Packet(f.hashCode() , i, numParts, (int)(f.length()%(packetSize-Packet.HEADER_SIZE)), data));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
