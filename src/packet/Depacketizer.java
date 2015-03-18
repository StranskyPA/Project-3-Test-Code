package packet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
//import java.util.PriorityQueue;

import application.Controller;

public class Depacketizer {
	private Controller GUI_Controller;
	private Hashtable<Integer, File> partialFiles;
	
	public Depacketizer(Controller c) {
		GUI_Controller = c;
		partialFiles = new Hashtable<Integer, File>();
	}
	
	public void depacketize(Packet p) {
		byte[] data = p.getAllData();
		File f;
		
		if(p.getPart() > 0) {
			f = partialFiles.get(p.getID());
		} else {
			f = new File(new String(data));
			partialFiles.put(p.getID(), f);
		}
		
		try {
			FileWriter writer = new FileWriter(f);
			String tempString = new String(data);
			writer.write(tempString);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(p.getPart() == p.getNumParts()) {
			GUI_Controller.receiveMessage(f);
			partialFiles.remove(p.getID());
		}
	}
}
