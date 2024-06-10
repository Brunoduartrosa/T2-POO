package dados;

import java.util.ArrayList;

public class Ludoteca implements Iterador {

	private int contador;

	private ArrayList<Jogo> jogos;

	public Ludoteca() {
		jogos = new ArrayList<>();
		contador = 0;
	}

	public boolean addJogo(Jogo jogo) {
		String nome = jogo.getNome();
		if(consultaPorNome(nome) == null){
			return jogos.add(jogo);
		}
		return false;
	}

	public Jogo consultaPorNome(String nome) {
		for(Jogo aux: jogos){
			if(aux.getNome().equals(nome)){
				return aux;
			}
		}
		return null;
	}

	public ArrayList<Jogo> consultaPorAno(int ano) {
		ArrayList<Jogo> jogo = new ArrayList<>();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				if(aux.getAno() == ano){
					jogo.add(aux);
				}
			}
		}
		return jogo;
	}

	public ArrayList<Jogo> getJogos() {
		return jogos;
	}

	public ArrayList<Jogo> consultaPorCategoria(Categoria categoria){
		ArrayList<Jogo> jogoPorCategoria = new ArrayList<>();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				if(aux instanceof JogoEletronico) {
					JogoEletronico jogoEletronico = (JogoEletronico) aux;
					if(jogoEletronico.getCategoria() == categoria) {
						jogoPorCategoria.add(jogoEletronico);
					}
				}
			}
		}
		return jogoPorCategoria;
	}

	/**
	 * @see dados.Iterador#reset()
	 */
	@Override
	public void reset() {
		contador = 0;
	}

	/**
	 * @see dados.Iterador#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if(contador<jogos.size()-1){
			return true;
		}
		return false;
	}

	/**
	 * @see dados.Iterador#next()
	 */
	@Override
	public Object next() {
		if(!hasNext()){
			return null;
		}
		contador++;
		return jogos.get(contador);
	}
}
