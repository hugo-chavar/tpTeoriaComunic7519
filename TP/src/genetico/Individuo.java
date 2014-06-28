package genetico;

import java.util.Random;

public class Individuo implements Comparable<Object> {

	private double aptitud;
	private float valor;
	private double probabilidadMutacion;
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

	public Individuo(double probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
		this.valor = rand.nextFloat() * 200 - 100; // ramdom entre -100 y 100
		calcularAptitud();
	}

	public Individuo(float valor, double probabilidadMutacion) {
		this.valor = valor;
		this.probabilidadMutacion = probabilidadMutacion;
		calcularAptitud();
	}

	public void calcularAptitud() {
		this.aptitud = Math.pow(this.valor, 2) + 4 * this.valor - 4;
	}

	public void mutar() {
		if (rand.nextDouble() < this.probabilidadMutacion) {
			this.realizarMutacion();
			calcularAptitud();
		}

	}

	public void realizarMutacion() {
		// realizo una intercambio de 3 bits de los 32 que tiene un float
		// los la posicion de los bits a cambiar se elije de manera aleatoria
		// con una distribucion normal
		int valorComoBitsDeEntero = Float.floatToIntBits(this.valor);
		int aux = 0;
		int cambiosValidos = 3;
		while (cambiosValidos > 0) {
			// los bits mas probables de ser cambiados
			// son los centrales
			int posicion = (int) (3 * rand.nextGaussian() + 15); 
			aux |= 1 << posicion;
			cambiosValidos--;
		}

		valorComoBitsDeEntero ^= (aux & 0xffffffff);
		this.valor = Float.intBitsToFloat(valorComoBitsDeEntero);
		calcularAptitud();
	}

	@Override
	public String toString() {
		return "Individuo [ valor=" + String.format("%.5f", this.valor)
				+ ",aptitud= " + String.format("%.5f", this.aptitud) + "]";
	}

	public Individuo reproducirCon(Individuo other) {

		Individuo hijo = new Individuo(
				(this.getValor() + other.getValor()) / 2,
				this.probabilidadMutacion);
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
