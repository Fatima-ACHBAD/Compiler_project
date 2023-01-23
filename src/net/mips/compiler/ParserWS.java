package net.mips.compiler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import net.mips.interpreter.Instruction;
import net.mips.interpreter.Mnemonique;

public class ParserWS extends Parser {
	public ArrayList<Instruction> pcode;
	PrintWriter fluxCible;

	public ParserWS(String nomfichier) throws IOException, Erreur_Compilation {
		super(nomfichier);
		setScan(new ScannerWS(nomfichier));
		pcode=new ArrayList<Instruction>();
		
}
	
	public ArrayList<Instruction> getPcode() {
		return pcode;
	}

	public void setPcode(ArrayList<Instruction> pcode) {
		this.pcode = pcode;
	}

	public PrintWriter getFluxCible() {
		return fluxCible;
	}

	public void setFluxCible(PrintWriter fluxCible) {
		this.fluxCible = fluxCible;
	}

	public void TESTE_INSERE(Tokens t,Classe_Idf c,CodesErr e) throws IOException, Erreur_Compilation {
		 if(getScan().getSymbCour().getToken()==t)
		 {	 
			 ((ScannerWS)getScan()).CHERCHER_SYMB();
				if (((ScannerWS)getScan()).getPlaces_Symb()!=-1)
					throw new Erreur_Semantique(CodesErr.DBL_DECL_ERR);
				((ScannerWS)getScan()).ENTRER_SYMB(c);
				getScan().symbSuiv();
		 }
		 else
			 throw new Erreur_Syntaxique(e);
		
	}
	public void TESTE_CHERCHE(Tokens t,CodesErr e) throws IOException, Erreur_Compilation {
		 if(getScan().getSymbCour().getToken()==t)
		 {
			 ((ScannerWS) getScan()).CHERCHER_SYMB();
			 int p=((ScannerWS)getScan()).getPlaces_Symb();
				if (p==-1)
					throw new Erreur_Semantique(CodesErr.NON_DECL_ERR);
				Symboles s=((ScannerWS)getScan()).getTable_Symb().get(p);
				if (s.getClasse()==Classe_Idf.PROGRAMMME)
					throw new Erreur_Semantique(CodesErr.PROG_USE_ERR);
				
				getScan().symbSuiv();
		 }
		 else
			 throw new Erreur_Syntaxique(e);
		
	}
	
	public void generer1(Mnemonique M) { 
		Instruction inst=new Instruction();
		inst.setMne(M);
		pcode.add(inst);
	}
	public void generer2(Mnemonique M,int x) {
		pcode.add(new Instruction(M,x));
	}
	//still errors 
	public PrintWriter savePcode() throws FileNotFoundException {
		fluxCible= new PrintWriter(new File("fich.txt"));
		for(Instruction inst:pcode)
		{	Mnemonique m=inst.getMne();
			if(m== Mnemonique.INT || m== Mnemonique.BZE ||m== Mnemonique.BRN || m== Mnemonique.LDI || m== Mnemonique.LDA )
				{
					fluxCible.println(m +"\t" +inst.getSuite());
				}
			else
				{
					 fluxCible.println(m);
				}
			fluxCible.close();
					
		}
		return fluxCible;
		
	}
	
