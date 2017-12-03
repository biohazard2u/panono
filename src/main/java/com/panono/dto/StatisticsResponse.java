package com.panono.dto;

import org.immutables.value.Value.Immutable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ponono.util.json.JsonSnakeCase;

import io.swagger.annotations.ApiModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Ponono statistics response")
@Immutable
@JsonSnakeCase
@JsonSerialize(as = ImmutableStatisticsResponse.class)
@JsonDeserialize(as = ImmutableStatisticsResponse.class)
public interface StatisticsResponse {

	long getCount();

	double getSum();

	double getMax();

	double getMin();

	default double getAvg() {
		return getCount() > 0 ? getSum() / getCount() : Double.NaN;
	}

	StatisticsResponse ZERO = ImmutableStatisticsResponse.builder().count(0).sum(0.0).max(Double.NaN).min(Double.NaN)
			.build();

	default StatisticsResponse register(double amount) {
		return this.equals(ZERO)
				? ImmutableStatisticsResponse.builder().count(1).sum(amount).min(amount).max(amount).build()
				: ImmutableStatisticsResponse.builder().count(getCount() + 1).sum(getSum() + amount)
						.min(Math.min(getMin(), amount)).max(Math.max(getMax(), amount)).build();
	}

	default StatisticsResponse merge(StatisticsResponse that) {
		if (this.equals(ZERO)) {
			return that;
		}
		if (that.equals(ZERO)) {
			return this;
		}
		return ImmutableStatisticsResponse.builder().count(this.getCount() + that.getCount())
				.sum(this.getSum() + that.getSum()).min(Math.min(this.getMin(), that.getMin()))
				.max(Math.max(this.getMax(), that.getMax())).build();
	}
}
