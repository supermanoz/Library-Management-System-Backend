package com.manoj.springboot.service;

import com.manoj.springboot.model.Book;
import com.manoj.springboot.repository.BookRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    public void exportExcel(Class clazz,String excelName) throws IOException;

    public void importExcel(Class clazz,MultipartFile file) throws IOException;
    public List<String> getAttributes(Class tableClass);
}
