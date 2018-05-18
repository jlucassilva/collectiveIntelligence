package intelligence;

import java.util.*;
import java.util.stream.Collectors;

public class Recomendacoes {

	public List<RankPorFilme> encontraRecomendacoes(Critico principal, List<Critico> outroCriticos) {
		outroCriticos.remove(principal);

		Map<Filme, Double> totals = new HashMap<>();
		Map<Filme, Double> totalSimilaridades = new HashMap<>();

		outroCriticos.forEach(critico -> {
			double similaridade = similaridadePearson(principal, critico);
			if (similaridade > 0) {
				critico.criticas.removeAll(principal.criticas);
				critico.criticas.forEach(critica -> {
					Double total = totals.get(critica.filme);
					if (total == null) total = 0.0;
					total += critica.nota * similaridade;
					totals.put(critica.filme, total);

					Double totalSim = totalSimilaridades.get(critica.filme);
					if (totalSim == null) totalSim = 0.0;

					totalSim += similaridade;
					totalSimilaridades.put(critica.filme, totalSim);
				});
			}
		});

		List<RankPorFilme> ranking = new ArrayList<>();
		for (Filme filme : totals.keySet()) {
			double score = totals.get(filme) / totalSimilaridades.get(filme);
			ranking.add(new RankPorFilme(filme, score));
		}
		ranking.sort(Comparator.comparing(RankPorFilme::getSimilaridade).reversed());
		return ranking;
	}

	public List<RankPorCritico> comparaTodosCom(Critico principal, List<Critico> outroCriticos) {
		outroCriticos.remove(principal);
		return outroCriticos.stream()
				.map(outro -> new RankPorCritico(outro, similaridadePearson(principal, outro)))
				.sorted((Comparator.comparingDouble(RankPorCritico::getSimilarity).reversed()))
				.collect(Collectors.toList());
	}

	public double similaridadeEuclidiana(Critico principal, Critico outro) {
		double somatorioDasSimilaridades = principal.criticas.stream()
				.map(criticaPrincipal -> {
					Critica criticaOutro = encontraCriticaDoFilme(criticaPrincipal.filme, outro.criticas);
					if (criticaOutro != null)
						return euclideanDistanceScore(criticaPrincipal.nota, criticaOutro.nota);
					return 0;
				}).mapToDouble(Number::doubleValue)
				.sum();
		return 1 / (1 + Math.sqrt(somatorioDasSimilaridades));
	}

	private static double euclideanDistanceScore(double nota1, double nota2) {
		return Math.pow(nota1 - nota2, 2);
	}

	public double similaridadePearson(Critico principal, Critico outro) {

		Set<Critica> criticasPrincipal = new HashSet<>(principal.criticas);
		Set<Critica> criticasOutro = new HashSet<>(outro.criticas);

		criticasPrincipal.retainAll(criticasOutro);
		criticasOutro.retainAll(criticasPrincipal);

		long n = criticasPrincipal.size();
		double totalNotasPrincipal = obterSomaDasNotas(criticasPrincipal);
		double totalNotasOutro = obterSomaDasNotas(criticasOutro);

		double totalDosQuadradosPrincipal = obterSomaDosQuadrados(criticasPrincipal);
		double totalDosQuadradosOutro = obterSomaDosQuadrados(criticasOutro);

		double totalDosProdutos = obterSomaDosProdutos(criticasPrincipal, criticasOutro);

		double pearsonScore = (n * totalDosProdutos) - (1.0 * totalNotasPrincipal * totalNotasOutro);
		double den1 = Math.sqrt((n * totalDosQuadradosPrincipal) - Math.pow(totalNotasPrincipal, 2));
		double den2 = Math.sqrt((n * totalDosQuadradosOutro) - Math.pow(totalNotasOutro, 2));
		double den = 1.0 * den1 * den2;

		if (den == 0) return 0;

		return pearsonScore / den;
	}

	private double obterSomaDasNotas(Set<Critica> criticas) {
		return criticas.stream()
				.map(Critica::getNota)
				.mapToDouble(Number::doubleValue)
				.sum();
	}

	private double obterSomaDosQuadrados(Set<Critica> criticas) {
		return criticas.stream()
				.map(Recomendacoes::quadradoDaNota)
				.mapToDouble(Number::doubleValue)
				.sum();
	}

	private static Double quadradoDaNota(Critica critica) {
		return Math.pow(critica.nota, 2);
	}

	private double obterSomaDosProdutos(Set<Critica> principal, Set<Critica> outro) {
		principal = principal.stream().sorted(Comparator.comparing(Critica::getFilme)).collect(Collectors.toSet());
		outro = outro.stream().sorted(Comparator.comparing(Critica::getFilme)).collect(Collectors.toSet());

		Iterator<Critica> pIt = principal.iterator();
		Iterator<Critica> oIt = outro.iterator();

		double somatorio = 0;

		while (pIt.hasNext() && oIt.hasNext()) {
			somatorio += pIt.next().nota * oIt.next().nota;
		}
		return somatorio;
	}

	private Critica encontraCriticaDoFilme(Filme filme, Set<Critica> criticas) {
		return criticas.stream()
				.filter(critica -> filme == critica.filme)
				.findFirst()
				.orElse(null);
	}
}
