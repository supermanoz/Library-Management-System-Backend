package com.manoj.springboot.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelExportService {
    public void exportExcel(HttpServletResponse response) throws IOException;
}
