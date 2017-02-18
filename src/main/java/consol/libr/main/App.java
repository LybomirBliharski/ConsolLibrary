package consol.libr.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import consol.libr.dao.BookDao;
import consol.libr.entity.Book;

public class App {
	@Autowired
	BookDao dao;

	void run() throws IOException {
		while (true) {
			System.out.print("Hello enter command : all , remove, add  or edit . After command use the following sign \":\" ");
			String a = input();
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

	private void printAll() {
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

	private void addNewBook(String author, String name) {
		Book book = new Book();
		book.setAuthor(author);
		book.setName(name);
		dao.add(book);
		System.out.println("book author: " + book.getAuthor() + " book name: " + "\"" + book.getName() + "\" was added");

	}

	private Book printAllWithSameName(String name) throws IOException {
		List<Book> bookList = dao.findAllbyName(name);
		Book book;
		if (bookList.size() > 1) {
			System.out.println("We have few books with such name please choose one by typing a number of book:");
			for (int i = 0; i < bookList.size(); i++) {
				System.out.println(i + " author  " + bookList.get(i).getAuthor() + " name " + "\"" + bookList.get(i).getName() + "\"");
			}
			book = bookList.get(getInt());
		} else {
			book = bookList.get(0);
		}
		return book;
	}

	private void update(String name) throws IOException {
		Book book = printAllWithSameName(name);
		System.out.println("please enter a new name ");
		String s = getString();
		book.setName(s);
		dao.update(book);
		System.out.println("book Author " + book.getAuthor() + " name : " + "\"" + book.getName() + "\" was edited");
	}

	public void delete(String name) throws IOException {
		Book book = printAllWithSameName(name);
		dao.delete(book);
		System.out.println("book Author " + book.getAuthor() + " name : " + "\"" + book.getName() + "\" was deleted");
	}

	public String input() throws IOException {
		String inputString = getString();
		return inputString;
	}

	public String getString() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
	}

	public int getInt() throws IOException {
		String s = getString();
		return Integer.parseInt(s);
	}
}
