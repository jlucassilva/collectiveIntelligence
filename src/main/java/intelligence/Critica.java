package intelligence;

import java.util.Objects;

public class Critica {

	public Critico critico;
	public Filme filme;
	public Double nota;

	public Critica(Critico critico, Filme filme, Double nota) {
		this.critico = critico;
		this.filme = filme;
		this.nota = nota;
	}

	public Critica(Criticos critico, Filme filme, Double nota) {
		this.critico = new Critico(critico);
		this.filme = filme;
		this.nota = nota;
	}

	public Double getNota() {
		return nota;
	}

	public Filme getFilme() {
		return filme;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Critica critica = (Critica) o;
		return filme == critica.filme;
	}

	@Override
	public int hashCode() {
		return Objects.hash(filme);
	}
}
