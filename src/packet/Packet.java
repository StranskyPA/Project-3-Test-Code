package packet;

import java.net.DatagramPacket;

public class Packet{
	private byte[] bytes;
	
	private static final int ID_IDX = 0;
	private static final int PART_IDX = 4;
	private static final int NUM_PARTS_IDX = 8;
	private static final int DATA_SIZE_IDX = 12;
	private static final int DATA_START_IDX = 16;
	
	public static final int HEADER_SIZE = DATA_START_IDX;

	public Packet(int id, int part, int totalNumParts, int dataSize, byte[] data) {
		bytes = new byte[dataSize + Packet.HEADER_SIZE];
		
		bytes[ID_IDX] = (byte)(id >> 24);
		bytes[ID_IDX+1] = (byte)(id >> 16);
		bytes[ID_IDX+2] = (byte)(id >> 8);
		bytes[ID_IDX+3] = (byte)(id);
		
		bytes[PART_IDX] = (byte)(part >> 24);
		bytes[PART_IDX+1] = (byte)(part >> 16);
		bytes[PART_IDX+2] = (byte)(part >> 8);
		bytes[PART_IDX+3] = (byte)(part);
		
		bytes[NUM_PARTS_IDX] = (byte)(totalNumParts >> 24);
		bytes[NUM_PARTS_IDX+1] = (byte)(totalNumParts >> 16);
		bytes[NUM_PARTS_IDX+2] = (byte)(totalNumParts >> 8);
		bytes[NUM_PARTS_IDX+3] = (byte)(totalNumParts);
		
		bytes[DATA_SIZE_IDX] = (byte)(dataSize >> 24);
		bytes[DATA_SIZE_IDX+1] = (byte)(dataSize >> 16);
		bytes[DATA_SIZE_IDX+2] = (byte)(dataSize >> 8);
		bytes[DATA_SIZE_IDX+3] = (byte)(dataSize);
		
		for(int i=DATA_START_IDX; i < dataSize; i++) {
			bytes[i] = data[i - DATA_START_IDX];
		}
	}
	
	public Packet(DatagramPacket p) {
		bytes = p.getData();
	}
	
	public int length() {
		return bytes.length;
	}
	
	public int getID() {
		int id = bytes[ID_IDX];
		id = (id << 8) + bytes[ID_IDX+1];
		id = (id << 8) + bytes[ID_IDX+2];
		id = (id << 8) + bytes[ID_IDX+3];
		return id;
	}
	
	public int getPart() {
		int part = bytes[PART_IDX];
		part = (part << 8) + bytes[PART_IDX+1];
		part = (part << 8) + bytes[PART_IDX+2];
		part = (part << 8) + bytes[PART_IDX+3];
		return part;
	}
	
	public int getNumParts() {
		int numParts = bytes[NUM_PARTS_IDX];
		numParts = (numParts << 8) + bytes[NUM_PARTS_IDX+1];
		numParts = (numParts << 8) + bytes[NUM_PARTS_IDX+2];
		numParts = (numParts << 8) + bytes[NUM_PARTS_IDX+3];
		return numParts;
	}
	
	public int getSize() {
		int size = bytes[DATA_SIZE_IDX];
		size = (size << 8) + bytes[DATA_SIZE_IDX+1];
		size = (size << 8) + bytes[DATA_SIZE_IDX+2];
		size = (size << 8) + bytes[DATA_SIZE_IDX+3];
		return size;
	}
	
	public byte[] getAllData() {
		byte[] data = new byte[getSize()];
		
		for(int i=0; i<getSize(); i++) {
			data[i] = bytes[i+DATA_START_IDX];
		}
		
		return data;
	}
	
	public DatagramPacket makeDatagramPacket() {
		return new DatagramPacket(bytes, bytes.length);
	}
}
