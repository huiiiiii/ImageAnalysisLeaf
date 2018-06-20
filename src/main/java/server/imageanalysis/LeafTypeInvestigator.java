package server.imageanalysis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import server.imageanalysis.LeafTypes.LeafTypeName;

public class LeafTypeInvestigator {
	
	public LeafTypeName identifyLeafType(MultipartFile file) throws ReadImageException {
		
		BufferedImage greenImage = readGreenColors(file);
		return LeafTypeName.Herzfoermig;
	}

	private BufferedImage readGreenColors(MultipartFile file) throws ReadImageException {
		BufferedImage image = null;
		try {
			image = ImageIO.read(convertToFile(file));
		} catch(IOException e){
			throw new ReadImageException("this is no supported image format: " + file.getOriginalFilename());
		}
		
		//get image width and height
	    int width = image.getWidth();
	    int height = image.getHeight();

	    //get green portion
	    for(int y = 0; y < height; y++){
	      for(int x = 0; x < width; x++){
	        int p = image.getRGB(x,y);

	        int a = (p>>24)&0xff;
	        int r = (p>>16)&0xff;
	        int g = 0;
	        int b = 0;

	        //replace RGB value with only green color
	        p = (a<<24) | (r<<16) | (g<<8) | b;

	        image.setRGB(x, y, p);
	      }
	    }	
	    
	    return image;
	}
	
	private File convertToFile(MultipartFile file) throws IOException {    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
}
