package com.manoj.springboot.serviceImpl;

import com.manoj.springboot.exception.AlreadyExistsException;
import com.manoj.springboot.exception.DoesNotExistException;
import com.manoj.springboot.model.Book;
import com.manoj.springboot.model.Publisher;
import com.manoj.springboot.repository.BookRepository;
import com.manoj.springboot.repository.PublisherRepository;
import com.manoj.springboot.service.ExcelService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@PropertySource("classpath:message.properties")
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PublisherRepository publisherRepository;
    private final String directory ="F:/GitHub/Library-Management-System-Backend/src/main/resources/files/";
    @Value("${doesNotExist}")
    private String doesNotExistMessage;
    @Value("${alreadyExists}")
    private String alreadyExistsMessage;
    Logger logger= LoggerFactory.getLogger(ExcelServiceImpl.class);

    @Override
    public void exportExcel(Class clazz,String excelName) throws IOException {
        HSSFWorkbook workbook = null;
        switch(clazz.getName()){
            case "com.manoj.springboot.model.Book":
                workbook=exportBookToExcel();
                break;
            case "com.manoj.springboot.model.Member":
                //todo
                break;
            case "com.manoj.springboot.model.Publisher":
                //todo
                break;
            case "com.manoj.springboot.model.Burrow":
                //todo
                break;
            case "com.manoj.springboot.model.Request":
                //todo
                break;
            default:
                System.out.println("Invalid Class for excel export!");
        }
        if(workbook!=null){
            File currDir=new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation=path.substring(0,path.length()-1)+"src/main/resources/files/"+excelName+".xls";
            FileOutputStream fileOutputStream=new FileOutputStream(fileLocation);
            workbook.write(fileOutputStream);
            workbook.close();
        }
        workbook.close();
    }

    @Override
    public void importExcel(Class clazz,MultipartFile file) throws IOException {
//        FileInputStream inputStream=(FileInputStream) file.getInputStream(); *** can't do this due to explicit permission needed / security reasons
        String fileName=file.getOriginalFilename();
        File dir=new File(directory);
        if(dir.exists()){
            File files[]=dir.listFiles();
            Arrays.stream(files).forEach(dirFile->{
                if(!dirFile.isDirectory() && dirFile.getName().equals(fileName)){
                    throw new AlreadyExistsException("File "+alreadyExistsMessage+"! Try renaming the file before uploading.");
                }
            });
            String excelFile= directory +fileName;
            file.transferTo(new File(excelFile));
            FileInputStream inputStream=new FileInputStream(excelFile);
            XSSFWorkbook workbook=new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheetAt(0);
            List<Book> books=importExcelToBook(sheet);
            bookRepository.saveAll(books);
        }
        else{
            throw new DoesNotExistException("Directory "+doesNotExistMessage);
        }
    }

    public List<Book> importExcelToBook(XSSFSheet sheet){
        List<Book> books=new ArrayList<>();
        for(Row row:sheet){
            if(row.getRowNum()==0)
                continue;
            HashMap<String,Object> bookHashMap=new HashMap<>();
            Row attRow=sheet.getRow(0);
            Integer i=0;
            for(Cell cell:row){
//                logger.trace(attRow.getCell(i)+" : "+cell.getCellType().toString());
                switch(cell.getCellType()){
                    case STRING -> {
                        bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),cell.getRichStringCellValue().getString());
                    }
                    case NUMERIC -> {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),cell.getDateCellValue());
                        } else {
                            bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),cell.getNumericCellValue());
                        }
                    }
                    case BOOLEAN -> bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),cell.getBooleanCellValue());
                    case FORMULA -> bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),cell.getCellFormula());
                    case BLANK -> bookHashMap.put(attRow.getCell(i).getRichStringCellValue().getString(),null);
                    default -> logger.trace(cell.getCellType()+" Blank cell mate!");
                }
                i++;
            }
            logger.trace(bookHashMap.toString());
            Book book=hashMapToBook(bookHashMap);
            books.add(book);
            logger.trace(book.toString());
        }
        return books;
    }

    private Book hashMapToBook(HashMap<String, Object> bookHashMap) {
        Book book=new Book();
        for(String key:bookHashMap.keySet()){
            switch (key){
                case "ISBN"->book.setIsbn((String)bookHashMap.get(key));
                case "Title"->book.setTitle((String)bookHashMap.get(key));
                case "Genre"->book.setGenre((String)bookHashMap.get(key));
                case "Author"->book.setAuthor((String)bookHashMap.get(key));
                case "Quantity"->{
                    Double quantity=(Double)bookHashMap.get(key);
                    book.setQuantity(quantity.intValue());
                }
                case "Publisher"->book.setPublisher(publisherRepository.findByName((String)bookHashMap.get(key)));
                case "Publication Date"->book.setPublicationDate((Date)bookHashMap.get(key));
                case "Checkout Duration"->{
                    Double duration=(Double) bookHashMap.get(key);
                    book.setCheckoutDuration(duration.intValue());
                }
                default->logger.trace("Key-set in hash doesn't match the key from model baby!");
            }
        }
        return book;
    }

    public HSSFWorkbook exportBookToExcel(){
        List<Book> books=bookRepository.findAllByIsDeleted(false);

        HSSFWorkbook workbook=new HSSFWorkbook();
        HSSFSheet sheet=workbook.createSheet("Book Sheet");
        HSSFRow attRow= sheet.createRow(0);
        attRow.createCell(0).setCellValue("ISBN");
        attRow.createCell(1).setCellValue("Title");
        attRow.createCell(2).setCellValue("Genre");
        attRow.createCell(3).setCellValue("Author");
        attRow.createCell(4).setCellValue("Quantity");
        attRow.createCell(5).setCellValue("Publisher");
        attRow.createCell(6).setCellValue("Publication Date");
        attRow.createCell(7).setCellValue("Checkout Duration");

        AtomicInteger dataRowNum=new AtomicInteger(1);
        books.forEach(book->{
            HSSFRow dataRow=sheet.createRow(dataRowNum.get());
            dataRow.createCell(0).setCellValue(book.getIsbn());
            dataRow.createCell(1).setCellValue(book.getTitle());
            dataRow.createCell(2).setCellValue(book.getGenre());
            dataRow.createCell(3).setCellValue(book.getAuthor());
            dataRow.createCell(4).setCellValue(book.getQuantity());
            dataRow.createCell(5).setCellValue(book.getPublisher().getName());
            dataRow.createCell(6).setCellValue(book.getPublicationDate());
            dataRow.createCell(7).setCellValue(book.getCheckoutDuration());
            dataRowNum.getAndIncrement();
        });
        return workbook;
    }

    @Override
    public List<String> getAttributes(Class tableClass) {
        List<String> attributes=new ArrayList<>();
        Metamodel metamodel=entityManager.getMetamodel();
        EntityType<?> entityType=metamodel.entity(tableClass);
        entityType.getAttributes().forEach(attribute->attributes.add(attribute.getName()));
        return attributes;
    }

