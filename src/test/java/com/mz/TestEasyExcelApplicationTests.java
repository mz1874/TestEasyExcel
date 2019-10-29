package com.mz;

import com.mz.entity.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    void excelInfo() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(fileUploadPath + "1.xlsx");
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheetAt = workbook.getSheetAt(0);
            for (Row row : sheetAt) {
                for (Cell cell : row) {
                    //根据不同类型转化成字符串
                    CellType cellTypeEnum = cell.getCellTypeEnum();
                    switch (cellTypeEnum){
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
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件没有找到 ---->{}", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
