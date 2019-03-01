package com.dylankilbride.grouppay.services;

import com.dylankilbride.grouppay.returnobjects.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3ImageManagerService {

	private AmazonClient amazonClient;

	@Autowired
	S3ImageManagerService(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	public ImageUploadResponse uploadFile(MultipartFile file){

		return this.amazonClient.uploadFileToBucket(file);
	}

	public ImageUploadResponse deleteFile(String fileUrl){
		return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
	}
}
