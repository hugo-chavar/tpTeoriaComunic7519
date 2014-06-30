package genetico;

import java.util.Random;

public class Individuo implements Comparable<Object> {

	private double aptitud;
	private float valor;
	public static Random rand = new Random();

	public double getAptitud() {
		return aptitud;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getValor() {
		return valor;
	}

	public Individuo() {
		this.valor = rand.nextFloat() * 200 - 100; // ramdom entre -100 y 100
		calcularAptitud();
	}

	public Individuo(float valor) {
		this.valor = valor;
		calcularAptitud();
	}

	public void calcularAptitud() {
		this.aptitud = Math.pow(this.valor, 2) + 4 * this.valor - 4;
	}

	@Override
	public String toString() {
		return "Individuo [ valor=" + String.format("%.5f", this.valor)
				+ ",aptitud= " + String.format("%.5f", this.aptitud) + "]";
	}

	public Individuo reproducirCon(Individuo other) {

		Individuo hijo = new Individuo(
				(this.getValor() + other.getValor()) / 2);
		return (hijo);
	}

	@Override
	public int compareTo(Object obj) {

		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == obj) {
			return EQUAL;
		}
		if (obj == null) {
			return EQUAL;
		}
		if (!(obj instanceof Individuo)) {
			return BEFORE;
		}

		Individuo other = (Individuo) obj;

		if (this.getAptitud() > other.getAptitud())
			return BEFORE;
		if (this.getAptitud() < other.getAptitud())
			return AFTER;

		return EQUAL;
	}
}
