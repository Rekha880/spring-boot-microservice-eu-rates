package com.eu.rates.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.eu.rates.model.Countries;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StandardAndReducedRatesRestController {
	
	Logger logger = LoggerFactory.getLogger(StandardAndReducedRatesRestController.class);
	
	@Value("${externalApiUrl}")
	private String apiUrl;
	
	
	
	
	// For Getting Json from Rest call and finding max Standard Rates

	@GetMapping(value="/standardRates")
	public ResponseEntity<?> getStandardRates() throws Exception
	{
		RestTemplate rt =new RestTemplate();
		logger.info(apiUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Application");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseJson = rt.exchange(apiUrl, HttpMethod.GET, entity, String.class);
		
		logger.info("getting response::::::::" +responseJson.getBody());
		ResponseEntity<?> standardRates = getStandardRatesFromJson(responseJson.getBody());
		return standardRates;
		
	}
	
	//Calculation of Standard Rates from Json Object
	public ResponseEntity<?> getStandardRatesFromJson(String responseJson) throws Exception
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> jsonData = mapper.readValue(responseJson, new
		TypeReference<Map<String, Object>>() { });
				 
		Map<String, Object> ob= (Map<String, Object>) jsonData.get("rates");
		Map<String,Map> mapOfStd=null;
		List<Countries> countries =new ArrayList<Countries>();
		
		for(Entry<String, Object> entry : ob.entrySet())
		{ 
			mapOfStd=new HashMap<String,Map>();
			mapOfStd.put(entry.getKey(),(Map) entry.getValue());
			Countries coun = mapper.convertValue(entry.getValue(), Countries.class);
		    countries.add(coun);
			
		}

		logger.info("Printing Countries List:::::" +countries.toString());
		Map<Double, Optional<Countries>> map = countries.stream().collect(Collectors.groupingBy(Countries::getStandard_rate, Collectors.maxBy(Comparator.comparingDouble(Countries::getStandard_rate))));
		//map.entrySet().stream().skip(7).forEach(System.out::println);
		
		return new ResponseEntity(map.entrySet().stream().skip(7), HttpStatus.OK);
		
		
	}
	
	// For Getting Json from Rest call and finding Min Reduced Rates
	@GetMapping(value="/reducedRates")
	public ResponseEntity<?> getLowReducedRates() throws Exception
	{
		RestTemplate rt =new RestTemplate();
		logger.info(apiUrl);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent", "Application");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<String> responseJson = rt.exchange(apiUrl, HttpMethod.GET, entity, String.class);
		
		logger.info("getting response::::::::" +responseJson.getBody());
		ResponseEntity<?> reducedRates = getReducedRatesFromJson(responseJson.getBody());
		return reducedRates;
	}
	
	//Calculation of Reduced Rates from Json Object
	public ResponseEntity<?> getReducedRatesFromJson(String responseJson) throws Exception
	{
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, Object> jsonData = mapper.readValue(responseJson, new
		TypeReference<Map<String, Object>>() { });
				 
		Map<String, Object> ob= (Map<String, Object>) jsonData.get("rates");
		Map<String,Map> mapOfStd=null;
		List<Countries> countries =new ArrayList<Countries>();
		
		for(Entry<String, Object> entry : ob.entrySet())
		{ 
			mapOfStd=new HashMap<String,Map>();
			mapOfStd.put(entry.getKey(),(Map) entry.getValue());
			Countries coun = mapper.convertValue(entry.getValue(), Countries.class);
		    countries.add(coun);
			
		}

		logger.info("Printing Countries List:::::" +countries.toString());
		
		Map<Double,Countries> mapList=new HashMap<Double,Countries>();
	    double min =  Double.MAX_VALUE;
	    for(int i=0;i<countries.size();i++) {
	    	if(!countries.get(i).getReduced_rate().equals("false"))
	    	{
	    		
	    		 min = Math.min(min, Double.parseDouble(countries.get(i).getReduced_rate()));
	    		 mapList.put(min,countries.get(i));
	    	}
	    	
	    }
	    
		return new ResponseEntity(mapList.entrySet().stream().distinct().skip(2), HttpStatus.OK);
		
		
	}
	
}
