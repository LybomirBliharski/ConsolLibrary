package consol.libr.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import consol.libr.dao.BookDao;
import consol.libr.entity.Book;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {
	@PersistenceContext
	private EntityManager entityManager;

	
	public void add(Book book) {
		entityManager.persist(book);
		
	}
	
	public List<Book> findAll() {
		List<Book> list = entityManager.createNativeQuery("SELECT * FROM books", Book.class).getResultList();
        return list;

	}
	
	public List<Book> findAllbyName(String name) {
return entityManager.createQuery("SELECT book FROM Book book WHERE book.name = :name", Book.class)
				.setParameter("name", name).getResultList();
	}
	
	public void delete(Book book) {
		entityManager.remove(entityManager.merge(book));
	}
	
	public Book update(Book book) {
		return entityManager.merge(book);
	}

}
