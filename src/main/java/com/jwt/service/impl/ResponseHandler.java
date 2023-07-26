package com.jwt.service.impl;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseHandler {
	/*
	 * @input - generated token, api details like HTTP status code for any method, reponse object of any method
	 * @output - JSON format
	 * description - Get the inputs and constructs all of them in a proper JSON format
	 */
	public static ResponseEntity<Object> generateResponse(String token,Object apiDetails, Object responseObj) {
		
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        if(!token.equals("") && token != null)
        	map.put("token", token);
        
        if(responseObj != null)
        	map.put("responseData", responseObj);
        else
        	map.put("responseData", "");
        
        if(apiDetails != null)
        	map.put("apiStatus", apiDetails);
        
        return ResponseEntity.ok(map);
    }
}
