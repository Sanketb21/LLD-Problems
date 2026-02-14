package LibraryBookBorrowingSystem;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LibraryService {
	private final Map<String, Book> booksMap = new ConcurrentHashMap<>();
	
	public void addBook(Book book) {
		booksMap.put(book.getIsbn(), book);
	}
	
	public boolean borrowBook(String isbn) {
		try {
			if(!booksMap.containsKey(isbn)) {
				throw new NoSuchElementException("No book of isbn : " + isbn + " available in the library");
			}
			
			Book book = booksMap.get(isbn);
			
			synchronized(book) {
				if(book.getAvailableCopies() <= 0) {
					throw new IllegalStateException("No available copies for book : " + isbn);
				}
				else {
					book.updateAvailableCopies(-1);
					return true;
				}
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public boolean returnBook(String isbn) {
		try {
			if(!booksMap.containsKey(isbn)) {
				throw new NoSuchElementException("No book of isbn : " + isbn + " available in the library");
			}
			
			Book book = booksMap.get(isbn);
			
			synchronized(book) {
				book.updateAvailableCopies(1);
				return true;
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	List<Book> getAvailableBooksByAuthor(String author){
		return booksMap.values().stream().filter(a -> a.getAuthor().equalsIgnoreCase(author))
									.filter(a -> a.getAvailableCopies() > 0)
									.sorted(Comparator.comparing(Book::getTitle))
									.collect(Collectors.toList());
	}
	
	public static void main(String[] args) {
		LibraryService library = new LibraryService();
		
		library.addBook(new Book("123", "Da Vinci Code", "Dan Brown", 200));
		library.addBook(new Book("124", "Angels & Demons", "Dan Brown", 150));
		library.addBook(new Book("125", "Inferno", "Dan Brown", 180));
		library.addBook(new Book("126", "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 300));
		library.addBook(new Book("127", "Harry Potter and the Chamber of Secrets", "J.K. Rowling", 250));
		library.addBook(new Book("128", "The Hobbit", "J.R.R. Tolkien", 1));
		library.addBook(new Book("129", "The Lord of the Rings", "J.R.R. Tolkien", 100));
		library.addBook(new Book("130", "Pride and Prejudice", "Jane Austen", 1));
		library.addBook(new Book("131", "1984", "George Orwell", 0));
		library.addBook(new Book("132", "To Kill a Mockingbird", "Harper Lee", 110));
		
		System.out.println(library.borrowBook("126") == true ? "Book Borrowed" : "Can't borrow book");
		System.out.println(library.borrowBook("129") == true ? "Book Borrowed" : "Can't borrow book");
		System.out.println(library.borrowBook("130") == true ? "Book Borrowed" : "Can't borrow book");
		System.out.println(library.borrowBook("131") == true ? "Book Borrowed" : "Can't borrow book");
		System.out.println(library.borrowBook("145") == true ? "Book Borrowed" : "Can't borrow book");
		
		System.out.println(library.returnBook("123") == true ? "Book Returned" : "Can't return book");
		System.out.println(library.returnBook("131") == true ? "Book Returned" : "Can't return book");
		System.out.println(library.returnBook("131") == true ? "Book Returned" : "Can't return book");
		System.out.println(library.returnBook("132") == true ? "Book Returned" : "Can't return book");
		System.out.println(library.returnBook("150") == true ? "Book Returned" : "Can't return book");
		
		List<Book> danBrownBooks = library.getAvailableBooksByAuthor("dan brown");
		System.out.println(danBrownBooks);
		
		List<Book> jkrowlingbooks = library.getAvailableBooksByAuthor("j.k. rowling");
		System.out.println(jkrowlingbooks);
		
		System.out.println(library.getAvailableBooksByAuthor("Harper Lee")); 
		System.out.println(library.getAvailableBooksByAuthor("Chetan bhagat")); 
	}
	
}
