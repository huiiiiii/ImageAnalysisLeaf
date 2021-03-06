package server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import server.imageanalysis.LeafTypeInvestigator;
import server.storageservice.StorageProperties;
import server.storageservice.StorageService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ImageAnalysisLeafApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageAnalysisLeafApplication.class, args);
	}
	
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
    
    @Bean
    LeafTypeInvestigator leafTypeInvestigator(StorageService storageService) {
    	return new LeafTypeInvestigator(storageService);
    }
}
