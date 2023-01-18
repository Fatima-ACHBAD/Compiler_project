package net.mips.compiler;
import java.io.IOException;
public class Parser {
	//private static final int EOF = '\0';
	Scanner scan;
	public Scanner getScan() {
		return scan;
	}

	public void setScan(Scanner scan) {
		this.scan = scan;
	}

	public Parser(String nomfichier) throws IOException,Erreur_Compilation {
		scan=new Scanner(nomfichier);
		
	}
	public void Test_Accept(Tokens T,CodesErr C) throws IOException,Erreur_Compilation{
		 if(scan.getSymbCour().getToken()==T)
			 scan.symbSuiv();
		 else
			 throw new Erreur_Syntaxique(C);
		 
	 } 
	 public void PROGRAM() throws IOException,Erreur_Compilation{
		 
		 Test_Accept(Tokens.PROGRAM_TOKEN, CodesErr.PROGRAM_ERR);
		 Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		 Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		 BLOCK();
		 Test_Accept(Tokens.PNT_TOKEN, CodesErr.PNT_ERR);
	 }
	 public void BLOCK() throws IOException,Erreur_Compilation {
			  CONSTS();
			  VARS();
			  INSTS();  
		
	 }
	 //FCT de Madame juste pour tester le fonctionnement du prog 
	/* public void CONSTS() throws IOIOException,Erreur_Compilation {
			switch(scan.getSymbCour().getToken()) {
			case CONST_TOKEN:
				scan.symbSuiv();
				 Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
				 Test_Accept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
				 Test_Accept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
				 Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
				while (scan.symbCour.getToken()== Tokens.ID_TOKEN) {
					scan.symbSuiv();
					 Test_Accept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
					 Test_Accept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
					 Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
				}
				break;
			case VAR_TOKEN:
				break;
			case BEGIN_TOKEN:
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.CONST_ERR);
			}
		}*/
	 public void CONSTS() throws IOException,Erreur_Compilation {
		Test_Accept(Tokens.CONST_TOKEN, CodesErr.CONST_ERR);
		 do {
			 Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			 Test_Accept(Tokens.EG_TOKEN, CodesErr.EG_ERR);
			 Test_Accept(Tokens.NUM_TOKEN, CodesErr.NUM_ERR);
			 Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
		 }while(scan.symbCour.getToken()== Tokens.ID_TOKEN);
		 
	 }
	/* public void VARS() throws IOIOException,Erreur_Compilation {
			switch (scan.getSymbCour().getToken()) {
			case VAR_TOKEN:
				//var ID { , ID } ;
				scan.symbSuiv();
				Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
				while (scan.getSymbCour().getToken()==Tokens.VIR_TOKEN){
					scan.symbSuiv();
					Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
				}
				Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
				break;
			case BEGIN_TOKEN:
				//epsilon
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.VAR_ERR);
			}
		}*/
	public void VARS() throws IOException,Erreur_Compilation {
		Test_Accept(Tokens.VAR_TOKEN, CodesErr.VAR_ERR);
		Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		 
		 while(scan.symbCour.getToken()== Tokens.VIR_TOKEN){
			 //Test_Accept(scan.symbCour.getToken().ID_TOKEN, c.ID_ERR);
			 //Absurde si je teste l'existance de la virgule puisque la boucle le teste 
			 //Donc je passe directement à lire le deuxième caractère par symbsuiv
			 scan.symbSuiv();
			Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			 //Test_Accept(symbCour.getToken().PVIR_TOKEN, c.PVIR_ERR);
		 }
		Test_Accept(Tokens.PVIR_TOKEN, CodesErr.PVIR_ERR);
	}
	public void INSTS() throws IOException,Erreur_Compilation{
		
		 Test_Accept(Tokens.BEGIN_TOKEN, CodesErr.BEGIN_ERR);
		 INST();
		 while(scan.symbCour.getToken()== Tokens.PVIR_TOKEN){
			 scan.symbSuiv();
			INST();
		 }
			Test_Accept(Tokens.END_TOKEN, CodesErr.END_ERR);
			
	}
	public void INST() throws IOException,Erreur_Compilation {
		//CodesErr c = null;
		switch(scan.symbCour.getToken()) {
		case BEGIN_TOKEN:
			INSTS();
			break;
		case ID_TOKEN:
			AFFEC();
			break;
		case IF_TOKEN:
			SI();
			break;
		case WHILE_TOKEN:
			TANTQUE();
			break;
		case WRITE_TOKEN:
			ECRIRE();
			break;
		case READ_TOKEN:
			LIRE();
			break;
		case PVIR_TOKEN:
			break;
		case END_TOKEN:
			break;
		default:
			//throw new Erreur_Lexicale(c.CAR_INC_ERR);
			//throw new Erreur_Lexicale(CodesErr.CAR_INC_ERR);
			throw new Erreur_Syntaxique(CodesErr.INST_ERR);
	}
	}
	