//    @Override
//    public void exportExcel(HttpServletResponse response) throws IOException {
//        List<Book> books=bookRepository.findAllByIsDeleted(false);
//
//        HSSFWorkbook workbook=new HSSFWorkbook();
//        HSSFSheet sheet=workbook.createSheet();
//        HSSFRow row=sheet.createRow(0);
//        row.createCell(0).setCellValue("ISBN");
//        row.createCell(1).setCellValue("Title");
//        row.createCell(2).setCellValue("Author");
//        row.createCell(3).setCellValue("Publication Date");
//        row.createCell(4).setCellValue("Genre");
//        row.createCell(5).setCellValue("Quantity");
//        row.createCell(6).setCellValue("Checkout Duration (Days)");
//        row.createCell(7).setCellValue("Publisher");
//
//        AtomicReference<Integer> dataRowNum= new AtomicReference<>(1);
//        books.forEach(book->{
//            HSSFRow dataRow=sheet.createRow(dataRowNum.get());
//            dataRow.createCell(0).setCellValue(book.getIsbn());
//            dataRow.createCell(1).setCellValue(book.getTitle());
//            dataRow.createCell(2).setCellValue(book.getAuthor());
//            dataRow.createCell(3).setCellValue(book.getPublicationDate());
//            dataRow.createCell(4).setCellValue(book.getGenre());
//            dataRow.createCell(5).setCellValue(book.getQuantity());
//            dataRow.createCell(6).setCellValue(book.getCheckoutDuration());
//            dataRow.createCell(7).setCellValue(book.getPublisher().getName());
//            dataRowNum.getAndSet(dataRowNum.get() + 1);
//        });
//
//        ServletOutputStream outputStream=response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//    }


}
