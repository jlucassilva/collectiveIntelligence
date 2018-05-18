package intelligence;

public class RankPorFilme {
	public Filme filme;
	public double similaridade;


	public RankPorFilme() {
	}

	public RankPorFilme(Filme filme, double similaridade) {
		this.filme = filme;
		this.similaridade = similaridade;
	}

	public double getSimilaridade() {
		return similaridade;
	}
}
