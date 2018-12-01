/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bmr_main_package;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Krzysztof
 */
public class ExcelReadWrite
{

    private Settings settings = null;
    private BMR_Measures measures = null;
    private final String[] headers =
    {
        "Date", "Systolic", "Diastolic", "Pulse", "Arrhythmia", "Glucose", "Empty stomach", "Comment"
    };

    ExcelReadWrite(BMR_Measures measures)
    {
        this.measures = measures;
    }

    public boolean writeFile(String fileName, Date dateFrom, Date dateTo)
    {
        // Create folder if necessary
        CommonDefs.CreateFolder(fileName);

        return writeExcel(fileName, dateFrom, dateTo);
    }

    private boolean writeExcel(String fileName, Date dateFrom, Date dateTo)
    {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        // Header
        Row row = sheet.createRow(rowCount++);
        writeHeader(sheet, row);

        // All measures
        for (Measure measure : this.measures.getAllMeasures())
        {
            Date measureDate = measure.getDateAsDate();
            if (measureDate.compareTo(dateFrom) >= 0 && measureDate.compareTo(dateTo) <= 0)
            {
                row = sheet.createRow(rowCount++);
                writeMeasure(measure, sheet, row);
            }
        }

        try
        {
            FileOutputStream outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            outputStream.close();

        } catch (IOException e)
        {
            return false;
        }

        return true;
    }

    private void writeMeasure(Measure measure, Sheet sheet, Row row)
    {

        Workbook wb = sheet.getWorkbook();

        // Cell style for date cell
        CellStyle csDate = wb.createCellStyle();
        csDate = setCellBorders(csDate);
        CreationHelper createHelper = wb.getCreationHelper();
        short dateFormat = createHelper.createDataFormat().getFormat(CommonDefs.localeDateHourFormat.toPattern());
        csDate.setDataFormat(dateFormat);
        csDate.setAlignment(HorizontalAlignment.CENTER);
        csDate.setShrinkToFit(true);

        // Cell style for arrhythmia
        CellStyle csCenter = wb.createCellStyle();
        csCenter = setCellBorders(csCenter);
        csCenter.setAlignment(HorizontalAlignment.CENTER);

        // Cell style for comment
        CellStyle csLeft = wb.createCellStyle();
        csLeft = setCellBorders(csLeft);
        csCenter.setAlignment(HorizontalAlignment.LEFT);
        csDate.setShrinkToFit(true);

        // WHO category as cell's background
        String colorName = measure.getCategoryWHO().getColor();
        IndexedColors color = IndexedColors.WHITE;
        switch (colorName)
        {
            case "Red":
                color = IndexedColors.RED;
                break;
            case "Orange":
                color = IndexedColors.GOLD;
                break;
            case "Yellow":
                color = IndexedColors.YELLOW;
                break;
            case "Green":
                color = IndexedColors.LIME;
                break;
        }
        CellStyle csColor = wb.createCellStyle();
        csColor = setCellBorders(csColor);
        csColor.setFillForegroundColor(color.getIndex());
        csColor.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        csColor.setAlignment(HorizontalAlignment.CENTER);

        // Write row
        int i = 0;

        Cell cell = row.createCell(i++);
        cell.setCellStyle(csDate);
        cell.setCellValue((Date) measure.getDateHourAsDate());

        cell = row.createCell(i++);
        cell.setCellStyle(csColor);
        cell.setCellValue((Integer) measure.getSystolicAsInt());

        cell = row.createCell(i++);
        cell.setCellStyle(csColor);
        cell.setCellValue((Integer) measure.getDiastolicAsInt());

        cell = row.createCell(i++);
        cell.setCellStyle(csColor);
        cell.setCellValue((Integer) measure.getPulseAsInt());

        cell = row.createCell(i++);
        cell.setCellStyle(csColor);
        cell.setCellValue((String) measure.getArrhythmiaAsYes());

        cell = row.createCell(i++);
        cell.setCellStyle(csCenter);
        cell.setCellValue((Integer) measure.getGlucoseAsInt());

        cell = row.createCell(i++);
        cell.setCellStyle(csCenter);
        cell.setCellValue((String) measure.getEmptyStomachAsYes());

        cell = row.createCell(i++);
        cell.setCellStyle(csLeft);
        cell.setCellValue((String) measure.getComment());
    }

    private void writeHeader(Sheet sheet, Row row)
    {
        CellStyle cs = sheet.getWorkbook().createCellStyle();
        cs = setCellBorders(cs);
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setShrinkToFit(true);

        int i = 0;
        Cell cell = null;
        for (String item : headers)
        {
            cell = row.createCell(i++);
            cell.setCellStyle(cs);
            cell.setCellValue((String) item);
        }

        // Column date
        sheet.setColumnWidth(0, 4200);

        // Column comments
        sheet.setColumnWidth(i - 1, 10000);

        /* 
        //Colors test       
        IndexedColors[] colors = IndexedColors.values();
        for (IndexedColors color : colors)
        {
            CellStyle cs1 = sheet.getWorkbook().createCellStyle();
            cs1.setFillForegroundColor(color.getIndex());
            cs1.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell = row.createCell(i++);
            cell.setCellStyle(cs1);
            cell.setCellValue(color.name());
        }
         */
    }

    private CellStyle setCellBorders(CellStyle cs)
    {
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        cs.setVerticalAlignment(VerticalAlignment.CENTER);

        return cs;
    }

}
