package ad.ad_ud2_a;

import static org.junit.Assert.*;

import org.junit.Test;

import prubasUnitarias.Calculadora;

public class CalculadoraTest {

	@Test
	public void testSumar() {
		Calculadora calc = new Calculadora();
		int resultado = calc.sumar(3, 5);
		assertEquals(8, resultado);
	}

	@Test
	public void testRestar() {
		Calculadora cal=new Calculadora();
		int resultado=cal.restar(10, 5);
		assertEquals(5, resultado);
	}

	@Test
	public void testMultiplicar() {
		Calculadora cal =new Calculadora();
		int resultado=cal.multiplicar(3, 3);
		assertEquals(9, resultado);
	}

	@Test
	public void testDividir() {
		Calculadora cal = new Calculadora();
		int resultado = cal.dividir(10, 5);
		assertEquals(2, resultado);
		
	}

	@Test(expected = ArithmeticException.class)
	public void testDividirPorCero() {
		Calculadora cal = new Calculadora();
		int resultado = cal.dividir(10, 0);
		assertEquals(ArithmeticException.class, resultado);
		
	}
}
