package com.usb.dictionary.word.file;

import com.usb.dictionary.exception.BusinessException;
import com.usb.dictionary.word.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.word.service.model.MeaningDto;
import com.usb.dictionary.word.service.model.WordDto;
import com.usb.dictionary.word.service.request.SaveServiceRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class WordFileReader {

  private static String defaultSourceLanguageCode = "en";
  private static String defaultTargetLanguageCode = "tr";
  private static int defaultSheetIndex = 0;

  public static List<SaveServiceRequest> readFromXlsxFile(
      ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
    validateRequest(readFromXlsxFileServiceRequest);
    List<SaveServiceRequest> saveEntryServiceRequestList = new ArrayList<>();
    InputStream file = getFile(readFromXlsxFileServiceRequest);
    Workbook workbook = new XSSFWorkbook(file);
    int sheetIndex =
        readFromXlsxFileServiceRequest.getSheetIndex() == null
            ? defaultSheetIndex
            : readFromXlsxFileServiceRequest.getSheetIndex();
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    int i = 0;
    for (Row row : sheet) {
      String word =
          row.getCell(readFromXlsxFileServiceRequest.getWordIndex())
              .getRichStringCellValue()
              .getString();
      if (!StringUtils.isEmpty(word)) {
        String targetWord = readFromXlsxRow(row, readFromXlsxFileServiceRequest.getMeaningIndex());
        String type = readFromXlsxRow(row, readFromXlsxFileServiceRequest.getTypeIndex());
        String sourceLanguageCode =
            readFromXlsxFileServiceRequest.getSourceLanguageCodeIndex() == null
                ? defaultSourceLanguageCode
                : readFromXlsxRow(row, readFromXlsxFileServiceRequest.getSourceLanguageCodeIndex());
        String targetLanguageCode =
            readFromXlsxFileServiceRequest.getTargetLanguageIndex() == null
                ? defaultTargetLanguageCode
                : readFromXlsxRow(row, readFromXlsxFileServiceRequest.getTargetLanguageIndex());
        String[] meanings = targetWord.split(",");
        for (String meaning : meanings) {
          meaning = meaning.trim();
          List<WordDto> wordList = new ArrayList<>();
          wordList.add(
              WordDto.builder().content(word).languageCode(sourceLanguageCode).type(type).build());
          wordList.add(
              WordDto.builder()
                  .content(meaning)
                  .languageCode(targetLanguageCode)
                  .type(type)
                  .build());
          SaveServiceRequest newWord =
              SaveServiceRequest.builder()
                  .words(wordList)
                  .meaning(
                      MeaningDto.builder().descriptions(Collections.asSet(meaning, word)).build())
                  .build();
          saveEntryServiceRequestList.add(newWord);
        }
      } else {
        break;
      }
    }
    return saveEntryServiceRequestList;
  }

  private static InputStream getFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest)
      throws IOException {
    if (readFromXlsxFileServiceRequest.getResource() == null) {
      ClassPathResource classPathResource =
          new ClassPathResource(readFromXlsxFileServiceRequest.getFileName());
      return new FileInputStream(classPathResource.getFile());
    } else {
      return readFromXlsxFileServiceRequest.getResource();
    }
  }

  private static String readFromXlsxRow(Row row, int cellIndex) {
    return row.getCell(cellIndex).getRichStringCellValue().toString();
  }

  private static void validateRequest(
      ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) {
    if (StringUtils.isEmpty(readFromXlsxFileServiceRequest.getFileName())
        && readFromXlsxFileServiceRequest.getResource() == null) {
      throw new BusinessException("", ErrorCodes.FILE_NAME_OR_FILLECAN_NOT_BE_NULL_AT_THE_SAMETIME);
    }
  }
}
