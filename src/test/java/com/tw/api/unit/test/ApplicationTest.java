package com.tw.api.unit.test;


import static org.junit.jupiter.api.Assertions.assertEquals;

import com.tw.api.unit.test.services.ShowService;
import com.tw.api.unit.test.services.TextService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ApplicationTest {

	@Autowired
	private ShowService showService;

	@Test
	@DisplayName("Integration test which will get the actual output of text service")
	public void contextLoads() {
		Assertions.assertEquals(showService.getShowLable(), TextService.ORIGINAL_OUTPUT);
	}

}
