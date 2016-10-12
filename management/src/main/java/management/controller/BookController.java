package management.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import management.entity.Book;
import management.dao.BookDao;
import management.dao.PersonDao;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping(value = "/views")
public class BookController {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext(
			"spring-mybatis.xml");
	static BookDao bookDao = (BookDao) ctx.getBean("bookDao");
	@SuppressWarnings("deprecation")
	public static boolean xlsx2entity(Workbook workbook) throws Exception{
		int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
		System.out.println(sheetCount);
		for (int s = 0; s < sheetCount; s++) {
			Sheet sheet = workbook.getSheetAt(s);
			int rows = sheet.getPhysicalNumberOfRows();
			if (rows == 0)
				return false;
			Row row = sheet.getRow(0);
			int lines = row.getPhysicalNumberOfCells();
			String methods[] = new String[lines];
			for (int l = 0; l < lines; l++) {
				Cell cell = row.getCell(l);
				String method = cell.getStringCellValue();
				switch (method) {
				case "书名":
					methods[l] = "Name";
					break;
				case "作者":
					methods[l] = "Writer";
					break;
				case "价格":
					methods[l] = "Price";
					break;
				case "数量":
					methods[l] = "Number";
					break;
				}
			}
			for (int r = 1; r < rows; r++) {
				Book book = new Book();
				row = sheet.getRow(r);
				for (int l = 0; l < lines; l++) {
					Cell cell = row.getCell(l);
					switch(cell.getCellType()){
					case Cell.CELL_TYPE_STRING:book.getClass().getMethod("set" + methods[l],String.class)
					.invoke(book, cell.getStringCellValue());break;
					case Cell.CELL_TYPE_NUMERIC:
						book.getClass().getMethod("set" + methods[l],double.class)
							.invoke(book, cell.getNumericCellValue());break;
					}
					
				}
				if (bookDao.ifBookSame(book)!=0) {
					return false;
				} else {
					book.setId();
					bookDao.addBook(book);
				}
			}
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/addorupdate", method=POST)
	public @ResponseBody Map<String, Object> addOrUpdate(@RequestBody Book book){
		//Book book = new Book();
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("success", false);
		if (book.getId() == "") {
			if (bookDao.ifBookSame(book)!=0) {
				//map.replace("success", true);
				return map;
			} else {
				book.setId();
				bookDao.addBook(book);
				map.replace("success", true);
				map.put("id", book.getId());
			}
		} else {
			map.replace("success", bookDao.updateBook(book)==1);
		}
		return map;
	}
	
	@RequestMapping(value = "/delete")
	public @ResponseBody boolean delete(@RequestBody List<String> ids){
		return bookDao.deleteByPrimaryKey(ids)==ids.size();
	}
	@RequestMapping(value = "/selectbyname")
	public @ResponseBody List<Book> select(@RequestBody Map<String, Object> map) {
		String name = map.get("name").toString();
		if (!(Boolean) map.get("ischecked")) {
			List<Book> list = bookDao.getBookByName(name);
			return list;
		} else {
			name = "%" + name + "%";
			List<Book> list = bookDao.getBookByNameFuzzy(name);
			return list;
		}
	}

	@RequestMapping(value = "/selectbywriter")
	public @ResponseBody List<Book> selectbywriter(@RequestBody Map<String, Object> map) {
		String name = map.get("writer").toString();
		if (!(Boolean) map.get("ischecked")) {
			List<Book> list = bookDao.getBookByWriter(name);
			return list;
		} else {
			name = "%" + name + "%";
			List<Book> list = bookDao.getBookByWriterFuzzy(name);
			return list;
		}
	}

	@RequestMapping(value = "/select")
	public @ResponseBody List<Book> select() {
		List<Book> list = bookDao.getAllBooks();
		return list;
	}

	@RequestMapping(value = "/uploadxls", method = POST)
	public @ResponseBody boolean uploadXls(
			@RequestParam(value = "file", required = false) MultipartFile file) {
		try {
			InputStream is = file.getInputStream();
			Workbook workbook = new XSSFWorkbook(is);
			return xlsx2entity(workbook);
			/*int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
			System.out.println(sheetCount);
			for (int s = 0; s < sheetCount; s++) {
				Sheet sheet = workbook.getSheetAt(s);
				int rows = sheet.getPhysicalNumberOfRows();
				if (rows == 0)
					return false;
				Row row = sheet.getRow(0);
				int lines = row.getPhysicalNumberOfCells();
				System.out.println(row.getPhysicalNumberOfCells());
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error here.");
		}

		return false;
	}
}
