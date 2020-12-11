package com.yelp.scraper.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.FaceAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.yelp.scraper.model.Avatar;

@Service
public class FaceDetectionService {

	public Avatar detectFacesUri(String gcsPath) throws IOException {
	    List<AnnotateImageRequest> requests = new ArrayList<>();

	    ImageSource imgSource = ImageSource.newBuilder().setImageUri(gcsPath).build();
	    Image img = Image.newBuilder().setSource(imgSource).build();
	    Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();

	    AnnotateImageRequest request =
	        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
	    requests.add(request);

	    // Initialize client that will be used to send requests. This client only needs to be created
	    // once, and can be reused for multiple requests. After completing all of your requests, call
	    // the "close" method on the client to safely clean up any remaining background resources.
	    Avatar avatar = new Avatar();
	    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
	      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
	      List<AnnotateImageResponse> responses = response.getResponsesList();
	      for (AnnotateImageResponse res : responses) {
	        if (res.hasError()) {
	          System.out.format("Error: %s%n", res.getError().getMessage());
	        }

	        for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
	        	avatar.setAngerLikelihood(annotation.getAngerLikelihood().toString());
	        	avatar.setHeadwearLikelihood(annotation.getHeadwearLikelihood().toString());
	        	avatar.setJoyLikelihood(annotation.getJoyLikelihood().toString());
	        	avatar.setSorrowLikelihood(annotation.getSorrowLikelihood().toString());
	        	avatar.setSurpriseLikelihood(annotation.getSurpriseLikelihood().toString());
	        }
	      }
	    }
	    return avatar;
	  }
	
}
