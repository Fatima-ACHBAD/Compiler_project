package net.mips.interpreter;
import java.util.ArrayList;

public class InterpreterPcode {
	
	public ArrayList<Integer> pile;
	public ArrayList<Integer> mem;
	public ArrayList<Instruction> pcode;
	public int SP;
	public int PC,offset;
	
	public InterpreterPcode() {
		pcode=new ArrayList<Instruction>();
		offset=-1;
		SP=-1;
		PC=-1;
	}
	public void Load1(Mnemonique M) {
		Instruction inst=new Instruction();
		PC++;
		inst.setMne(M);
		pcode.add(inst);
		
	}
	public void Load2(Mnemonique M,int A) {
		PC++;
		pcode.add(new Instruction(M,A));
		
	}
	void INTER_INST(Instruction inst) {
		int val1,adr,val2;
		
		switch(inst.mne) {
		case INT: {
			offset=SP=inst.suite;
			PC++;
			break;
		}
		case LDI: {
			SP++;
			pile.add(inst.suite);
			PC++;
			break;
		}
		case LDA: {//on suppose que lda prend les variable par leur indice exemple pour la variable x on a 0 pour y ona 1 ainsi de suite 
			SP++;
			pile.add(inst.suite);
			PC++;
			break;
		}
		case LDV: {
			adr=pile.get(pile.get(SP));
			pile.set(pile.get(SP), pile.get(adr));
			PC++;
			break;
		}
		case STO: {
			val1=mem.get(mem.get(SP--));
			adr=pile.get(pile.get(SP--));
			pile.set(pile.get(adr), val1);//a voir encore la slt 
			PC++;
			break;
		}
		case ADD: {
			SP--;
			int cpt=mem.get(mem.get(SP))+mem.get(mem.get(SP+1));
			mem.set(mem.get(SP), cpt);
			break;
		}
		case SUB: {
			SP--;
			int cpt=mem.get(mem.get(SP))-mem.get(mem.get(SP+1));
			mem.set(mem.get(SP), cpt);
			break;
		}
		case MUL: {
			SP--;
			int cpt=mem.get(mem.get(SP))*mem.get(mem.get(SP+1));
			mem.set(mem.get(SP), cpt);
			break;
		}
		case DIV: {
			SP--;
			int cpt=mem.get(mem.get(SP))/mem.get(mem.get(SP+1));
			mem.set(mem.get(SP), cpt);
			break;
		}
		case EQL: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1==val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case NEQ: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1!=val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case GTR: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1 > val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case LSS: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1 < val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case GEQ: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1 >= val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case LEQ: {
			val1=pile.get(pile.get(SP--));
			val2=pile.get(pile.get(SP--));
			boolean cptb=(val1 <= val2);
			int cpt;
				if(cptb==true) cpt=1; else cpt=0;
			mem.set(mem.get(++SP), cpt);
			break;
		}
		case BZE:{
			if(mem.get(SP--)==0)
				PC=inst.suite;
			else 
				PC++;
			break;
		}
		case BRN:{
				PC=inst.suite; 
			break;
		}
		case PRN:{
			System.out.println(pile.get(pile.get(SP--)));
		break;
	}
		case INN:{
			//a voir encore 
		break;
	}
		case HLT:
			break;
	
		default:
			{
				System.out.println("Linstruction n'existe pas ");
			break;
			}
		}
		
		
	}
	
	void INTER_CODE() {
		PC=0;
		Instruction inst = null;
		while(pcode.get(PC).mne != inst.mne)
			INTER_INST(pcode.get(PC));
	}
	
}
