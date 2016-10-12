package management.dao;

import java.util.List;

import management.entity.Book;
public interface BookDao {
	public List<Book> getBookByName(String name);
	public List<Book> getBookByNameFuzzy(String name);
	public List<Book> getBookByWriter(String writer);
	public List<Book> getBookByWriterFuzzy(String writer);
	public List<Book> getAllBooks();
	public Integer deleteByPrimaryKey(List<String> list);
	public Integer ifBookSame(Book book);
	public  void addBook(Book book);
	public Integer updateBook(Book book);
}
