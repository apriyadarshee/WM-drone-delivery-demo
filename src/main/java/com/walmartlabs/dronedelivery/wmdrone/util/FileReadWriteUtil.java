package com.walmartlabs.dronedelivery.wmdrone.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData;
import com.walmartlabs.dronedelivery.wmdrone.exception.BadInputFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class deals with the input file for parsing and validity checks
 */
@Component
public class FileReadWriteUtil {

  Logger logger = LoggerFactory.getLogger(FileReadWriteUtil.class);

  /**
   * read the input file into a list or lines. Exception if input file is empty
   * 
   * @param filePath
   * @return
   * @throws IOException
   * @throws BadInputFileException
   */
  public static List<String> readFromInputFile(final String filePath) throws IOException, BadInputFileException {

    final List<String> inputLines = new ArrayList<String>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        inputLines.add(line);
      }
    }
    if (inputLines.size() == 0) {
      throw new BadInputFileException("Input file does not have data");
    }

    return inputLines;
  }

  /**
   * This method creates a mock input file (mock-input.txt) at the resources
   * folder in classpath
   * 
   * @param n
   * @return
   * @throws IOException
   */
  public static String createMockInput(final int n, final int maxSteps, final int minHour, final int maxHour)
      throws IOException {
    final String filePath = "src/main/resources/mock-input.txt";
    final Random rand = new Random();
    final String ns = "NS";
    final String ew = "EW";

    final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
    for (int i = 0; i < n; i++) {
      int hours = rand.nextInt((maxHour - minHour) + 1) + minHour;
      int minutes = rand.nextInt(60);
      int seconds = rand.nextInt(60);
      writer.write("WM" + String.format("%03d", i + 1) + " " + ns.charAt(rand.nextInt(2)) + rand.nextInt(maxSteps)
          + ew.charAt(rand.nextInt(2)) + rand.nextInt(maxSteps) + " " + LocalTime.of(hours, minutes, seconds) + "\n");

    }
    writer.close();
    return filePath;

  }

  /**
   * generate the output file
   * 
   * @param orderList
   * @param fileName
   * @throws IOException
   */
  public static void generateOutput(final List<OrderData> orderList, final String fileName) throws IOException {
    Integer numberOfPromoters = 0;
    Integer numberOfDetractors = 0;
    final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
    for (final OrderData input : orderList) {
      final String outputLine = new StringBuilder(input.getId()).append(" ")
          .append(input.getLaunchTime().format(DateTimeFormatter.ISO_LOCAL_TIME)).toString();

      if (input.getTag().equals(OrderData.Tag.PROMOTER)) {
        numberOfPromoters += 1;
      } else if (input.getTag().equals(OrderData.Tag.DETRACTOR)) {
        numberOfDetractors += 1;
      }

      writer.write(outputLine + "\n");

    }
    // write NPS calculation
    final Integer npsValue = (numberOfPromoters - numberOfDetractors) * 100 / orderList.size();
    final String npsLine = new StringBuilder("NPS ").append(npsValue).toString();
    writer.write(npsLine);

    writer.close();

  }

}