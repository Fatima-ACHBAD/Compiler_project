package net.mips.compiler;
import java.io.FileReader;
import java.io.IOException;
import  java.io.File;
import java.util.ArrayList;
//import java.util.List;
//import java.util.List;

//import net.mips.compiler.Erreur_Compilation;
//import net.mips.compiler.Erreur_Lexicale;
public class Scanner {
	
public ArrayList<Symboles> motsCles;
public Symboles symbCour;
public char carCour;
public FileReader fluxSour;
public final static  int EOF='\0';


public ArrayList<Symboles> getMotsCles() {
	return motsCles;
}
public void setMotsCles(ArrayList<Symboles> motsCles) {
	this.motsCles = motsCles;
}
public Symboles getSymbCour() {
	return symbCour;
}
public void setSymnCour(Symboles symbCour) {
	this.symbCour = symbCour;
}
public FileReader getFluxSour() {
	return fluxSour;
}
public char getCarCour() {
	return carCour;
}
public void setCarCour(char carCour) {
	this.carCour = carCour;
}
public void setFluxSour(FileReader fluxSour) {
	this.fluxSour = fluxSour;
}

public Scanner(String nomfichier) throws IOException,Erreur_Compilation {
	
	File fichier= new File(nomfichier);
	
	if(!fichier.exists())
		throw new Erreur_Lexicale(CodesErr.FIC_VIDE_ERR);
	else 
		{
		fluxSour=new FileReader(fichier);
		
		motsCles=new ArrayList<Symboles>();
		}
}


public  void initMotscles() {
	motsCles.add(new Symboles(Tokens.BEGIN_TOKEN,"begin"));
	motsCles.add(new Symboles(Tokens.END_TOKEN,"End"));
	motsCles.add(new Symboles(Tokens.IF_TOKEN,"SI"));
	motsCles.add(new Symboles(Tokens.WHILE_TOKEN,"TANT QUE"));
	motsCles.add(new Symboles(Tokens.THEN_TOKEN,"DONC"));
	motsCles.add(new Symboles(Tokens.DO_TOKEN,"FAIRE"));
	motsCles.add(new Symboles(Tokens.WRITE_TOKEN,"ECRIRE"));
	motsCles.add(new Symboles(Tokens.READ_TOKEN,"LIRE"));
	motsCles.add(new Symboles(Tokens.PROGRAM_TOKEN,"PROGRAMME"));
	motsCles.add(new Symboles(Tokens.CONST_TOKEN,"CONSTANTE"));
	motsCles.add(new Symboles(Tokens.VAR_TOKEN,"VAR"));
	//motsCles.add(new Symboles(Tokens.ERR_TOKEN,"ERREUR"));
}

//procédure codage_lex
public  void codageLex() {

	String nom1=symbCour.getNom();
	
for( Symboles s:motsCles)
{   String nom2=s.getNom();
	if(nom1.equalsIgnoreCase(nom2))
	{	
		symbCour.setToken(s.getToken());
	return;
	}
}
	symbCour.setToken(Tokens.ID_TOKEN);
	
}


public void lireCar() throws IOException {
	if (fluxSour.ready())
		carCour=(char) fluxSour.read();
	else
		carCour=EOF;
}

 	public void lireMot() throws IOException {
		symbCour.setNom(symbCour.getNom()+carCour);
		lireCar();
		while(Character.isLetterOrDigit(carCour)) {
			symbCour.setNom(symbCour.getNom()+carCour);
			lireCar();
		}
		codageLex();
	}
	
	public void lireNombre() throws IOException {
		symbCour.setNom(symbCour.getNom()+carCour);
		lireCar();
		while(Character.isDigit(carCour)) {
			symbCour.setNom(symbCour.getNom()+carCour);
			lireCar();
		}
		symbCour.setToken(Tokens.NUM_TOKEN);
	}
	
public void symbSuiv() throws IOException, Erreur_Compilation{
	 	symbCour=new Symboles();
		while(Character.isWhitespace(carCour))
			lireCar();
		if (Character.isLetter(carCour)) {
			lireMot();
			return;
		}
		if(Character.isDigit(carCour)) {
			lireNombre();
			return;
		}
		switch(carCour) {
		case '+':
			symbCour.setToken(Tokens.PLUS_TOKEN);
			lireCar();
			break;
		case '-':
			symbCour.setToken(Tokens.MOINS_TOKEN);
			lireCar();
			break;
		case '*':
			symbCour.setToken(Tokens.MUL_TOKEN);
			lireCar();
			break;
		case '/':
			symbCour.setToken(Tokens.DIV_TOKEN);
			lireCar();
			break;
		case '(':
			symbCour.setToken(Tokens.PARG_TOKEN);
			lireCar();
			break;
		case ')':
			symbCour.setToken(Tokens.PARD_TOKEN);
			lireCar();
			break;
		case '.':
			symbCour.setToken(Tokens.PNT_TOKEN);
			lireCar();
			break;
		case ',':
			symbCour.setToken(Tokens.VIR_TOKEN);
			lireCar();
			break;
		case ';':
			symbCour.setToken(Tokens.PVIR_TOKEN);
			lireCar();
			break;
		case '=':
			symbCour.setToken(Tokens.EG_TOKEN);
			lireCar();
			break;
		case EOF:
			symbCour.setToken(Tokens.EOF_TOKEN);
			break;
		case ':':
			lireCar();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.AFFEC_TOKEN);
				lireCar();
				break;
			default:
				symbCour.setToken(Tokens.ERR_TOKEN);
				throw new Erreur_Lexicale(CodesErr.CAR_INC_ERR);
			}
			break;
		case '>':
			lireCar();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.SUPEG_TOKEN);
				lireCar();
				break;
			default:
				symbCour.setToken(Tokens.SUP_TOKEN);
				break;
			}
			break;
		case '<':
			lireCar();
			switch(carCour) {
			case '=':
				symbCour.setToken(Tokens.INFEG_TOKEN);
				lireCar();
				break;
			case '>':
				symbCour.setToken(Tokens.DIFF_TOKEN);
				lireCar();
				break;
			default:
				symbCour.setToken(Tokens.INF_TOKEN);
				break;
			}
			break;
		default:
			throw new Erreur_Lexicale(CodesErr.CAR_INC_ERR);
		}
		
			
	}
 
	public static void main(String [] args) throws IOException, Erreur_Compilation {
		Scanner scanner=new Scanner("test1.p");
		scanner.initMotscles();
		scanner.lireCar();
		//System.out.println(scanner.getSymbCour());
		while(scanner.getCarCour()!=EOF) {
			scanner.symbSuiv();
			System.out.println(scanner.getSymbCour().getToken());
		}
		//System.out.println(scanner.getSymbCour()+"***");
	}
	
	
}

 


