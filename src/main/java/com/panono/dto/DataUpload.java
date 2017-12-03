package com.panono.dto;

import static java.time.temporal.ChronoUnit.SECONDS;

import javax.validation.constraints.Min;

import org.immutables.value.Value.Immutable;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.panono.util.validation.WithinLast;
import com.ponono.util.json.JsonSnakeCase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Immutable
@JsonSnakeCase
@JsonSerialize(as = DataUpload.class)
@JsonDeserialize(as = DataUpload.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "DataUpload")
public class DataUpload extends ResourceSupport{

	@ApiModelProperty(value = "DataUpload")
	@JsonProperty("count")
	@Min(0)
	double count;
	
	@ApiModelProperty(value = "timestamp")
	@JsonProperty("timestamp")
	@WithinLast(duration = 60, unit = SECONDS)
	long timestamp;
}
