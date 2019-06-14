package com.tw.api.unit.test.services;

import org.springframework.stereotype.Service;

/**
 * @author romeh
 */
@Service
public class TextService {
	public static final String ORIGINAL_OUTPUT = "Original Text Service Text";

	public String getText() {

		return ORIGINAL_OUTPUT;

	}
}