 public void PROGRAM() throws IOException,Erreur_Compilation{
		 
		 Test_Accept(Tokens.PROGRAM_TOKEN,CodesErr.PROGRAM_ERR);
		 TESTE_INSERE(Tokens.ID_TOKEN, Classe_Idf.PROGRAMMME,CodesErr.ID_ERR);
		 Test_Accept(Tokens.PVIR_TOKEN,CodesErr.PVIR_ERR);
		 //TESTE_INSERE(Tokens.PVIR_TOKEN,Classe_Idf.PROGRAMMME,CodesErr.PVIR_ERR);
		 BLOCK();
		 generer1(Mnemonique.HLT);
		 Test_Accept(Tokens.PNT_TOKEN,CodesErr.PNT_ERR);
	 }
 public void BLOCK() throws IOException,Erreur_Compilation {
	  
	  CONSTS();
	  VARS();
	  generer2(Mnemonique.INT,((ScannerWS) scan).getOffset());//a verifier la valeur de l'offset
	  INSTS();  
}

	
	 public void CONSTS() throws IOException,Erreur_Compilation {
		Test_Accept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
		 do {
			 TESTE_INSERE(Tokens.ID_TOKEN,Classe_Idf.CONSTANTE,CodesErr.ID_ERR);
			 	generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());
			 Test_Accept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
			 Test_Accept(Tokens.NUM_TOKEN,CodesErr.NUM_ERR);
			 	generer2(Mnemonique.LDI,((ScannerWS) scan).getVal());
			 	generer1(Mnemonique.STO);
			 Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		 }while(getScan().getSymbCour().getToken()== Tokens.ID_TOKEN);
		 
	 }
	public void VARS() throws IOException,Erreur_Compilation {
		
		Test_Accept(Tokens.VAR_TOKEN,CodesErr.VAR_ERR);
		TESTE_INSERE(Tokens.ID_TOKEN,Classe_Idf.VARIABLE, CodesErr.ID_ERR);
		  generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());
		 
		 while(getScan().getSymbCour().getToken()== Tokens.VIR_TOKEN){
			 getScan().symbSuiv();
			 TESTE_INSERE(Tokens.ID_TOKEN,Classe_Idf.VARIABLE, CodesErr.ID_ERR);
			   generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());
			 //Test_Accept(symbCour.getToken().PVIR_TOKEN, c.PVIR_ERR);
		 }
		 
		Test_Accept(Tokens.PVIR_TOKEN,CodesErr.PVIR_ERR);
	}
	//still
	public void AFFEC() throws IOException,Erreur_Compilation {
		
		TESTE_CHERCHE(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		   generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());

		int p=((ScannerWS)getScan()).getPlaces_Symb();
		Symboles s=((ScannerWS)getScan()).getTable_Symb().get(p);
		if (s.getClasse()==Classe_Idf.CONSTANTE)
			throw new Erreur_Semantique(CodesErr.CONST_MODIF_ERR);
		Test_Accept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
		EXPR();
	 }
	//still
	public void LIRE() throws IOException,Erreur_Compilation {
		
		Test_Accept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
		Test_Accept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
		do {
			
			TESTE_CHERCHE(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			   generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());

			int p=((ScannerWS)getScan()).getPlaces_Symb();
			Symboles s=((ScannerWS)getScan()).getTable_Symb().get(p);
			if (s.getClasse()==Classe_Idf.CONSTANTE)
				throw new Erreur_Semantique(CodesErr.CONST_MODIF_ERR);
		
		} while(getScan().symbCour.getToken()== Tokens.VIR_TOKEN);
		
		Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
			
	}

	
		public void FACT() throws IOException,Erreur_Compilation {
			switch(scan.getSymbCour().getToken()) {
			case ID_TOKEN:
				TESTE_CHERCHE(Tokens.ID_TOKEN, CodesErr.ID_ERR);
				if(scan.getSymbCour().getClasse()== Classe_Idf.CONSTANTE)
					{
					generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());
					generer1(Mnemonique.LDV);
					
					}
				else if(scan.getSymbCour().getClasse()== Classe_Idf.VARIABLE)
						{
						generer2(Mnemonique.LDA,scan.getSymbCour().getAdresse());
						generer1(Mnemonique.LDV);
						}
				break;
			case NUM_TOKEN:
			 	generer2(Mnemonique.LDI,((ScannerWS) scan).getVal());
			 	generer1(Mnemonique.STO);
				scan.symbSuiv();
				break;
			case PARG_TOKEN:
				scan.symbSuiv();
				EXPR();
				 Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.FACT_ERR);
				
		}}
		
			public void TERM() throws IOException,Erreur_Compilation {
				FACT();
				while(getScan().getSymbCour().getToken()== Tokens.MUL_TOKEN ||getScan().getSymbCour().getToken()== Tokens.DIV_TOKEN )
				{
					if(getScan().getSymbCour().getToken()== Tokens.MUL_TOKEN )
						generer1(Mnemonique.MUL);
					else
						generer1(Mnemonique.DIV);
						
				getScan().symbSuiv();
				FACT();
				}
			}
			public void EXPR() throws IOException,Erreur_Compilation{
				TERM();
				while(scan.symbCour.getToken()== Tokens.PLUS_TOKEN ||scan.symbCour.getToken()== Tokens.MOINS_TOKEN )
					{
						if(getScan().getSymbCour().getToken()== Tokens.MUL_TOKEN )
							generer1(Mnemonique.ADD);
						else
							generer1(Mnemonique.SUB);
						scan.symbSuiv();
						TERM();	
					}
			}
			public void ECRIRE() throws IOException,Erreur_Compilation {
				Test_Accept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
				generer1(Mnemonique.PRN);
				Test_Accept(Tokens.PARG_TOKEN, CodesErr.THEN_ERR);
				 EXPR();
				 while(scan.symbCour.getToken()== Tokens.VIR_TOKEN){
					 scan.symbSuiv();
					 EXPR();
				 }
					 Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR); 
			}
			
	 

		public static void main(String [] args) throws IOException,Erreur_Compilation{
			ParserWS parse=new ParserWS("test1.p");
			parse.getScan().initMotscles();
			parse.getScan().lireCar();
			parse.getScan().symbSuiv();
			parse.PROGRAM();
			if (parse.getScan().getSymbCour().getToken()==Tokens.EOF_TOKEN) { 
				System.out.println("Analyse syntaxique reussie");
			}
			else
				throw new Erreur_Syntaxique(CodesErr.EOF_ERR);
		}
}
