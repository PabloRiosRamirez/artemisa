package online.grisk.artemisa.presentation.controller;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AnalysisFileController {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/v1/analysisFiles")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
    	System.out.println("llegó al controller");
    	System.out.println(file.getOriginalFilename());
    	Workbook workbook = null;
    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
    	try {
	    	if(extension.equalsIgnoreCase("xlsx")) {
	    		workbook = new XSSFWorkbook(file.getInputStream());
	    	}else if (extension.equalsIgnoreCase("xls")) {
	    		workbook = new HSSFWorkbook(file.getInputStream());
	    	}
	    	
	    	Sheet sheet = workbook.getSheetAt(0);
	    	
	    	Iterator<Row> rows = sheet.iterator();
	    	
	    	rows.next(); //skip headers
	    	
	    	while(rows.hasNext()) {
	    		Row row = rows.next();
	    		System.out.println(row.getCell(0));
	    	}
	    	
    	}catch (Exception e) {
			// TODO: handle exception
		}
    }

}
