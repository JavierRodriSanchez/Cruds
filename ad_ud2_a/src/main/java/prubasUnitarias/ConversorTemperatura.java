package prubasUnitarias;

public class ConversorTemperatura {

	public double celsiusAFahrenheit(double a) {
		double farenjein;
		farenjein=a*1.8+32 ;
		
		return farenjein;
		
		
		
	}
	
	 public double fahrenheitACelsius(double a) {
		 double celsius;
		 celsius=(a-32)/1.8;
		 return celsius;
	 }
	
	 
	 
}
