package model;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

/**
 * @author francoise.perrin
 * 
 * Cete classe fabrique et stocke toutes les PieceModel du Model dans une collection 
 * elle est donc responsable de rechercher et mettre � jour les PieceModel (leur position)
 * En r�alit�, elle d�l�gue � une fabrique le soin de fabriquer les bonnes PieceModel 
 * avec les bonnes coordonn�es
 * 
 * En revanche, elle n'est pas responsable des algorithme m�tiers li�s au d�placement des pi�ces
 * (responsabilit� de la classe Model)
 */
public class ModelImplementor {

	// la collection de piéces en jeu - mélange noires et blanches
	private Collection<PieceModel> pieces ;	
	
	public ModelImplementor() {
		super();
		pieces = ModelFactory.createPieceModelCollection();
	}

	public PieceSquareColor getPieceColor(Coord coord) {
		PieceSquareColor color = null;

		PieceModel piece = findPiece(coord);
		if(piece!=null) {
			color=piece.getPieceColor();
		}
		
		return color;
	}

	public boolean isPiecehere(Coord coord) {
		boolean isPiecehere = false;

		if(findPiece(coord)!=null) {
			isPiecehere=true;
		}
		
		return isPiecehere;
	}

	public boolean isMovePieceOk(Coord initCoord, Coord targetCoord, boolean isPieceToTake) {

		boolean isMovePieceOk = false;

		if(Coord.coordonnees_valides(targetCoord)) {
			PieceModel piece = findPiece(initCoord);
			if(piece!=null) {
				if(piece.isMoveOk(targetCoord, isPieceToTake)) {
					isMovePieceOk=true;
				}
			}
		}
		
		return isMovePieceOk;
	}


	public boolean movePiece(Coord initCoord, Coord targetCoord) {
//		boolean isMovePieceDone = false;
//		boolean isPieceToTake = (initCoord.getColonne() == targetCoord.getColonne()+2 || initCoord.getColonne() == targetCoord.getColonne()-2) && (initCoord.getLigne() == targetCoord.getLigne()-2 || initCoord.getLigne() == targetCoord.getLigne()+2);
//		if(isMovePieceOk(initCoord, targetCoord, isPieceToTake)) {
//			findPiece(initCoord).move(targetCoord);
//			isMovePieceDone = true;
//		}
//		return isMovePieceDone;
		findPiece(initCoord).move(targetCoord);
		boolean isMovePieceDone = true;
		
		return isMovePieceDone;
	}

	public void removePiece(Coord pieceToTakeCoord) {		
		this.pieces.remove(findPiece(pieceToTakeCoord));		
		
	}
	
	public void addPiece(PieceModel piece) {
		this.pieces.add(piece);
	}

	
	public List<Coord> getCoordsOnItinerary(Coord initCoord, Coord targetCoord) {
//		List<Coord> coordsOnItinerary = new ArrayList<Coord>();
//		List<Integer> lineList = new ArrayList<Integer>();
//		List<Character> colList = new ArrayList<Character>();
//		if(initCoord.getLigne()<targetCoord.getLigne()) {
//			int line = initCoord.getLigne()+1;
//			while(line<targetCoord.getLigne()) {
//				lineList.add(line);
//				line++;
//			}
//		}else {
//			int line = targetCoord.getLigne()+1;
//			while(line<initCoord.getLigne()) {
//				lineList.add(line);
//				line++;
//			}
//		}
//		
//		if(initCoord.getColonne()<targetCoord.getColonne()) {
//			char col = (char) (initCoord.getColonne()+1);
//			while(col<targetCoord.getColonne()) {
//				colList.add(col);
//				col++;
//			}
//		}else {
//			char col = (char) (targetCoord.getColonne()+1);
//			while(col<initCoord.getColonne()) {
//				colList.add(col);
//				col++;
//			}
//		}
//		
//		for(int i =0; i<lineList.size();i++) {
//			coordsOnItinerary.add(new Coord(colList.get(0),lineList.get(0)));
//		}
		return findPiece(initCoord).getCoordsOnItinerary(targetCoord);
	}

	
	/**
	 * @param coord
	 * @return la pi�ce qui se trouve aux coordonn�es indiqu�es
	 */
	 PieceModel findPiece(Coord coord) {
		 
		 PieceModel pieceResult =null;
			for(PieceModel piece : this.pieces) {
				if(piece.hasThisCoord(coord)) {
					pieceResult = piece;
				}
			}
			return pieceResult;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * La m�thode toStrong() retourne une repr�sentation 
	 * de la liste de pi�ces sous forme d'un tableau 2D
	 * 
	 */
	public String toString() {


		String st = "";
		String[][] damier = new String[ModelConfig.LENGTH][ModelConfig.LENGTH];

//		// cr�ation d'un tableau 2D avec les noms des pi�ces � partir de la liste de pi�ces
//		for(PieceModel piece : this.pieces) {
//
//			PieceSquareColor color = piece.getPieceColor();
//			String stColor = (PieceSquareColor.WHITE.equals(color) ? "--B--" : "--N--" );
//
//			int col = piece.getColonne() -'a';
//			int lig = piece.getLigne() -1;
//			damier[lig][col ] = stColor ;
//		}

		// Affichage du tableau formatt�
		st = "     a      b      c      d      e      f      g      h      i      j\n";
		for ( int lig = 9; lig >=0 ; lig--) {
			st += (lig+1) + "  ";
			for ( int col = 0; col <= 9; col++) {					 
				String stColor = damier[lig][col];				
				if (stColor != null) {						
					st += stColor + "  ";	
				} 
				else {
					st += "-----  ";
				}
			}
			st +="\n";
		}
		
		return "\nDamier du model \n" + st;	
	}


	public PieceSquareColor hasWinColor(){
		boolean isWhitePiece = false;
		boolean isBlackPiece = false;
		PieceSquareColor color = null;

		List<PieceModel> listPiece = new ArrayList<PieceModel>(this.pieces);

		int i = 0;
		while(i < this.pieces.size() && (!isWhitePiece || !isBlackPiece)){
			if(PieceSquareColor.WHITE.equals(listPiece.get(i).getPieceColor())){
				isWhitePiece = true;
			}else{
				isBlackPiece = true;
			}
			i++;
		}

		if(!isWhitePiece){
			color = PieceSquareColor.BLACK;
		}else if(!isBlackPiece){
			color = PieceSquareColor.WHITE;
		}

		return color;
	}

}
