package model;



/**
 * @author francoiseperrin
 *
 * Coordonn�es des PieceModel
 */
public class Coord implements Comparable<Coord>{
	
	private char colonne; 	// ['a'..'j']
	private int ligne;		// [10..1]
	static final int MAX = ModelConfig.LENGTH;	// 10

	public Coord(char colonne, int ligne) {
		super();
		this.colonne = colonne;
		this.ligne = ligne;
	}

	public char getColonne() {
		return colonne;
	}

	public int getLigne() {
		return ligne;
	}


	@Override
	public String toString() {
		return "["+ligne + "," + colonne + "]";
	}


	/**
	 * @param coord
	 * @return true si 'a' <= col < 'a'+MAX et 1 < lig <= MAX
	 */
	public static boolean coordonnees_valides(Coord coord){
		boolean ret = false;
		if('a' <= coord.getColonne()  && coord.getColonne()<'a'+MAX && 1 <= coord.getLigne() && coord.getLigne() <=MAX ) {
			ret = true;
		}
		
		return ret;
	}


	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * La méthode compareTo() indique comment comparer un objet � l'objet courant
	 * selon l'ordre dit naturel
	 * Dans cet application, nous décidons que l'ordre naturel est celui 
	 * correspondant au N° de la case d'un tableau 2D représenté par la Coord
	 * ainsi le N° 1 correspond à la Coord ['a', 10], le N° 100 correspond à la Coord ['j', 1]  
	 */
	@Override
	public int compareTo(Coord o) {
		
		if(this.getColonne()==o.getColonne() && this.getLigne()==o.getLigne()) {
			return 0;
		}else if(this.getColonne()>o.getColonne()) {
			return 1;
		}else if(this.getColonne()==o.getColonne() && this.getLigne()>o.getLigne()) {
			return 1;
		}else {
			return -1;
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Coord) {
			return compareTo((Coord)o)==0;
		}else {
			return false;
		}
	}

}
