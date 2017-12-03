package com.panono.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.panono.dto.DataUpload;
import com.panono.dto.StatisticsResponse;
import com.panono.service.DataService;

@RestController
@RequestMapping("data")
public class PanonoDataController {

	@Autowired
	DataService dataService;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void uploadData(@Valid @RequestBody DataUpload dataUpload) {
		dataService.upload(dataUpload);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StatisticsResponse> getStatistics() {
		StatisticsResponse output = dataService.getStatistics();
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
}