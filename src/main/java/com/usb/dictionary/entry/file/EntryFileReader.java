package com.usb.dictionary.entry.file;

import com.usb.dictionary.entry.file.request.ReadFromXlsxFileServiceRequest;
import com.usb.dictionary.entry.service.request.EntryServiceRequestDto;
import com.usb.dictionary.entry.service.request.SaveEntryServiceRequest;
import com.usb.dictionary.entry.service.request.WordServiceRequestDto;
import com.usb.dictionary.exception.BusinessException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.usb.dictionary.entry.file.ErrorCodes.FILE_NAME_OR_FILLECAN_NOT_BE_NULL_AT_THE_SAMETIME;
import static java.util.Arrays.asList;

public class EntryFileReader {

    private static String defaultSourceLanguageCode = "en";
    private static String defaultTargetLanguageCode = "tr";
    private static int defaultSheetIndex = 0;

    public static List<SaveEntryServiceRequest> readFromXlsxFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
        validateRequest(readFromXlsxFileServiceRequest);
        List<SaveEntryServiceRequest> saveEntryServiceRequestList = new ArrayList<>();
        InputStream file = getFile(readFromXlsxFileServiceRequest);
        Workbook workbook = new XSSFWorkbook(file);
        int sheetIndex = readFromXlsxFileServiceRequest.getSheetIndex() == null ? defaultSheetIndex : readFromXlsxFileServiceRequest.getSheetIndex();
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int i = 0;
        for (Row row : sheet) {
            String word = row.getCell(readFromXlsxFileServiceRequest.getWordIndex()).getRichStringCellValue().getString();
            if(!StringUtils.isEmpty(word)) {
                String meaning = readFromXlsxRow(row, readFromXlsxFileServiceRequest.getMeaningIndex());
                String type = readFromXlsxRow(row, readFromXlsxFileServiceRequest.getTypeIndex());
                String sourceLanguageCode = readFromXlsxFileServiceRequest.getSourceLanguageCodeIndex() == null
                        ? defaultSourceLanguageCode
                        : readFromXlsxRow(row, readFromXlsxFileServiceRequest.getSourceLanguageCodeIndex());
                String targetLanguageCode = readFromXlsxFileServiceRequest.getTargetLanguageIndex() == null
                        ? defaultTargetLanguageCode
                        : readFromXlsxRow(row, readFromXlsxFileServiceRequest.getTargetLanguageIndex());
                SaveEntryServiceRequest newEntry = SaveEntryServiceRequest.builder()
                        .entry(EntryServiceRequestDto.builder()
                                .type(type)
                                .words(asList(WordServiceRequestDto.builder()
                                        .name(word)
                                        .languageCode(sourceLanguageCode)
                                        .build(),
                                        WordServiceRequestDto.builder()
                                                .name(meaning)
                                                .languageCode(targetLanguageCode)
                                                .build()))
                                .build())
                        .build();
                saveEntryServiceRequestList.add(newEntry);
            }
            else {
                break;
            }
        }
        return saveEntryServiceRequestList;
    }

    private static InputStream getFile(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) throws IOException {
        if(readFromXlsxFileServiceRequest.getResource() == null) {
            ClassPathResource classPathResource = new ClassPathResource(readFromXlsxFileServiceRequest.getFileName());
            return new FileInputStream(classPathResource.getFile());
        }
        else {
            return readFromXlsxFileServiceRequest.getResource();
        }
    }

    private static String readFromXlsxRow(Row row, int cellIndex){
        return row.getCell(cellIndex).getRichStringCellValue().toString();
    }

    private static void validateRequest(ReadFromXlsxFileServiceRequest readFromXlsxFileServiceRequest) {
        if(StringUtils.isEmpty(readFromXlsxFileServiceRequest.getFileName()) && readFromXlsxFileServiceRequest.getResource() == null){
            throw new BusinessException("", FILE_NAME_OR_FILLECAN_NOT_BE_NULL_AT_THE_SAMETIME);
        }
    }
}
