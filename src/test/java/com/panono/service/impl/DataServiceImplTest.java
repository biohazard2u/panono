package com.panono.service.impl;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.panono.dto.DataUpload;
import com.panono.dto.StatisticsResponse;
import com.panono.util.storage.MetricsStorage;
import com.panono.util.validation.CastsUtils;

@RunWith(MockitoJUnitRunner.class)
public class DataServiceImplTest {

	private static final Class<UnaryOperator<StatisticsResponse>> STATISTIC_UPDATER = CastsUtils.cast(UnaryOperator.class);
    private static final Class<BinaryOperator<StatisticsResponse>> STATISTIC_REDUCER = CastsUtils.cast(BinaryOperator.class);
    private static final Long TIMESTAMP = new Random().nextLong();
    private static final double COUNT = new Random().nextDouble();

    @Mock
    private MetricsStorage<StatisticsResponse> storage;

    private DataServiceImpl service;

    @Mock
    private StatisticsResponse statistic;

    @Mock
    private StatisticsResponse anotherStatistic;

    @Mock
    private StatisticsResponse reducedStatistic;


    @Before
    public void setUp() throws Exception {
        service = new DataServiceImpl(storage);
    }

    @Test
    public void shouldRegisterTransaction() throws Exception {
        doAnswer(invocation -> invocation.getArgumentAt(1, STATISTIC_UPDATER).apply(statistic))
                .when(storage).update(eq(TIMESTAMP), any(STATISTIC_UPDATER));

        DataUpload dataUpload = new DataUpload();
        dataUpload.setCount(COUNT);
        dataUpload.setTimestamp(TIMESTAMP);
        service.upload(dataUpload);

        verify(statistic).register(COUNT);
    }

    @Test
    public void shouldReturnTransactionStatistics() throws Exception {
        doAnswer(invocation -> {
            invocation.getArgumentAt(0, STATISTIC_REDUCER).apply(statistic, anotherStatistic);
            return reducedStatistic;
        }).when(storage).reduce(any(STATISTIC_REDUCER));

        StatisticsResponse actual = service.getStatistics();

        assertThat(actual, is(reducedStatistic));

        verify(statistic).merge(anotherStatistic);
    }
}
