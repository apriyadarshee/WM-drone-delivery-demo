package com.walmartlabs.dronedelivery.wmdrone.util;

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.walmartlabs.dronedelivery.wmdrone.domain.OrderData;
import com.walmartlabs.dronedelivery.wmdrone.exception.BadInputFileException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class deals with the input file for parsing and validity checks
 */
@Component
public class InputFileParser {

  Logger logger = LoggerFactory.getLogger(InputFileParser.class);


  /**
   * Create an output file name. Exception if input file does not exist.
   * 
   * @param filePath
   * @return
   * @throws BadInputFileException
   */
  public String getOutputFileName(final String filePath) throws BadInputFileException {

    final File inputFile = new File(filePath);
    String outputFilePath = "";
    if (inputFile.exists()) {

      outputFilePath = new StringBuilder(inputFile.getParent()).append("/output.for.").append(inputFile.getName())
          .toString();
      System.out.print(outputFilePath);
    } else {
      throw new BadInputFileException("Input file does not exists");
    }
    return outputFilePath;
  }

  /**
   * Convert list of lines to list of orders.
   *
   * @param inputLines
   * @return
   * @throws BadInputFileException
   */
  public List<OrderData> convertToOrderDataList(final List<String> inputLines) throws BadInputFileException {

    final List<OrderData> orderList = new ArrayList<OrderData>();

    for (final String order : inputLines) {
      orderList.add(convertedData(order));
    }
    return orderList;

  }

  /**
   * Takes each lines from the input and convert into an order object. Exception
   * if input entry is corrupted.
   * 
   * @param order
   * @return
   * @throws BadInputFileException
   * @throws DateTimeParseException
   * @throws NumberFormatException
   */
  private OrderData convertedData(final String order)
      throws BadInputFileException, DateTimeParseException, NumberFormatException {
    final OrderData datum = new OrderData();
    final String[] fields = order.split(" ");
    if (fields.length != 3) {
      throw new BadInputFileException("Input file does not have all data in 3 columns");
    }
    datum.setId(fields[0]);
    datum.setLocation(fields[1]);
    datum.setTimeStampStr(fields[2]);
    datum.setTimeStamp(LocalTime.parse(fields[2]));
    String location = fields[1];
    location = location.replaceAll("[N|W|S|E]", " ");
    final String[] steps = location.trim().split(" ");
    if (steps.length != 2) {
      throw new BadInputFileException("Input file has error in location format");
    }
    int timeToLocation = 0;
    for (int i = 0; i < 2; i++) {

      timeToLocation += Integer.parseInt(steps[i]);
    }
    datum.setTimeToLocation(timeToLocation);
    logger.debug(new Gson().toJson(datum));
    // System.out.print(new Gson().toJson(datum));
    return datum;
  }

}