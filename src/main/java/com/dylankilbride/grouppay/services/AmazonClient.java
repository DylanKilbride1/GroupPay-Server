package com.dylankilbride.grouppay.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dylankilbride.grouppay.returnobjects.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Service
public class AmazonClient {

	@Autowired
	private AmazonS3 s3Client;

	@Value("${aws.region}")
	String endpointUrl;
	@Value("${aws.image.bucket}")
	private String bucketName;
	String accessKey = "AKIAIU272V4VCYLUFTUQ";
	String secretKey = "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";

	public AmazonClient () {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
		s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.EU_WEST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
	}


	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
	}

	private void uploadFileTos3bucket(String fileName, File file) {
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
					.withCannedAcl(CannedAccessControlList.PublicRead));
	}

	public ImageUploadResponse prepareFileForUpload(MultipartFile multipartFile) {

		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = endpointUrl + ".amazonaws.com/" + bucketName + "/" + fileName;
			uploadFileTos3bucket(fileName, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ImageUploadResponse("success", fileUrl);
	}

	public ImageUploadResponse deleteFileFromS3Bucket(String fileUrl) {
		String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
		try {
			s3Client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
			return new ImageUploadResponse("success");
		} catch (SdkClientException s) {
			return new ImageUploadResponse("failure");
		}
	}
}
