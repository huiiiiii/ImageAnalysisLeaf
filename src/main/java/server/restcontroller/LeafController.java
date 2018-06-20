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


import server.imageanalysis.LeafTypes.LeafTypeName;
import server.storageservice.StorageFileNotFoundException;
import server.storageservice.StorageService;

@RestController
public class LeafController {
	
    private final StorageService storageService;

    @Autowired
    public LeafController(StorageService storageService) {
        this.storageService = storageService;
    }

    @RequestMapping("/leaftype")
    public LeafTypeName getLeafType(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.Herzfoermig;
    }
    
    @RequestMapping("/leaftypelist")
    public LeafTypeName[] getLeafTypeList(@RequestParam(value="name", defaultValue="World") String name) {
        return LeafTypeName.values();
    }
    
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}