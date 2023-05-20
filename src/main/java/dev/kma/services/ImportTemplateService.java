package dev.kma.services;

import com.google.gson.JsonObject;
import dev.kma.dto.ImportedRecord;
import dev.kma.mysql.interfaces.IRecordImportDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class ImportTemplateService {

    @Autowired
    IRecordImportDao iRecordImportDao;

    /**
     * download the file
     *
     * @param response
     */
    public void downloadFile(HttpServletResponse response) {
        String fileName = "download.xls";

        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            fileInputStream = new FileInputStream(fileName);
            bufferedInputStream = new BufferedInputStream(fileInputStream);
            OutputStream os = response.getOutputStream();
            int i = bufferedInputStream.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bufferedInputStream.read(buffer);
            }
        } catch (FileNotFoundException e) {
            log.error("file not found error: {}", e.getMessage());
        } catch (IOException e) {
            log.error("IO exception error: {}", e.getMessage());
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param fileName
     * @return
     * @throws Exception
     */
    @Transactional(transactionManager = "MysqlTransactionManager", rollbackFor = Exception.class)
    public String dealImportFile(String fileName, String note) throws Exception {
        List<List<ImportedRecord>> importCharacterInfos = new ArrayList<>();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.' + 1));

        try {
            // todo: fix get file thing
            Response resp = getWorkbook();
            InputStream is = resp.body().asInputStream();
            Workbook workbook = getWorkbook(is, fileExtension);

            List<ImportedRecord> importedRecords = buildCell(workbook);
            if (!importedRecords.isEmpty()) {
                iRecordImportDao.insertBatchData(importedRecords);
                return "ok";
            } else {
                return "file is empty";
            }
        } catch (IOException e) {
            log.info("===> deal import file error: {}", e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new Exception("import failed, check file format");
        }
        return "file import failed";
    }

    /**
     * get excel workbook from workbook extension
     *
     * @param fs
     * @param str
     * @return
     */
    private static Workbook getWorkbook(InputStream fs, String str) {
        log.info("===========> get workbook version from extension: {}", str);
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * build cell column
     *
     * @param workbook
     * @return Jsonobjecy
     */
    private List<ImportedRecord> buildCell(Workbook workbook) {
        log.info("build column start: ");
        Sheet sheet = workbook.getSheetAt(1);
        // get first row (info)
        Row idRow = sheet.getRow(0);
        // get number of columns in each row
        int charNumCell = idRow.getPhysicalNumberOfCells();

        JsonObject json = new JsonObject();
        Set<String> recordNames = new HashSet<>();
        for (int c = 1; c < charNumCell; c++) {
            Cell cell = idRow.getCell(c);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType().name()) {
                    case "FORMULA" -> value = cell.getCellFormula();

                    case "NUMERIC" -> {
                        if (cell.getCellStyle().getDataFormat() == 0) {
                            // cell.setCellType(Cell.CELL_TYPE_STRING);
                            // NumberToTextConverter.toText(cell.getNumericCellValue());
                            DecimalFormat df = new DecimalFormat("#.##");
                            value = df.format(cell.getNumericCellValue());
                        }
                    }

                    case "STRING" -> value = cell.getStringCellValue();

                    default -> {
                    }
                }
            }
            log.info("-----------------> value = {}", value);

            json.addProperty(value, String.valueOf(c));
            recordNames.add(value);
        }

        List<ImportedRecord> importedRecords = buildImportedRecordByCell(json, recordNames);

        log.info("-----------------> build column finished");
        return importedRecords;
    }


    public List<ImportedRecord> buildImportedRecordByCell(JsonObject json, Set<String> recordNames) {
        log.info("build cell content start!");
        List<ImportedRecord> importedRecords = new ArrayList<>();

        for (String recordName : recordNames) {
            // todo
            ImportedRecord importedRecord = new ImportedRecord();
        }
        return importedRecords;
    }
}
