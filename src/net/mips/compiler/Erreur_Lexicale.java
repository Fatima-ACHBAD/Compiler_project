package net.mips.compiler;

public class Erreur_Lexicale extends Erreur_Compilation {
	public Erreur_Lexicale(CodesErr erreur) {
		super(erreur.getMsg());
	}
}
