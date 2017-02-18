package consol.libr.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import consol.libr.config.DatabaseConfig;
import consol.libr.dao.BookDao;
import consol.libr.entity.Book;

public class Mains {
	@Autowired
	static BookDao dao;

	public static void main(String[] args) throws IOException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class);
		dao = context.getBean(BookDao.class);
		while (true) {
			System.out.print("Hello enter command : all , remove, add  or edit . After command use the following sign \":\" ");
			String a = getString();
			String[] el = a.split("[\\p{Punct}]");
			String action = el[0].trim();
			switch (action) {
			case "add":
				addNewBook(el[1].trim(), el[2].trim());
				break;
			case "remove":
				delete(el[1].trim());
				break;
			case "edit":
				update(el[1].trim());
				break;
			case "all":
				printAll();
				break;
			default:
				System.out.println("Invalid entry\n");
			}
		}
	}

	private static void update(String name) throws IOException {
		List<Book> list = dao.findAllbyName(name);
		int i = 0;
		Book book;
		if (list.size() > 1) {
			System.out.println("We have few books with such name please choose one by typing a number of book:");
			for (Book b : list) {
				book = b;
				System.out.println(i + " author  " + b.getAuthor() + " name " +"\"" +b.getName()+"\"");
				i++;
			}
			book = list.get(getInt());
		}
		book = list.get(0);
		System.out.println("please enter a new name ");
		String s = getString();
		book.setName(s);
		dao.update(book);
		System.out.println("book Author " + book.getAuthor() + " name : " + "\""+book.getName() + "\" was edited");
	}

	private static void printAll() {
		List<Book> list = dao.findAll();
		if (list.isEmpty()) {
			System.out.println("Unfortunately there are no books in the database");
			return;
		}
		System.out.println("Our books:");
		for (Book book : list) {
			System.out.println(book);
		}
	}

	private static void addNewBook(String author, String name) {
		Book b = new Book();
		b.setAuthor(author);
		b.setName(name);
		dao.add(b);
		System.out.println("book author: " + b.getAuthor() + " book name: " + "\""+b.getName() +"\" was added");

	}

	public static String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}

	public static int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}

	public static void delete(String name) throws IOException {
		List<Book> list = dao.findAllbyName(name);
		int i = 0;
		Book book;
		if (list.size() > 1) {
			System.out.println("We have few books with such name please choose one by typing a number of book:");
		for (Book b : list) {
			book = b;
			System.out.println(i + " remove author  " + b.getAuthor() + " name " +"\""+ b.getName()+"\"");
			i++;
		}
		book = list.get(getInt());
		}
		book = list.get(0);
		dao.delete(book);
		System.out.println("book Author " + book.getAuthor() + " name : "+"\"" + book.getName() + "\" was deleted");
	}
}
