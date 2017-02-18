package consol.libr.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import consol.libr.entity.Book;

@Repository
public interface BookDao {
	public void add(Book book);

	public void delete(Book book);

	public List<Book> findAll();

	public List<Book> findAllbyName(String name);

	public Book update(Book book);
}
