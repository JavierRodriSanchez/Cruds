package sakila;

import java.util.Set;

public interface PatronDAO<T> {

	Set<T> getAllFilms();

	T getFilmById(int filmId);

	int countAllFilms();

}
