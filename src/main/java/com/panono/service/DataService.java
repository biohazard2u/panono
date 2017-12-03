package com.panono.service;

import com.panono.dto.DataUpload;
import com.panono.dto.StatisticsResponse;

public interface DataService {

	void upload(DataUpload dataUpload);

	StatisticsResponse getStatistics();
}