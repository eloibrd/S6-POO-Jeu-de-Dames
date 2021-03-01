package model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public class PawnModel extends AbstractPieceModel implements Promotable{

	
	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super(coord,pieceColor);
	}	

	@Override
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		boolean ret = false;
		if(Coord.coordonnees_valides(targetCoord)){
			if(!isPieceToCapture) {
				if(this.getPieceColor() == PieceSquareColor.BLACK ) {
					if((targetCoord.getColonne() == this.getColonne()+1 || targetCoord.getColonne() == this.getColonne()-1) && targetCoord.getLigne() == this.getLigne()-1) {
						return true;
					}else {
						return false;
					}
				}else {
					if((targetCoord.getColonne() == this.getColonne()+1 || targetCoord.getColonne() == this.getColonne()-1) && targetCoord.getLigne() == this.getLigne()+1) {
						return true;
					}else {
						return false;
					}
				}
			}else {
				if(this.getPieceColor() == PieceSquareColor.BLACK ) {
					if((targetCoord.getColonne() == this.getColonne()+2 || targetCoord.getColonne() == this.getColonne()-2) && (targetCoord.getLigne() == this.getLigne()-2 || targetCoord.getLigne() == this.getLigne()+2)) {
						return true;
					}else {
						return false;
					}
				}else {
					if((targetCoord.getColonne() == this.getColonne()+2 || targetCoord.getColonne() == this.getColonne()-2) && (targetCoord.getLigne() == this.getLigne()+2 || targetCoord.getLigne() == this.getLigne()-2)) {
						return true;
					}else {
						return false;
					}
				}
			}
			
		}
		return ret;
	}

	@Override
	public List<Coord> getCoordsOnItinerary(Coord targetCoord) {

		List<Coord> coordsOnItinery = new LinkedList<Coord>(); 
		int initCol = this.getColonne();
		int initLig = this.getLigne();
		int colDistance = targetCoord.getColonne() - this.getColonne();
		int ligDistance = targetCoord.getLigne() - this.getLigne();
		int deltaLig = (int) Math.signum(ligDistance);
		int deltaCol = (int) Math.signum(colDistance);

		// Vérif déplacement en diagonale
		if (Math.abs(colDistance) == Math.abs(ligDistance)){

			// recherche coordonnées des cases traversées
			for (int i = 1; i < Math.abs(colDistance); i++) {
				Coord coord = new Coord((char) (initCol + i*deltaCol), initLig + i*deltaLig);
				coordsOnItinery.add(coord);
			}
		}
		return coordsOnItinery;
	}

	@Override
	public boolean isPromotable() {
		if(this.getPieceColor() == PieceSquareColor.WHITE && this.getLigne()==10) {
			return true;
		}else if(this.getPieceColor() == PieceSquareColor.BLACK && this.getLigne()==1) {
			return true;
		}else {
			return false;
		}
		//return (this.getPieceColor() == PieceSquareColor.WHITE && this.getLigne()==10) || (this.getPieceColor() == PieceSquareColor.BLACK && this.getLigne()==1);
	}

	@Override
	public void promote() {
		throw new UnsupportedOperationException();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getPieceColor()+"["+this.getColonne()+","+this.getLigne()+"]";
	}

	
}

