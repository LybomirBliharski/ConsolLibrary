package consol.libr.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import consol.libr.dao.BookDao;
import consol.libr.entity.Book;

@Repository
public class BookDaoImpl implements BookDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void add(Book book) {
		entityManager.persist(book);

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Book> findAll() {
		return entityManager.createNativeQuery("SELECT * FROM books ORDER BY book_name ASC", Book.class).getResultList();

	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Book> findAllbyName(String name) {
		return entityManager.createQuery("SELECT book FROM Book book WHERE book.name = :name", Book.class).setParameter("name", name).getResultList();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delete(Book book) {
		entityManager.remove(entityManager.merge(book));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Book update(Book book) {
		return entityManager.merge(book);
	}

}
