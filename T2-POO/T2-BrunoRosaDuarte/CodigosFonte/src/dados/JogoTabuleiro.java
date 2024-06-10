package dados;

public class  JogoTabuleiro extends Jogo {

	private int numeroPecas;

	public JogoTabuleiro(String nome, int ano, double precoBase, int numeroPecas) {
		super(nome, ano, precoBase);
		this.numeroPecas = numeroPecas;
	}

	public int getNumeroPecas() {
		return numeroPecas;
	}

	@Override
	public double calculaPrecoFinal() {
		double precoFinal;
		precoFinal = getPrecoBase() + getPrecoBase() * (numeroPecas* 0.01);
		return precoFinal;
	}
}
