package intelligence;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class RecomendacoesTest {

	private List<Critica> criticas;
	private Recomendacoes recomendacoes;

	@Before
	public void setUp() throws Exception {
		criticas = inicializaCriticas();
		recomendacoes = new Recomendacoes();

	}

	@Test
	public void testaSimilaridadeEuclidiana() {
		Critico rose = new Critico(Criticos.Rose);
		Critico seymour = new Critico(Criticos.Seymour);
		rose.criticas = encontraCriticasDe(rose);
		seymour.criticas = encontraCriticasDe(seymour);

		double similaridadeEuclidiana = recomendacoes.similaridadeEuclidiana(rose, seymour);

		assertEquals(0.294298055086, similaridadeEuclidiana, 0.000000000001);
	}

	private Set<Critica> encontraCriticasDe(Critico critico) {
		return criticas.stream()
				.filter(critica -> critica.critico.equals(critico))
				.collect(Collectors.toSet());
	}

	@Test
	public void testaSimilaridadeComPearson() {
		Critico rose = new Critico(Criticos.Rose);
		Critico seymour = new Critico(Criticos.Seymour);
		rose.criticas = encontraCriticasDe(rose);
		seymour.criticas = encontraCriticasDe(seymour);


		double similaridadePearson = recomendacoes.similaridadePearson(rose, seymour);

		assertEquals(0.396059017191, similaridadePearson, 0.000000000001);
	}

	@Test
	public void rankingBaseadoEmUsuarioComPearson() {
		Critico critico = new Critico(Criticos.Toby);
		critico.criticas = encontraCriticasDe(critico);

		List<RankPorCritico> ranking = recomendacoes.comparaTodosCom(critico, todosOsCriticos());
		assertEquals(0.9912407071619302, ranking.get(0).similarity, 0.000000000001);
		assertEquals(0.9244734516419048, ranking.get(1).similarity, 0.000000000001);
		assertEquals(0.8934051474415647, ranking.get(2).similarity, 0.000000000001);
	}

	@Test
	public void deveTer3EmComum() {
		Critico toby = new Critico(Criticos.Toby);
		toby.criticas = encontraCriticasDe(toby);

		Critico rose = new Critico(Criticos.Rose);
		rose.criticas = encontraCriticasDe(rose);

		toby.criticas.retainAll(rose.criticas);
		assertEquals(3, toby.criticas.size());

		rose.criticas.retainAll(toby.criticas);
		assertEquals(3, rose.criticas.size());
	}

	@Test
	public void semEfeitoColateral() {
		Critico toby = new Critico(Criticos.Toby);
		toby.criticas = encontraCriticasDe(toby);

		Critico rose = new Critico(Criticos.Rose);
		rose.criticas = encontraCriticasDe(rose);

		Set<Critica> criticasT = new HashSet<>(toby.criticas);
		Set<Critica> criticasR = new HashSet<>(rose.criticas);

		criticasT.retainAll(criticasR);
		criticasR.retainAll(criticasT);

		assertEquals(3, criticasR.size());
		assertEquals(3, criticasT.size());

		assertEquals(3, toby.criticas.size());
		assertEquals(6, rose.criticas.size());
	}

	@Test
	public void deveRecomendarFilmes() {
		Critico critico = new Critico(Criticos.Toby);
		critico.criticas = encontraCriticasDe(critico);

		List<RankPorFilme> ranking = recomendacoes.encontraRecomendacoes(critico, todosOsCriticos());

		assertEquals(3.3477895267131013, ranking.get(0).similaridade, 0.000000000001);
		assertEquals(2.8325499182641614, ranking.get(1).similaridade, 0.000000000001);
		assertEquals(2.5309807037655645, ranking.get(2).similaridade, 0.000000000001);
	}

	private List<Critico> todosOsCriticos() {
		return new ArrayList<Critico>() {
			private static final long serialVersionUID = 6158524868409753618L;

			{
				add(criaCritico(Criticos.LaSalle));
				add(criaCritico(Criticos.Matthews));
				add(criaCritico(Criticos.Phillips));
				add(criaCritico(Criticos.Puig));
				add(criaCritico(Criticos.Rose));
				add(criaCritico(Criticos.Seymour));
				add(criaCritico(Criticos.Toby));
			}
		};
	}

	private Critico criaCritico(Criticos critico) {
		Critico temp = new Critico(critico);
		return new Critico(critico, encontraCriticasDe(temp));
	}

	public static List<Critica> inicializaCriticas() {
		List<Critica> criticas = new ArrayList<Critica>(){{
			add(new Critica(Criticos.Rose,Filme.LADY_WATER, 2.5));
			add(new Critica(Criticos.Rose,Filme.SNAKES_ON_A_PLANE, 3.5));
			add(new Critica(Criticos.Rose,Filme.JUST_MY_LUCK, 3.0));
			add(new Critica(Criticos.Rose,Filme.SUPERMAN_RETURNS, 3.5));
			add(new Critica(Criticos.Rose,Filme.YOU_ME_DUPREE, 2.5));
			add(new Critica(Criticos.Rose,Filme.NIGHT_LISTENER, 3.0));

			add(new Critica((Criticos.Seymour),Filme.LADY_WATER, 3.0));
			add(new Critica((Criticos.Seymour),Filme.SNAKES_ON_A_PLANE, 3.5));
			add(new Critica((Criticos.Seymour),Filme.JUST_MY_LUCK, 1.5));
			add(new Critica((Criticos.Seymour),Filme.SUPERMAN_RETURNS, 5.0));
			add(new Critica((Criticos.Seymour),Filme.YOU_ME_DUPREE, 3.5));
			add(new Critica((Criticos.Seymour),Filme.NIGHT_LISTENER, 3.0));

			add(new Critica((Criticos.Phillips),Filme.LADY_WATER, 2.5));
			add(new Critica((Criticos.Phillips),Filme.SNAKES_ON_A_PLANE, 3.0));
			add(new Critica((Criticos.Phillips),Filme.SUPERMAN_RETURNS, 3.5));
			add(new Critica((Criticos.Phillips),Filme.NIGHT_LISTENER, 4.0));

			add(new Critica((Criticos.Puig),Filme.SNAKES_ON_A_PLANE, 3.5));
			add(new Critica((Criticos.Puig),Filme.JUST_MY_LUCK, 3.0));
			add(new Critica((Criticos.Puig),Filme.SUPERMAN_RETURNS, 4.0));
			add(new Critica((Criticos.Puig),Filme.YOU_ME_DUPREE, 2.5));
			add(new Critica((Criticos.Puig),Filme.NIGHT_LISTENER, 4.5));

			add(new Critica((Criticos.LaSalle),Filme.LADY_WATER, 3.0));
			add(new Critica((Criticos.LaSalle),Filme.SNAKES_ON_A_PLANE, 4.0));
			add(new Critica((Criticos.LaSalle),Filme.JUST_MY_LUCK, 2.0));
			add(new Critica((Criticos.LaSalle),Filme.SUPERMAN_RETURNS, 3.0));
			add(new Critica((Criticos.LaSalle),Filme.YOU_ME_DUPREE, 2.0));
			add(new Critica((Criticos.LaSalle),Filme.NIGHT_LISTENER, 3.0));

			add(new Critica((Criticos.Matthews),Filme.LADY_WATER, 3.0));
			add(new Critica((Criticos.Matthews),Filme.SNAKES_ON_A_PLANE, 4.0));
			add(new Critica((Criticos.Matthews),Filme.SUPERMAN_RETURNS, 5.0));
			add(new Critica((Criticos.Matthews),Filme.YOU_ME_DUPREE, 3.5));
			add(new Critica((Criticos.Matthews),Filme.NIGHT_LISTENER, 3.0));

			add(new Critica((Criticos.Toby),Filme.SNAKES_ON_A_PLANE, 4.5));
			add(new Critica((Criticos.Toby),Filme.SUPERMAN_RETURNS, 4.0));
			add(new Critica((Criticos.Toby),Filme.YOU_ME_DUPREE, 1.0));
		}};


		return criticas;
	}

}


