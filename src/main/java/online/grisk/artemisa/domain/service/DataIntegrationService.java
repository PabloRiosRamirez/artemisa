package online.grisk.artemisa.domain.service;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.exception.FileStorageException;
import online.grisk.artemisa.domain.exception.MyFileNotFoundException;
import online.grisk.artemisa.persistence.repository.IDataIntegrationRepository;

@Service
public class DataIntegrationService {

	@Autowired
	private IDataIntegrationRepository dataIntegrationRepository;
	
	@Transactional
	public void deletedByOrganization(Long organization) {
		dataIntegrationRepository.deleteAllByOrganization(organization);
	}
	@Transactional
	public DataIntegration save(DataIntegration dataIntegration) {
		return dataIntegrationRepository.save(dataIntegration);
	}
	@Transactional
	public DataIntegration uploadFile(long id_dataintegration, MultipartFile file) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
			}			
			DataIntegration di = dataIntegrationRepository.getOne(id_dataintegration);
			
			di.setAnalyticsFileName(fileName);
			di.setAnalyticsFile(file.getBytes());

			return dataIntegrationRepository.save(di);
		} catch (IOException ex) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}
	@Transactional
	public DataIntegration findOne(long id_dataintegration) {
		return dataIntegrationRepository.findById(id_dataintegration)
				.orElseThrow(() -> new MyFileNotFoundException("DataIntegration not found with id " + id_dataintegration));
	}
}
