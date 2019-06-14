package com.tw.api.unit.test.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@ExtendWith(MockitoExtension.class)
@DisplayName("A example to test showservice with mock strategy")
public class ShowServiceTests {
	private static final String MOCK_OUTPUT = "Mocked show label";

	@Mock
	private TextService textService;

	@InjectMocks
	private ShowService showService;

	@BeforeEach
	void setMockOutput() {
		when(textService.getText()).thenReturn(MOCK_OUTPUT);
	}

	@Test
	@DisplayName("Mock the output of the text service using mockito")
	public void contextLoads() {

		assertEquals(showService.getShowLable(), MOCK_OUTPUT);

	}

}
