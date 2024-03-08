package sakila;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.mysql.cj.jdbc.DatabaseMetaData;


public class SakilaImpl implements PatronDAO<Film>, FilmjdbcDAO {

	private static final String URL = "jdbc:mysql://127.0.0.1:3306/sakila?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "castelao";
	private static final String GETALLFILMS = "SELECT* FROM FILM";
	private static final String GETFILMBYID = "SELECT* FROM FILM WHERE FILM_ID = ? ";
	private static final String COUNTALLFILMS = "SELECT COUNT(*) FROM FILM";
	private static final String sqlDelete="DELETE FROM FILM WHERE FILM_ID = ?";
	//private static final String CREATEFILM="INSERT INTO FILM(TITLE,DESCRIPTION,RELEASE_YEAR,LANGUAGE_ID,ORIGINAL_LANGUAGE_ID,"
	@Override
	public Set<Film> getAllFilms() {
		// TODO Auto-generated method stub
		Set<Film> films = new HashSet<Film>();

		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement statement = connection.prepareStatement(GETALLFILMS);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int filmId = resultSet.getInt("film_id");
				String title = resultSet.getString("title");
				String description = resultSet.getString("description");
				short releaseYear = resultSet.getShort("release_year");
				int languageId = resultSet.getInt("language_id");
				int originallanguageid = resultSet.getInt("original_language_id");
				int rentalDuration = resultSet.getInt("rental_duration");
				BigDecimal rentalRate = resultSet.getBigDecimal("rental_rate");
				int length = resultSet.getInt("length");
				BigDecimal replacementCost = resultSet.getBigDecimal("replacement_cost");
				String rating = resultSet.getString("rating");
				// Preguntar a ruben por que no necesito leer el SET
				// resultSet.getString(11);
				Timestamp lastUpdate = resultSet.getTimestamp("last_update");

				films.add(new Film(filmId, title, description, releaseYear, languageId, originallanguageid,
						rentalDuration, rentalRate, length, replacementCost, rating, lastUpdate));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public Film getFilmById(int filmId) {
		Film film = null;
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(GETFILMBYID);
			preparedStatement.setInt(1, filmId);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				int filmsById = resultSet.getInt("film_id");
				String title = resultSet.getString("title");
				String description = resultSet.getString("description");
				short releaseYear = resultSet.getShort("release_year");
				int languageId = resultSet.getInt("language_id");
				int originallanguageid = resultSet.getInt("original_language_id");
				int rentalDuration = resultSet.getInt("rental_duration");
				BigDecimal rentalRate = resultSet.getBigDecimal("rental_rate");
				int length = resultSet.getInt("length");
				BigDecimal replacementCost = resultSet.getBigDecimal("replacement_cost");
				String rating = resultSet.getString("rating");
				// Preguntar a ruben por que no necesito leer el SET
				// resultSet.getString(11);
				Timestamp lastUpdate = resultSet.getTimestamp("last_update");

				film = new Film(filmsById, title, description, releaseYear, languageId, originallanguageid,
						rentalDuration, rentalRate, length, replacementCost, rating, lastUpdate);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return film;
	}

	@Override
	public int countAllFilms() {
		int count = 0;
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(COUNTALLFILMS);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}

	// Método para obtener películas por actor y ordenarlas
	@Override
	public Set<Film> getAllFilmsByActor(String actor, boolean orderAsc) {
		String query = "SELECT f .* FROM film f " + "JOIN film_actor fa ON f.film_id = fa.film_id "
				+ "JOIN actor a ON fa.actor_id = a.actor_id " + "WHERE a.first_name = ? " + "ORDER BY f.film_id "
				+ (orderAsc ? "ASC" : "DESC");

		// Configurar la conexión a la base de datos
		try (Connection connection = DriverManager
				.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			// Configurar el parámetro del actor en la consulta
			preparedStatement.setString(1, actor);

			// Ejecutar la consulta
			ResultSet resultSet = preparedStatement.executeQuery();

			// Procesar los resultados y construir el conjunto de películas
			Set<Film> films = new HashSet<>();
			while (resultSet.next()) {
				Film film = new Film();
				film.setFilmId(resultSet.getInt("film_id"));
				film.setTitle(resultSet.getString("title"));
				film.setDescription(resultSet.getString("description"));
				film.setReleaseYear(resultSet.getShort("release_year"));
				film.setLanguageId(resultSet.getInt("language_id"));
				film.setOriginalLanguageId(resultSet.getInt("original_language_id"));
				film.setRentalDuration(resultSet.getInt("rental_duration"));
				film.setRentalRate(resultSet.getBigDecimal("rental_rate"));
				film.setLength(resultSet.getInt("length"));
				film.setReplacementCost(resultSet.getBigDecimal("replacement_cost"));
				film.setRating(resultSet.getString("rating"));
				film.setLastUpdate(resultSet.getTimestamp("last_update"));
				films.add(film);
			}

			return films;

		} catch (SQLException e) {
			e.printStackTrace(); // Manejar la excepción apropiadamente en una aplicación real
			return null;
		}
	}

	@Override
	public Set<Film> getAllFilms(List<String> categoriesName) {
		String query = "SELECT DISTINCT f.* " + "FROM film f " + "JOIN film_category fc ON f.film_id = fc.film_id "
				+ "JOIN category c ON fc.category_id = c.category_id " + "WHERE c.name IN ("
				+ String.join(",", Collections.nCopies(categoriesName.size(), "?")) + ")";

		Set<Film> films = new HashSet<>();
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(query);

			// Establecer los valores de los parámetros
			for (int i = 0; i < categoriesName.size(); i++) {
				preparedStatement.setString(i + 1, categoriesName.get(i));
			}

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Film film = new Film();
				film.setFilmId(resultSet.getInt("film_id"));
				film.setTitle(resultSet.getString("title"));
				film.setDescription(resultSet.getString("description"));
				film.setReleaseYear(resultSet.getShort("release_year"));
				film.setLanguageId(resultSet.getInt("language_id"));
				film.setOriginalLanguageId(resultSet.getInt("original_language_id"));
				film.setRentalDuration(resultSet.getInt("rental_duration"));
				film.setRentalRate(resultSet.getBigDecimal("rental_rate"));
				film.setLength(resultSet.getInt("length"));
				film.setReplacementCost(resultSet.getBigDecimal("replacement_cost"));
				film.setRating(resultSet.getString("rating"));
				film.setLastUpdate(resultSet.getTimestamp("last_update"));

				films.add(film);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;

	}

	@SuppressWarnings("unchecked")
	public JSONArray getMetadataAsJson() {

		JSONArray jsonArray = new JSONArray();
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM FILM");
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("film_id", resultSet.getInt("film_id"));
				jsonObject.put("title", resultSet.getString("title"));
				jsonObject.put("description", resultSet.getString("description"));
				jsonObject.put("release_year", resultSet.getShort("release_year"));
				jsonObject.put("language_id", resultSet.getInt("language_id"));
				jsonObject.put("original_language_id", resultSet.getInt("original_language_id"));
				jsonObject.put("rental_duration", resultSet.getInt("rental_duration"));
				jsonObject.put("rental_rate", resultSet.getBigDecimal("rental_rate"));
				jsonObject.put("length", resultSet.getInt("length"));
				jsonObject.put("replacement_cost", resultSet.getBigDecimal("replacement_cost"));
				jsonObject.put("rating", resultSet.getString("rating"));
				jsonObject.put("last_update", resultSet.getTimestamp("last_update").toString());

				jsonArray.add(jsonObject);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return jsonArray;
	}

	public void bd() throws SQLException {

		Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
		DatabaseMetaData dbMet = (DatabaseMetaData) con.getMetaData();
		if (dbMet == null)
			System.out.println("No hay información de MetaData");
		else {
			System.out.println("Tipo de la BD: " + dbMet.getDatabaseProductName());
			System.out.println("Versión : " + dbMet.getDatabaseProductVersion());
			System.out.println("Cantidad máxima de conexiones activas: " + dbMet.getMaxConnections());
			System.out.println(dbMet.getMaxCatalogNameLength());
		}

	}

	@Override
	public int createFilm(Film film) {
		String sql="INSERT INTO FILM (FILM_ID,TITLE,DESCRIPTION,RELEASE_YEAR,LANGUAGE_ID,"
				+ "ORIGINAL_LANGUAGE_ID,RENTAL_DURATION,RENTAL_RATE,LENGTH,REPLACEMENT_COST,RATING,LAST_UPDATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
	
		try {
			Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
		
			preparedStatement.setInt(1, film.getFilmId());
			preparedStatement.setString(2,film.getTitle());
			preparedStatement.setString(3, film.getDescription());
			preparedStatement.setShort(4, film.getReleaseYear());
			preparedStatement.setInt(5, film.getLanguageId());
			Integer originalLanguageId = film.getOriginalLanguageId();
	        preparedStatement.setObject(6, originalLanguageId != null ? originalLanguageId.intValue() : null);
			preparedStatement.setInt(7, film.getRentalDuration());
			preparedStatement.setBigDecimal(8, film.getRentalRate());
			preparedStatement.setInt(9, film.getLength());
			preparedStatement.setBigDecimal(10, film.getReplacementCost());
			preparedStatement.setString(11, film.getRating());
			preparedStatement.setTimestamp(12,film.getLastUpdate());
			
			preparedStatement.executeUpdate();
			System.out.println("lo añadio");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("nada de nada");
			e.printStackTrace();
		}
		
		
		return 0;
	}

	@Override
	public void updateFilm(Film film) {
		// TODO Auto-generated method stub
		String sqlUpdate = "UPDATE FILM SET DESCRIPTION = ? WHERE FILM_ID = ?";
		try {
			
			Connection connection =DriverManager.getConnection(URL,USER,PASSWORD);
			
			PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
			
			preparedStatement.setString(1, film.getDescription());
			preparedStatement.setInt(2, film.getFilmId());
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void deleteFilm(int filmId) {
		
		try {
			Connection connection= DriverManager.getConnection(URL,USER,PASSWORD);
			PreparedStatement preparedStatement=connection.prepareStatement(sqlDelete);
			preparedStatement.setInt(1, filmId);
			preparedStatement.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
