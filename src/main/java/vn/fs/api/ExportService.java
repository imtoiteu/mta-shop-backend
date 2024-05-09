package vn.fs.api;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import vn.fs.entity.Category;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportService {

    public ByteArrayOutputStream createExcelFile(List<Category> categories) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Categories");

            // Tạo header row
            String[] headers = new String[] { "ID", "Name", "Description" };
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }

            // Điền dữ liệu
            int rowIndex = 1;
            for (Category category : categories) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(category.getCategoryId());
                row.createCell(1).setCellValue(category.getCategoryName());
            }

            // Tự động resize các cột
            for (int col = 0; col < headers.length; col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(out);
            return out;
        }
    }
}
