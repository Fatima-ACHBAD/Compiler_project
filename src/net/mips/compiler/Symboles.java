package net.mips.compiler;

public class Symboles {

	Tokens token;
	String nom;
	Classe_Idf classe;
	int adresse;
	
	
	
	public int getAdresse() {
		return adresse;
	}

	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}

	public Classe_Idf getClasse() {
		return classe;
	}

	public void setClasse(Classe_Idf classe) {
		this.classe = classe;
	}

	
	public Symboles() {
		super();
	}

	public Symboles(Tokens token, String nom, Classe_Idf classe) {
		super();
		this.token = token;
		this.nom = nom;
		this.classe = classe;
	}

	public Symboles(Tokens token, String nom) {
		super();
		this.token = token;
		this.nom = nom;
	}
	
	public Tokens getToken() {
		return token;
	}
	public void setToken(Tokens token) {
		this.token = token;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
}
