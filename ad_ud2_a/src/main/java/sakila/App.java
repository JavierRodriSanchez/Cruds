package sakila;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;



public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SakilaImpl films = new SakilaImpl();
		//Todas las peliculas;
		Set<Film> allFilms = films.getAllFilms();

		/*
		 * for (Film film : allFilms) { System.out.println(film);
		 * 
		 * }
		 * 
		
		 * 
		 * //cantidad de peliculas int total= films.countAllFilms();
		 * System.out.println(total);
		 * 
		 * //peliculas por actor allFilms= films.getAllFilmsByActor("PENELOPE", true);
		 * for (Film film2 : allFilms) { System.out.println(film2); }
		 */
		
		
		 //Pelicula en concreto
		 Film film= films.getFilmById(1001);
		  System.out.println(film);
		// lista de cadenas de nombreCategorias
		/*List<String> listaCategorias = new ArrayList<String>();

		listaCategorias.add("Action");
		listaCategorias.add("Animation");

		allFilms = films.getAllFilms(listaCategorias);
		for (Film f : allFilms) {
			System.out.println(f);
		}

		try {
			films.bd();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//JSONArray array = films.getMetadataAsJson();
		*/
		//System.out.println(array);
		BigDecimal rentalRate =new BigDecimal("0.8");
		BigDecimal replacementCost=new BigDecimal("122");
		
		Timestamp last_Update=new Timestamp(System.currentTimeMillis());
		
		//Crear nueva pelicula
		
		
		//films.createFilm(new Film(1001, "paco de lucia","el mejor de todos los tiempo", (short) 24,1, null, 27,rentalRate, 133,replacementCost, "R",last_Update));
		
		//Actualizar la pelicula
		//films.updateFilm(new Film(1001, "paco de lucia","bueno no tanto", (short) 24,1, null, 27,rentalRate, 133,replacementCost, "R",last_Update));
		//;
		  
		//delete pelicula
		//films.deleteFilm(1001);  
		  
	}

}
