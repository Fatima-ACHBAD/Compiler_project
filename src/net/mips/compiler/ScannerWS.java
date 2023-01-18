package net.mips.compiler;
import java.io.IOException;
import java.util.ArrayList;

public class ScannerWS extends Scanner{
	
	
	private ArrayList<Symboles> Table_Symb;
	private int Places_Symb;
	int offset;
	private int val;
	
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	
	public ArrayList<Symboles> getTable_Symb() {
		return Table_Symb;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setTable_Symb(ArrayList<Symboles> table_Symb) {
		this.Table_Symb = table_Symb;
	}
	public int getPlaces_Symb() {
		return Places_Symb;
	}
	public void setPlaces_Symb(int places_Symb) {
		Places_Symb = places_Symb;
	}

		public ScannerWS(String nomFich) throws IOException, Erreur_Compilation {
			super(nomFich);
			Table_Symb=new ArrayList<Symboles>();
			offset=-1;
		}
		
	public  void initMotscles() {
		Table_Symb.add(new Symboles(Tokens.BEGIN_TOKEN,"begin"));
		Table_Symb.add(new Symboles(Tokens.END_TOKEN,"End"));
		Table_Symb.add(new Symboles(Tokens.IF_TOKEN,"SI"));
		Table_Symb.add(new Symboles(Tokens.WHILE_TOKEN,"TANT QUE"));
		Table_Symb.add(new Symboles(Tokens.THEN_TOKEN,"DONC"));
		Table_Symb.add(new Symboles(Tokens.DO_TOKEN,"FAIRE"));
		Table_Symb.add(new Symboles(Tokens.WRITE_TOKEN,"ECRIRE"));
		Table_Symb.add(new Symboles(Tokens.READ_TOKEN,"LIRE"));
		Table_Symb.add(new Symboles(Tokens.PROGRAM_TOKEN,"PROGRAMME"));
		Table_Symb.add(new Symboles(Tokens.CONST_TOKEN,"CONSTANTE"));
		Table_Symb.add(new Symboles(Tokens.VAR_TOKEN,"VAR"));
}
	public  void codageLex() {

		String nom1=symbCour.getNom();
		
	for( Symboles s:Table_Symb)
	{   String nom2=s.getNom();
		if(nom1.equalsIgnoreCase(nom2))
		{	
			getSymbCour().setToken(s.getToken());
			symbCour.setClasse(s.getClasse());
		return;
		}
	}
		getSymbCour().setToken(Tokens.ID_TOKEN);
		symbCour.setClasse(Classe_Idf.VARIABLE);
		
	}
	public void lireNombre() throws IOException {
		super.lireNombre();
		val=Integer.parseInt(getSymbCour().getNom());
	}
	//voir vec prof 
	public void ENTRER_SYMB(Classe_Idf c) {
		
		Symboles symbo=new Symboles();//dernier_symb=dernier_symb+1
		
		symbo.setToken(getSymbCour().getToken());
		symbo.setNom(getSymbCour().getNom());
		symbo.setClasse(c);
		if (c==Classe_Idf.CONSTANTE || c==Classe_Idf.VARIABLE) {
				offset++;//reservation mémoire virtuelle 
				Table_Symb.add(symbo);
				Table_Symb.get(getPlaces_Symb()).setAdresse(offset);
		}
	}
	//gerer la regle :po de doublant 
	public void CHERCHER_SYMB() {
		String nom1=getSymbCour().getNom();
		for(int i=0;i<Table_Symb.size();i++) {
			String nom2=Table_Symb.get(i).getNom();
		if(nom2.equalsIgnoreCase(nom1));
			{
				setPlaces_Symb(i);
				return;
			}
			}
		setPlaces_Symb(-1);
		}
	
	
	
}
