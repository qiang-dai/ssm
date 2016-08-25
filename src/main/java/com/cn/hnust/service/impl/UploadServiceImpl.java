package com.cn.hnust.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.cn.hnust.service.IUploadService;
import eu.medsea.mimeutil.MimeUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collection;

@Service("uploadService")
public class UploadServiceImpl implements IUploadService {
    public String upload(String filePath) {
        String url = uploadByAws(filePath);

        return url;
    }

    private String getMimeTypes(String filePath) {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        File f = new File (filePath);
        Collection<?> mimeTypes = MimeUtil.getMimeTypes(f);
        String content_type = mimeTypes.toString();

        return content_type;
    }
    private String uploadByAws(String filePath) {
        String existingBucketName = "keyboard-cdn";

        TransferManager tm = new TransferManager(new ProfileCredentialsProvider());

        PutObjectRequest putObjectRequest = new PutObjectRequest(existingBucketName, filePath, new File(filePath));
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(getMimeTypes(filePath));
        objectMetadata.setExpirationTime(DateTime.now().plusYears(10).toDate());

        Upload upload = tm.upload(putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead).withMetadata(objectMetadata));

        try {
            upload.waitForCompletion();
            //URL url = tm.getAmazonS3Client().getUrl(existingBucketName, filePath);
            //return url.toString();
            //url = https://keyboard-cdn.s3.amazonaws.com/downFileDir/a.png
            //说明:事实上,用的是cdn的链接
            String url = "http://cdn.kikakeyboard.com/" + filePath;
            return url;

        } catch (AmazonClientException amazonClientException) {
            System.out.println("Unable to upload file, upload was aborted.");
            amazonClientException.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException {}" + e);
        }
        System.out.println("over");
        return "";
    }
}
