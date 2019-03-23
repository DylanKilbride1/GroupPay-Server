package com.dylankilbride.grouppay.Services;

import com.dylankilbride.grouppay.Clients.AmazonClient;
import com.dylankilbride.grouppay.ReturnObjects.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3ImageManagerService {

	private AmazonClient amazonClient;

	@Autowired
	S3ImageManagerService(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
	}

	public ImageUploadResponse uploadFile(MultipartFile file){

		return this.amazonClient.prepareFileForUpload(file);
	}

	public ImageUploadResponse deleteFile(String fileUrl){
		return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
	}
}
