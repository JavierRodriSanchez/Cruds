package sakila;

import java.util.List;
import java.util.Set;

public interface FilmjdbcDAO extends PatronDAO<Film> {

	Set<Film> getAllFilmsByActor(String autor, boolean orderAsc);

	Set<Film> getAllFilms(List<String> categoriesName);

	int createFilm(Film film);

	void updateFilm(Film film);

	void deleteFilm(int filmId);

}
