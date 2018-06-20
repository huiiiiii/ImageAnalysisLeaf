package server.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import server.imageanalysis.LeafTypeInvestigator;
import server.imageanalysis.LeafTypes.LeafTypeName;
import server.imageanalysis.ReadImageException;
import server.storageservice.StorageFileNotFoundException;
import server.storageservice.StorageService;

@RestController
public class LeafController {
	
    private final StorageService storageService;
    private final LeafTypeInvestigator leafTypeInvestigator;

    @Autowired
    public LeafController(StorageService storageService, LeafTypeInvestigator leafTypeInvestigator) {
        this.storageService = storageService;
        this.leafTypeInvestigator = leafTypeInvestigator;
    }

/*    @RequestMapping("/leaftype")
    public LeafTypeName getLeafType(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.Herzfoermig;
    } */
    
    @RequestMapping("/leaftypelist")
    public LeafTypeName[] getLeafTypeList(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.values();
    }
    
    @PostMapping("/leaftype")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file); 
        LeafTypeName leafTypeName = null;
        try {
        	leafTypeName = leafTypeInvestigator.identifyLeafType(file);

        } catch(ReadImageException e) {
        	return e.getMessage();
        }
         
        return "You successfully uploaded " + file.getOriginalFilename() + " --> leafe type is: " + leafTypeName;
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}