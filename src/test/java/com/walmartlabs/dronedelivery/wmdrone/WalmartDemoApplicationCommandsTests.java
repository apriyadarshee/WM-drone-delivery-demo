package com.walmartlabs.dronedelivery.wmdrone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData;
import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData.Tag;
import com.walmartlabs.dronedelivery.wmdrone.exception.BadInputFileException;
import com.walmartlabs.dronedelivery.wmdrone.service.DeliveryLaunchCalcualtion;
import com.walmartlabs.dronedelivery.wmdrone.util.FileReadWriteUtil;
import com.walmartlabs.dronedelivery.wmdrone.util.InputFileParser;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.core.IsNull;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

//@SpringBootTest
class WalmartDemoApplicationCommandsTests {

	private final DeliveryLaunchCalcualtion delCal = new DeliveryLaunchCalcualtion();
	private final InputFileParser parserService = new InputFileParser();
	// @Test
	// void contextLoads() {
	// }
    static {
        System.setProperty("delStartStr", "06:00:00");
    }
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
		} catch (final BadInputFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testFileConvertedToData() {

		try {
			final List<String> parsedLines = FileReadWriteUtil.readFromInputFile("src/main/resources/input.txt");
			final List<OrderData> inputList = parserService.convertToOrderDataList(parsedLines);
			assertEquals(parsedLines.size(), inputList.size());
			assertEquals(inputList.get(0).getId(), "WM001");
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final BadInputFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testDefaultCategorization() {

		final String line1 = "WM001 N11W50 05:11:50";
		final String line2 = "WM003 N11E5 09:13:20";

		final List<OrderData> inputList = new ArrayList<OrderData>();
		try {

			inputList.add(ReflectionTestUtils.invokeMethod(parserService, "convertedData", line1));
			inputList.add(ReflectionTestUtils.invokeMethod(parserService, "convertedData", line2));

			System.out.println(delCal.tagInput(inputList));

			System.out.println(inputList.get(0).getTag());
			System.out.println(inputList.get(1).getTag());
			 assertEquals(inputList.get(0).getTag(),Tag.DETRACTOR);
			 assertEquals(inputList.get(1).getTag(),Tag.PROMOTER);
			
			assertEquals(2, inputList.size());
			assertEquals(inputList.get(0).getId(), "WM001");
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
