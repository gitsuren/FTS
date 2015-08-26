package com.suru.fts.api.controller;

import static com.suru.fts.routes.Routes.API_GET_AVAILABLE_FEATURES;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.suru.fts.api.dto.FeaturesForIdResponse;
import com.suru.fts.api.exception.ResourceNotFoundException;
import com.suru.fts.mongo.domain.ToggleSystem;
import com.suru.fts.service.ToggleService;

@Controller
public class ApiController extends ApiControllerBase {

	@Autowired
	private ToggleService toggleService;
	
	@RequestMapping(value = API_GET_AVAILABLE_FEATURES, method = RequestMethod.GET)
	public ResponseEntity<FeaturesForIdResponse> getActiveFeatures(@PathVariable(value = "systemName") final String systemName, @PathVariable(value = "id") final String id) {
		
		ToggleSystem system = toggleService.getSystem(systemName);
		if (system == null) {
			throw new ResourceNotFoundException(systemName + " is not found");
		} else {
			return new ResponseEntity<FeaturesForIdResponse>(populdateActiveFeaturesResponse(systemName, id, toggleService.getFeaturesForId(system, id)), HttpStatus.OK);
		}
	}

	protected FeaturesForIdResponse populdateActiveFeaturesResponse(String systemName,
			String id, Set<String> featuresForId) {
		
		FeaturesForIdResponse response = new FeaturesForIdResponse();
		response.setSystemName(systemName);
		response.setId(id);
		response.getFeatures().addAll(featuresForId);
		return response;
	}
}
