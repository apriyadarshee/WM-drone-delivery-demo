package com.walmartlabs.dronedelivery.wmdrone;

import java.io.IOException;
import java.util.List;

import com.walmartlabs.dronedelivery.wmdrone.exception.BadInputFileException;
import com.walmartlabs.dronedelivery.wmdrone.util.FileReadWriteUtil;
import static org.hamcrest.Matchers.hasItems;
import org.hamcrest.core.IsNull;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WalmartDemoApplicationCommandsTests {


	@Test
	void contextLoads() {
	}

	@Test
	void testFileRead() {

		final String line = "Hi";
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
	

}
