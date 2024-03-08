package prubasUnitarias;


import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;

    public Library() {
    	books=new ArrayList<Book>();
    }

    public void addBook(Book book) {
    	books.add(book);
    }

    public List<Book> findBooksByAuthor(String author) {
    	List<Book>tmp=new ArrayList<Book>();
    	for (Book book : books) {
			if(book.getAuthor().contains(author)) {
				tmp.add(book);
			}
		}
		return tmp;

    }

    public boolean removeBook(String title) {
    	List<Book>tmp=new ArrayList<Book>();
    	for (Book book : books) {
			if(book.getTitle().contains(title)) {
				tmp.remove(book);
				return true;
			}
			
		}
    	
		return false;

    }

    public int getTotalBooks() {
    	System.out.println(books.size());
		return books.size();

    }

    // Otros métodos relevantes para la gestión de libros en la biblioteca
}