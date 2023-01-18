package net.mips.compiler;

public class Erreur_Semantique extends Erreur_Compilation {
	public Erreur_Semantique(CodesErr erreur) {
		super(erreur.getMsg());
		
	}	
}
