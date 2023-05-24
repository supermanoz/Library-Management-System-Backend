package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.repository.BookRepository;
import com.manoj.springboot.service.ExcelExportService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    @Autowired
    BookRepository bookRepository;
    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {
        List<Book> books=bookRepository.findAllByIsDeleted(false);

        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet();
        HSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("ISBN");
        row.createCell(1).setCellValue("Title");
        row.createCell(2).setCellValue("Author");
        row.createCell(3).setCellValue("Publication Date");
        row.createCell(4).setCellValue("Genre");
        row.createCell(5).setCellValue("Quantity");
        row.createCell(6).setCellValue("Checkout Duration (Days)");
        row.createCell(7).setCellValue("Publisher");

        AtomicReference<Integer> dataRowNum= new AtomicReference<>(1);
        books.forEach(book->{
            HSSFRow dataRow=sheet.createRow(dataRowNum.get());
            dataRow.createCell(0).setCellValue(book.getIsbn());
            dataRow.createCell(1).setCellValue(book.getTitle());
            dataRow.createCell(2).setCellValue(book.getAuthor());
            dataRow.createCell(3).setCellValue(book.getPublicationDate());
            dataRow.createCell(4).setCellValue(book.getGenre());
            dataRow.createCell(5).setCellValue(book.getQuantity());
            dataRow.createCell(6).setCellValue(book.getCheckoutDuration());
            dataRow.createCell(7).setCellValue(book.getPublisher().getName());
            dataRowNum.getAndSet(dataRowNum.get() + 1);
        });

        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
