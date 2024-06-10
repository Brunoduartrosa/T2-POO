package dados;

public class JogoEletronico extends Jogo {

	private String plataforma;

	private Categoria categoria;

	public JogoEletronico(String nome, int ano, double precoBase, String plataforma, Categoria categoria) {
		super(nome, ano, precoBase);
		this.plataforma = plataforma;
		this.categoria = categoria;
	}

	public String getPlataforma() {
		return plataforma;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	@Override
	public double calculaPrecoFinal() {
		double precoFinal = 0;
		double precoBase = getPrecoBase();
		if(categoria == Categoria.ACT){
			precoFinal = precoBase + precoBase * 10/100;
		} else if (categoria == Categoria.SIM) {
			precoFinal = precoBase + precoBase * 30/100;
		} else if (categoria == Categoria.STR) {
			precoFinal = precoBase + precoBase * 70/100;
		}
		return precoFinal;
	}
}
