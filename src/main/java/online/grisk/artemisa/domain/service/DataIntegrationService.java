package online.grisk.artemisa.domain.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.exception.FileStorageException;
import online.grisk.artemisa.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.IDataIntegrationRepository;

@Service
public class DataIntegrationService {
	
	@Autowired
	private IDataIntegrationRepository dataIntegrationRepository;
	
	
	public DataIntegration save(DataIntegration dataIntegration) {
		return dataIntegrationRepository.save(dataIntegration);
	}
	
	public DataIntegration uploadFile(long id, MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}
			
			DataIntegration di = dataIntegrationRepository.getOne(id);
			
			di.setAnalyticsFileName(fileName);
			di.setAnalyticsFileType(file.getContentType());
			di.setAnalyticsFile(file.getBytes());
			
			return dataIntegrationRepository.save(di);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public DataIntegration findOne(long fileId) {
		return dataIntegrationRepository.findById(fileId)
				.orElseThrow(() -> new MyFileNotFoundException("DataIntegration not found with id " + fileId));
	}
}
