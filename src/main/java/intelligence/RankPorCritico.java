package intelligence;

public class RankPorCritico {
    public Critico critico;
    public double similarity;

    public RankPorCritico() {
    }

    public RankPorCritico(Critico critico, double similarity) {
        this.critico = critico;
        this.similarity = similarity;
    }

    public Critico getCritico() {
        return critico;
    }

    public void setCritico(Critico critico) {
        this.critico = critico;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }
}
