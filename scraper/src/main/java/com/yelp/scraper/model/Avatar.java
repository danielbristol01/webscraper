package com.yelp.scraper.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Avatar {
	
	private String joyLikelihood;
	
	private String sorrowLikelihood;
	
	private String angerLikelihood;
	
	private String surpriseLikelihood;
	
	private String headwearLikelihood;

}
