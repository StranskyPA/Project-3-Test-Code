package packet;

import static org.junit.Assert.*;

import org.junit.Test;

public class PacketTest {
	private final int ID = 1;
	private final int PART = 2;
	private final int NUM_PARTS = 3;
	private final int DATA_SIZE = 4;
	private final byte[] DATA = {0,1,2,3};
	private final byte[] OVERSIZED_DATA = {0,1,2,3,4};

	@Test
	public void testID() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, DATA);
		assertEquals(ID, testPacket.getID());
	}
	
	@Test
	public void testPart() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, DATA);
		assertEquals(PART, testPacket.getPart());
	}
	
	@Test
	public void testNumParts() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, DATA);
		assertEquals(NUM_PARTS, testPacket.getNumParts());
	}
	
	@Test
	public void testSize() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, DATA);
		assertEquals(DATA_SIZE, testPacket.getSize());
	}
	
	@Test
	public void testData() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, DATA);
		
		byte[] packetData = testPacket.getAllData();
		
		for(int i=0; i<packetData.length; i++) {
			//assertEquals(DATA[i], packetData[i]);
			System.out.println(DATA[i] + " :: " + packetData[i]);
		}
	}
	
	@Test
	public void testOversizedData() {
		Packet testPacket = new Packet(ID, PART, NUM_PARTS, DATA_SIZE, OVERSIZED_DATA);
		
		byte[] packetData = testPacket.getAllData();
		
		for(int i=0; i<packetData.length; i++) {
			//assertEquals(DATA[i], packetData[i]);
			System.out.println(DATA[i] + " :: " + packetData[i]);
		}
	}
}