		public void AFFEC() throws IOException,Erreur_Compilation {
			Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
		Test_Accept(Tokens.AFFEC_TOKEN, CodesErr.AFFEC_ERR);
		 EXPR();
		 }

		public void SI() throws IOException,Erreur_Compilation {
			Test_Accept(Tokens.IF_TOKEN, CodesErr.IF_ERR);
			 COND();
			Test_Accept(Tokens.THEN_TOKEN, CodesErr.THEN_ERR);
			 INST();
		}

		public void TANTQUE() throws IOException,Erreur_Compilation {
			Test_Accept(Tokens.WHILE_TOKEN, CodesErr.WHILE_ERR);
			 COND();
			Test_Accept(Tokens.DO_TOKEN, CodesErr.DO_ERR);
			 INST();
		}

		public void ECRIRE() throws IOException,Erreur_Compilation {
			Test_Accept(Tokens.WRITE_TOKEN, CodesErr.WRITE_ERR);
			Test_Accept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
			 EXPR();
			 while(scan.symbCour.getToken()== Tokens.VIR_TOKEN){
				 scan.symbSuiv();
				 EXPR();
			 }
				 Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR); 
		}
		public void LIRE() throws IOException,Erreur_Compilation {
			Test_Accept(Tokens.READ_TOKEN, CodesErr.READ_ERR);
			Test_Accept(Tokens.PARG_TOKEN, CodesErr.PARG_ERR);
			Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			 while(scan.symbCour.getToken()== Tokens.VIR_TOKEN){
				 scan.symbSuiv();
				Test_Accept(Tokens.ID_TOKEN, CodesErr.ID_ERR);
			 }
				Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
				
		}
		public void COND() throws IOException,Erreur_Compilation{
			EXPR();
			RELOP();
			EXPR();
			}
		
		public void RELOP() throws IOException,Erreur_Compilation{
			//CodesErr c;
			switch(scan.symbCour.getToken()) {
			case EG_TOKEN:
				scan.symbSuiv();
				break;
			case DIFF_TOKEN:
				scan.symbSuiv();
				break;
			case INF_TOKEN:
				scan.symbSuiv();
				break;
			case SUP_TOKEN:
				scan.symbSuiv();
				break;
			case INFEG_TOKEN:
				scan.symbSuiv();
				break;
			case SUPEG_TOKEN:
				scan.symbSuiv();
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.RELOP_ERR);
			
		}	
	}
		public void EXPR() throws IOException,Erreur_Compilation{
			TERM();
			while(scan.symbCour.getToken()== Tokens.PLUS_TOKEN ||scan.symbCour.getToken()== Tokens.MOINS_TOKEN )
				{
				scan.symbSuiv();
				TERM();	
				}
		}
		public void TERM() throws IOException,Erreur_Compilation {
			FACT();
			while(scan.symbCour.getToken()== Tokens.MUL_TOKEN ||scan.symbCour.getToken()== Tokens.DIV_TOKEN )
				{
				scan.symbSuiv();
				FACT();
				}
		}
		//*********************************************************************************************************************
/*		public void MULOP() throws Erreur_Compilation, IOException {
			switch(scan.symbCour.getToken()) {
			case MUL_TOKEN:
				scan.symbSuiv();
				break;
			case DIV_TOKEN:
				scan.symbSuiv();
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.MULOP);
		}
		}
		public void ADDOP() throws Erreur_Compilation, IOException {
			switch(scan.symbCour.getToken()) {
			case PLUS_TOKEN:
				scan.symbSuiv();
				break;
			case MOINS_TOKEN:
				scan.symbSuiv();
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.ADDOP);
		}
		}*/
	//	**********************************************************************************
		public void FACT() throws IOException,Erreur_Compilation {
			switch(scan.getSymbCour().getToken()) {
			case ID_TOKEN:
				scan.symbSuiv();
				break;
			case NUM_TOKEN:
				scan.symbSuiv();
				break;
			case PARG_TOKEN:
				scan.symbSuiv();
				EXPR();
				 Test_Accept(Tokens.PARD_TOKEN, CodesErr.PARD_ERR);
				break;
			default:
				throw new Erreur_Syntaxique(CodesErr.FACT_ERR);
				
		}
	 
		}
	
		public static void main(String [] args) throws IOException,Erreur_Compilation {
			Parser PARS=new Parser("tst.p");
			PARS.getScan().initMotscles();
			PARS.getScan().lireCar();
			PARS.getScan().symbSuiv();
			PARS.PROGRAM();
			if(PARS.getScan().getSymbCour().getToken()==Tokens.EOF_TOKEN)
				System.out.println("UHUUUU Analyse syntaxique reussie");
			else
				throw new Erreur_Syntaxique(CodesErr.EOF_ERR);
		
		}
		
		
	}

