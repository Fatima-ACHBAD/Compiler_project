package net.mips.compiler;

public class Erreur_Syntaxique extends Erreur_Compilation{
	public Erreur_Syntaxique(CodesErr erreur) {
		super(erreur.getMsg());
		
	}
	
}
