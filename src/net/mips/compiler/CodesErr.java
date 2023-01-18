package net.mips.compiler;

public enum CodesErr {
	CAR_INC_ERR("caract�reinconnu"),
	FIC_VIDE_ERR("fichiervide100"),
	
	PROGRAM_ERR("mot clé program attendu!"),
	ID_ERR("identificateur attendu!"),
	PVIR_ERR("Symbole ; attendu !"),
	PNT_ERR("Symbole . attendu !"),
	CONST_ERR("mot clé const attendu!"),
	VAR_ERR("mot clé var attendu !"),
	IF_ERR("mot clé if attendu !"),
	THEN_ERR("mot clé then attendu !"),
	WHILE_ERR("mot clé while attendu !"),
	DO_ERR("mot clé do attendu !"),
	WRITE_ERR("mot clé write attendu !"),
	READ_ERR("mot clé read attendu !"),
	EG_ERR("Symbole == attendu !"),
	BEGIN_ERR("mot clé begin attendu!"),
	AFFEC_ERR("Symbole = attendu!"),
	PARG_ERR("Symbole ( attendu !"),
	PARD_ERR("Symbole ) attendu !"),
	END_ERR("mot clé end attendu!"),
	NUM_ERR("un numéro est attendu !"),
	RELOP_ERR("un des symbole arithmétique attendu !"),
	ADDOP_ERR("opérateur + ou - attendu"),
	MULOP_ERR("opérateur * ou / attendu"),
	EOF_ERR("EOF"),
	FACT_ERR("opérateur = attendu"),
	INST_ERR ("Un mot clé begin, if, while, read ou end, un identificateur, ou le symbole ; sont attendus!"),

	
	DBL_DECL_ERR("Erreur double déclaration"),
	NON_DECL_ERR("Erreur symbole non déclaré"),
	CONST_MODIF_ERR("Erreur constante ne peut changer de valeur"),
	PROG_USE_ERR("Erreur nom de programme non autorisé");

	String msg;

	private CodesErr(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
