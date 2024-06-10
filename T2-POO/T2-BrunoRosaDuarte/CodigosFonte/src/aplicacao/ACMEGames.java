package aplicacao;

import dados.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class ACMEGames {

	private Ludoteca ludoteca;
	private Scanner entrada;
	private PrintStream saidaPadrao = System.out;

	public ACMEGames() {
		try {
			BufferedReader streamEntrada = new BufferedReader(new FileReader("dadosin.txt"));
			entrada = new Scanner(streamEntrada);
			entrada.useDelimiter("[;\n\r]");
			PrintStream streamSaida = new PrintStream(new File("dadosout.txt"), StandardCharsets.UTF_8);
			System.setOut(streamSaida);
		} catch (Exception e) {
			System.out.println(e);
		}
		Locale.setDefault(Locale.ENGLISH);
		entrada.useLocale(Locale.ENGLISH);
		ludoteca = new Ludoteca();
	}

	public void executa() {
		cadastraJogoEletronico();
		cadastraJogoTabuleiro();
		mostraDadosJogoPorNome();
		mostraDadosJogoPorAno();
		mostraDadosJogoEletronicoPorCategoria();
		mostraSomatorioPrecoFinal();
		mostraDadosJogoTabuleiroMaiorPreco();
		mostraDadosJogoProximoMediaPreco();
		mostraDadosJogoTabuleiroMaisAntigo();
	}



	public static Categoria getByName(String nome){
		if(nome.equalsIgnoreCase("Acao")) {
			return Categoria.ACT;
		} else if (nome.equalsIgnoreCase("Estrategia")) {
			return Categoria.STR;
		}else if(nome.equalsIgnoreCase("Simulacao")){
			return Categoria.SIM;
		}
		return null;
	}

	//método 1
	private void cadastraJogoEletronico() {
		String nome;
		int ano;
		double precoBase;
		String plataforma;
		String cat;
		Categoria categoria;
		JogoEletronico jogo = null;
		nome = entrada.next();
		while(!nome.equals("-1")){
			ano = entrada.nextInt();
			precoBase = entrada.nextDouble();
			plataforma = entrada.next();
			cat = entrada.next();
			entrada.nextLine();
			categoria = getByName(cat);
			jogo = new JogoEletronico(nome, ano, precoBase, plataforma, categoria);
			if(ludoteca.addJogo(jogo)){
				System.out.printf("1:%s,R$ %.2f\n", jogo.getNome(), jogo.calculaPrecoFinal());
			}else{
				System.out.println("1:Erro-jogo com nome repetido: "+ nome);
			}
			nome = entrada.next();
		}
	}

	//método 2
	private void cadastraJogoTabuleiro() {
		String nome;
		int ano;
		double precoBase;
		int numeroPecas;
		JogoTabuleiro jogo = null;
		nome = entrada.next();
		while(!nome.equals("-1")){
			ano = entrada.nextInt();
			precoBase = entrada.nextDouble();
			numeroPecas = entrada.nextInt();
			entrada.nextLine();
			jogo = new JogoTabuleiro(nome, ano, precoBase, numeroPecas);
			if(ludoteca.addJogo(jogo)){
				System.out.printf("2:%s,R$ %.2f\n", nome, jogo.calculaPrecoFinal());
			}else{
				System.out.println("2:Erro-jogo com nome repetido: "+ nome);
			}
			nome = entrada.next();
		}
		entrada.nextLine();
	}

	//método 3
	private void mostraDadosJogoPorNome() {
		String nome;
		Jogo jogo;
		nome = entrada.nextLine();
		jogo = ludoteca.consultaPorNome(nome);
		if(jogo != null){
			if(jogo instanceof JogoEletronico) {
				JogoEletronico jogoEle = (JogoEletronico) jogo;
				System.out.printf("3:%s,%d,R$ %.2f,%s,%s,R$ %.2f\n", nome, jogoEle.getAno(), jogoEle.getPrecoBase(), jogoEle.getPlataforma(), jogoEle.getCategoria().getNome(), jogoEle.calculaPrecoFinal());
			}else if(jogo instanceof JogoTabuleiro){
				JogoTabuleiro jogoTab = (JogoTabuleiro) jogo;
				System.out.printf("3:%s,%d,R$ %.2f,%d,R$ %.2f\n", nome, jogoTab.getAno(), jogoTab.getPrecoBase(), jogoTab.getNumeroPecas(), jogoTab.calculaPrecoFinal());
			}
		}else {
			System.out.println("3:Nome inexistente.");
		}
	}

	//método 4
	private void mostraDadosJogoPorAno() {
		int ano;
		ArrayList<Jogo> jogos;
		ano = entrada.nextInt();
		jogos = ludoteca.consultaPorAno(ano);
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos) {
				if(aux instanceof JogoEletronico) {
					JogoEletronico jogoEle = (JogoEletronico) aux;
					System.out.printf("4:%s,%d,R$ %.2f,%s,%s,R$ %.2f\n", jogoEle.getNome(), ano, jogoEle.getPrecoBase(), jogoEle.getPlataforma(), jogoEle.getCategoria().getNome(), jogoEle.calculaPrecoFinal());
				}else if(aux instanceof JogoTabuleiro){
					JogoTabuleiro jogoTab = (JogoTabuleiro) aux;
					System.out.printf("4:%s,%d,R$ %.2f,%d,R$ %.2f\n", jogoTab.getNome(), ano, jogoTab.getPrecoBase(), jogoTab.getNumeroPecas(), jogoTab.calculaPrecoFinal());
				}
			}
		}else{
			System.out.println("4:Nenhum jogo encontrado.");
		}
	}

	//método 5
	private void mostraDadosJogoEletronicoPorCategoria() {
		String cat = entrada.next();
		Categoria categoria = getByName(cat);
		ArrayList<Jogo> jogos;
		jogos = ludoteca.consultaPorCategoria(categoria);
		if(categoria != null){
			for(Jogo aux: jogos){
				JogoEletronico jogoEle = (JogoEletronico) aux;
				System.out.printf("5:%s,%d,R$ %.2f,%s,%s,R$ %.2f\n", jogoEle.getNome(), jogoEle.getAno(), jogoEle.getPrecoBase(), jogoEle.getPlataforma(), jogoEle.getCategoria().getNome(), jogoEle.calculaPrecoFinal());
			}
		}else{
			System.out.println("5:Categoria inexistente.");
		}
		if(jogos.isEmpty()){
			System.out.println("5:Nenhum jogo encontrado.");
		}
	}

	//método 6
	private void mostraSomatorioPrecoFinal() {
		double sum = 0;
		ArrayList<Jogo> jogos;
		jogos = ludoteca.getJogos();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				sum += aux.calculaPrecoFinal();
			}
			System.out.printf("6:R$ %.2f\n",sum);
		}else{
			System.out.println("6:Nenhum jogo encontrado.");
		}
	}

	//método 7
	private void mostraDadosJogoTabuleiroMaiorPreco() {
		double precoFinal = 0;
		JogoTabuleiro jogo = null;
		ArrayList<Jogo> jogos;
		jogos = ludoteca.getJogos();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				if(aux instanceof JogoTabuleiro) {
					if (precoFinal < aux.calculaPrecoFinal()) {
						jogo = (JogoTabuleiro) aux;
					}
				}
			}
			try {
				System.out.printf("7:%s,R$ %.2f\n", jogo.getNome(), jogo.calculaPrecoFinal());
			}catch(NullPointerException e){
				System.out.println("7:Nenhum jogo encontrado.");
			}
		}else{
			System.out.println("7:Nenhum jogo encontrado.");
		}
	}

	//método 8
	private void mostraDadosJogoProximoMediaPreco() {
		double sum = 0;
		double media = 0;
		double diferenca = 2000000;
		Jogo jogo = null;
		JogoEletronico jogoEletronico = null;
		JogoTabuleiro jogoTabuleiro = null;
		ArrayList<Jogo> jogos;
		jogos = ludoteca.getJogos();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				sum += aux.getPrecoBase();
			}
			media = sum/jogos.size();
			for(Jogo aux1: jogos){
				double temp = Math.abs((media-aux1.getPrecoBase()));
				if(diferenca>temp){
					diferenca = temp;
					jogo = aux1;
				}
			}
			if(jogo instanceof JogoEletronico){
				jogoEletronico = (JogoEletronico) jogo;
				System.out.printf("8:R$ %.2f,%s,%d,R$ %.2f,%s,%s,R$ %.2f\n", media, jogoEletronico.getNome(), jogoEletronico.getAno(), jogoEletronico.getPrecoBase(), jogoEletronico.getPlataforma(), jogoEletronico.getCategoria().getNome(), jogoEletronico.calculaPrecoFinal());
			}else if(jogo instanceof JogoTabuleiro){
				jogoTabuleiro = (JogoTabuleiro) jogo;
				System.out.printf("8:R$ %.2f,%s,%d,R$ %.2f,%d,R$ %.2f\n", media, jogoTabuleiro.getNome(), jogoTabuleiro.getAno(), jogoTabuleiro.getPrecoBase(), jogoTabuleiro.getNumeroPecas(), jogoTabuleiro.calculaPrecoFinal());
			}
		}else{
			System.out.println("8:Nenhum jogo encontrado.");
		}
	}

	//método 9
	private void mostraDadosJogoTabuleiroMaisAntigo() {
		int ano = 2023;
		JogoTabuleiro jogo = null;
		ArrayList<Jogo> jogos;
		jogos = ludoteca.getJogos();
		if(!jogos.isEmpty()){
			for(Jogo aux: jogos){
				if(aux instanceof JogoTabuleiro){
					if(ano>aux.getAno()){
						jogo = (JogoTabuleiro) aux;
					}
				}
			}
			try {
				System.out.printf("9:%s,%d\n", jogo.getNome(), jogo.getAno());
			}catch (NullPointerException e){
				System.out.println("9:Nenhum jogo encontrado.");
			}
		}else{
			System.out.println("9:Nenhum jogo encontrado.");
		}
	}
}
