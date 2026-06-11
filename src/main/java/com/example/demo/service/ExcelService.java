package com.example.demo.service;

import com.example.demo.dto.request.TransactionRequest;
import com.example.demo.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExcelService {

    private final TransactionService service;

    private String getCellValue(Cell cell) {
        if (cell == null) return null;
        return cell.toString().trim();
    }

    private String getFormattedDate(Cell cell) {
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            Date date = cell.getDateCellValue();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }

        return cell.toString(); // fallback
    }

    public List<TransactionRequest> readExcel(MultipartFile file) throws IOException {
        List<TransactionRequest> result = new ArrayList<>();

        InputStream is = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(is);

        Sheet sheet = workbook.getSheetAt(0);
        String userId = null;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) continue;

            TransactionRequest request = new TransactionRequest();

            request.setName(getCellValue(row.getCell(0)));
            request.setNote(getCellValue(row.getCell(1)));

            String amountStr = getCellValue(row.getCell(2));
            request.setAmount(amountStr != null ? new BigDecimal(amountStr) : null);

            request.setType(getCellValue(row.getCell(3)));
            request.setTransactionDate(getFormattedDate(row.getCell(4)));

            userId = getCellValue(row.getCell(5));

            request.setWalletId(getCellValue(row.getCell(6)));
            request.setCategoryId(getCellValue(row.getCell(7)));

            result.add(request);
        }
        workbook.close();

        //Save transaction
        service.create(userId, result);

        return result;
    }
}
