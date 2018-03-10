package Files;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class AddImage {
	static Map<String, String> pathMap = new HashMap<String, String>();

	public void addMap(String songName, String songPath) {
		pathMap.put(songName, songPath);
	}

	public void clearAll(Map map) {
		map.clear();
	}

	public void showList() {
		for (Map.Entry<String, String> entry : pathMap.entrySet())
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

	}

	public String getValue(String key) {
		return pathMap.get(key);
	}

	// https://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html
	// https://www.programcreek.com/2009/02/java-convert-image-to-byte-array-convert-byte-array-to-image/
	public static byte[] convertImage(String fileName) throws IOException {
		String fileDir = pathMap.get(fileName);
		File fin = new File(fileDir);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		BufferedImage img = ImageIO.read(new File(fileDir, fileName));
		ImageIO.write(img, "jpg", baos);
		baos.flush();
		String base64String = Base64.encode(baos.toByteArray());
		baos.close();

		return  Base64.decode(base64String);

	}
	
	public static void covertByteArray(byte[] bytearray) throws IOException {
		BufferedImage imag=ImageIO.read(new ByteArrayInputStream(bytearray));
		ImageIO.write(imag, "jpg", new File("new.jpg"));
	}

}