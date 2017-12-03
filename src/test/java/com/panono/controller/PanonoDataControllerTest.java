package com.panono.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.panono.dto.DataUpload;
import com.panono.dto.StatisticsResponse;
import com.panono.exception.InvalidTimestampException;
import com.panono.service.DataService;
import com.panono.util.validation.WithinLastLongValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(PanonoDataController.class)
public class PanonoDataControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DataService dataService;

	String content;
	
	@Before
    public void setUp() throws Exception {
        WithinLastLongValidator.NOW = () -> 1478192204000L;
        content = "{\"count\": 1, \"timestamp\": 1512248453385, \"links\": [{\"href\": \"string\",\"rel\": \"string\"}]}";
    }
	
	@Test
	public void shouldReturnZeroStatistics() throws Exception {
		doReturn(StatisticsResponse.ZERO).when(dataService).getStatistics();

		mvc.perform(get("/data").accept("application/json"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith("application/json"))
			.andExpect(jsonPath("count", is(0)))
			.andExpect(jsonPath("sum", is(0.0)))
			.andExpect(jsonPath("avg", is("NaN")))
			.andExpect(jsonPath("max", is("NaN")))
			.andExpect(jsonPath("min", is("NaN")));
	}
	
	@Test
	public void shouldValidateRequest() throws Exception {		
		mvc.perform(post("/data").contentType("application/json").content(content))
			.andExpect(status().isCreated())
			.andExpect(content().bytes(new byte[0]));
	}

    @Test
    public void shouldHandleInvalidTimestampException() throws Exception {
        doThrow(new InvalidTimestampException(""))
                .when(dataService).upload(any(DataUpload.class));
                
        mvc.perform(post("/data")
                .contentType("application/json")
                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().bytes(new byte[0]));
    }
}