package com.walmartlabs.dronedelivery.wmdrone;

import java.io.IOException;
import java.util.List;

import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData;
import com.walmartlabs.dronedelivery.wmdrone.exception.BadInputFileException;
import com.walmartlabs.dronedelivery.wmdrone.service.DeliveryLaunchCalcualtion;
import com.walmartlabs.dronedelivery.wmdrone.util.FileReadWriteUtil;
import com.walmartlabs.dronedelivery.wmdrone.util.InputFileParser;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.core.IsNull;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class WalmartDemoApplicationCommandsTests {

	private DeliveryLaunchCalcualtion delCal = new DeliveryLaunchCalcualtion();
	private InputFileParser parserService = new InputFileParser();
	// @Test
	// void contextLoads() {
	// }

	@Test
	void testFileRead() {

		final String line = "WM001 N11W5 05:11:50";
		try {
			final List<String> parsedLines = FileReadWriteUtil.readFromInputFile("src/main/resources/input.txt");
			assertThat(parsedLines, IsNull.notNullValue());
			assertThat(parsedLines, hasItems(line));
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadInputFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testFileConvertedToData() {

		final String line = "WM001 N11W5 05:11:50";
		try {
			final List<String> parsedLines = FileReadWriteUtil.readFromInputFile("src/main/resources/input.txt");
			List<OrderData> inputList = parserService.convertToOrderDataList(parsedLines);
			assertEquals(parsedLines.size(), inputList.size());
			assertEquals(inputList.get(0).getId(), "WM001");
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadInputFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
