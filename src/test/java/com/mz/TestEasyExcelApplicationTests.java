package com.mz;

import com.mz.entity.User;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class TestEasyExcelApplicationTests {

    private static Logger logger = LoggerFactory.getLogger(TestEasyExcelApplicationTests.class);

    @Value("${file.upload}")
    private String fileUploadPath;

    @Test
    void contextLoads() {

    }

    /**
     * 解析excel
     */
    @Test
    void testExcelInfo() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/wd.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheetAt = workbook.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();
            logger.info("显示excel中的所有的列行数 ------>{}",lastRowNum);
            for (Row row : sheetAt) {
                for (Cell cell : row) {
                    //根据不同类型转化成字符串
                    CellType cellTypeEnum = cell.getCellTypeEnum();
                    switch (cellTypeEnum) {
                        case BLANK:
                            System.out.println(cell);
                            break;
                        case ERROR:
                            System.out.println();
                            break;
                        case STRING:
                            System.out.println(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            System.out.println(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            System.out.println(cell.getNumericCellValue());
                            break;
                        case FORMULA:
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件没有找到 ---->{}", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testExcelExport() {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("0");
        for (int i = 0; i < 9; i++) {
            sheet.setColumnWidth(i, 4300);

        }

        /*单元格*/
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
//        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中

        /*标题*/
        XSSFFont titleFont = wb.createFont();
        titleFont.setFontHeight(24);
        titleFont.setBold(true);
        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setBorderTop(BorderStyle.THIN);
        titleCellStyle.setBorderBottom(BorderStyle.THIN);
        titleCellStyle.setBorderLeft(BorderStyle.THIN);
        titleCellStyle.setBorderRight(BorderStyle.THIN);
        titleCellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleCellStyle.setRightBorderColor(HSSFColor.BLACK.index);
//        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
//        titleCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 上下居中
        titleCellStyle.setFont(titleFont);

        /**
         * 主 标题 在这里插入主标题
         */
        Row titleRow;
        Cell titleCell;
        sheet.addMergedRegion(new CellRangeAddress((short) 0, (short) 2, (short) 0, (short) 8));
        for (int i = 0; i <= 2; i++) {
            titleRow = sheet.createRow(i);
            for (int j = 0; j < 9; j++) {
                titleCell = titleRow.createCell(j);
                titleCell.setCellType(CellType.STRING);
                titleCell.setCellStyle(titleCellStyle);
                titleCell.setCellValue("测试标题数据");
            }
        }

        /**
         * 列 标题 在这里插入标题
         */
        Row rowLabel;
        Cell cellLabel;
        /*创建一行作为标题列*/
        for (int i = 3; i < 4; i++) {
            rowLabel = sheet.createRow(i);
            for (int j = 0; j < 9; j++) {
                cellLabel = rowLabel.createCell(j);
                cellLabel.setCellType(CellType.STRING);
                cellLabel.setCellStyle(cellStyle);
                cellLabel.setCellValue("测试标题列【" + (j + 1) + "】");
            }
        }


        /**
         * 列 数据 在这里插入数据
         */
        Row rowCheck;
        Cell cellCheck;
        for (int i = 3; i < 10; i++) {
            rowCheck = sheet.createRow((i + 1));
            for (int j = 0; j < 9; j++) {
                cellCheck = rowCheck.createCell(j);
                cellCheck.setCellType(CellType.STRING);
                cellCheck.setCellStyle(cellStyle);
                cellCheck.setCellValue("测试 - 0" + (i - 2));
            }
        }


        exportOutPutExcel("/ExportExcel.xlsx", wb);

    }

    public void exportOutPutExcel(String exportPositionPath, XSSFWorkbook wb) {
        try {
            File file = new File(exportPositionPath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}
