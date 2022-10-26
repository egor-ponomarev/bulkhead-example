package com.ponomarev.example.bulkhead;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@EnableAutoConfiguration
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BulkheadV1Test {
	@Autowired
	private MockMvc mockMvc;

	@Test
	void bulkheadTest() throws Exception {
		int numberOfRequests = 30;
		ExecutorService executor = Executors.newFixedThreadPool(numberOfRequests);
		List<CompletableFuture<Integer>> cfList = new ArrayList<>();

		for (int i = 0; i < numberOfRequests; i++) {
			cfList.add(CompletableFuture.supplyAsync(() -> performRequest(), executor));
		}

		int countOK = 0;
		for (CompletableFuture<Integer> cf : cfList) {
			if (cf.get(1, TimeUnit.SECONDS) == HttpStatus.OK.value()) {
				countOK++;
			}
		}

		// 5 - is max-concurrent-calls in application-test.yml
		Assertions.assertEquals(5, countOK);
	}

	private int performRequest() {
		try {
			MvcResult mvcResult = mockMvc.perform(get("/v1"))
				.andDo(print())
				.andReturn();
			return mvcResult.getResponse().getStatus();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
