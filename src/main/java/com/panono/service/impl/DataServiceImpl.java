package com.panono.service.impl;

import static com.panono.dto.StatisticsResponse.ZERO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.panono.dto.DataUpload;
import com.panono.dto.StatisticsResponse;
import com.panono.service.DataService;
import com.panono.util.storage.AtomicMetricsStorage;
import com.panono.util.storage.MetricsStorage;


@Service
public class DataServiceImpl implements DataService {

	private final MetricsStorage<StatisticsResponse> transactions;
	
	@Autowired
	public DataServiceImpl() {
		this(AtomicMetricsStorage.lastMinute(() -> ZERO));
	}
	
	@VisibleForTesting
    protected DataServiceImpl(MetricsStorage<StatisticsResponse> transactions) {
        this.transactions = transactions;
    }

	@Override
	public void upload(DataUpload dataUpload) {
		transactions.update(dataUpload.getTimestamp(), statistic -> statistic.register(dataUpload.getCount()));
	}

	@Override
	public StatisticsResponse getStatistics() {
		 return transactions.reduce(StatisticsResponse::merge);
	}
}
