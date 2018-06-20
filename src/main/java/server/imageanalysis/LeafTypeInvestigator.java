package server.imageanalysis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import server.imageanalysis.LeafTypes.LeafTypeName;
import server.storageservice.StorageService;

public class LeafTypeInvestigator {
	
    private final StorageService storageService;
    
    @Autowired
    public LeafTypeInvestigator(StorageService storageService) {
        this.storageService = storageService;
    }
	
	public LeafTypeName identifyLeafType(String filename) throws ReadImageException {
		
		BufferedImage greenImage = readGreenColors(filename);
		//TODO delete. just for testing:
		writeImage(greenImage);
		return LeafTypeName.Herzfoermig;
	}

	private BufferedImage readGreenColors(String filename) throws ReadImageException {
		BufferedImage image = null;
		try {
			File file = storageService.load(filename).toFile();
			image = ImageIO.read(file);
		} catch(IOException e){
			throw new ReadImageException("this is no supported image format: " + filename);
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
	        int g = (p>>8)&0xff;
	        int b = (p)&0xff;
	        
	        int h = (r + g + b) / 3;
	        if (h < 120) {
	        	r = 255;
		        g = 255;
		        b = 255;
	        } else {
	        	r = 0;
		        g = 0;
		        b = 0;
	        }

	        //replace RGB value with only green color
	        p = (a<<24) | (r<<16) | (g<<8) | b;

	        image.setRGB(x, y, p);
	      }
	    }	
	    
	    return image;
	}
	
	private void writeImage(BufferedImage image) {
		try{
			  File f = new File("C:\\Users\\RebeccaS\\Desktop\\Output.png");
			  ImageIO.write(image, "png", f);
			}catch(IOException e){
			  System.out.println(e);
			}
	}
	
/*	private File convertToFile(MultipartFile file) throws IOException {    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	} */
}
