package model;


import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public class PawnModel extends AbstractPieceModel implements Promotable{

	
	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super(coord,pieceColor);
	}

	@Override
	public int compareTo(PieceModel p1){
		if(p1.getLigne() < this.getLigne()){
			return 1;
		}else if(p1.getLigne() == this.getLigne()){
			return 0;
		}else{
			return -1;
		}
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
					if((targetCoord.getColonne() == this.getColonne()+2 || targetCoord.getColonne() == this.getColonne()-2) && targetCoord.getLigne() == this.getLigne()-2) {
						return true;
					}else {
						return false;
					}
				}else {
					if((targetCoord.getColonne() == this.getColonne()+2 || targetCoord.getColonne() == this.getColonne()-2) && targetCoord.getLigne() == this.getLigne()+2) {
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

		int subColonne = targetCoord.getColonne() - super.getColonne();
		
		if(this.getPieceColor()== PieceSquareColor.BLACK) {
			if(subColonne>0) {
				coordsOnItinery.add(new Coord((char)(this.getColonne()+1),this.getLigne()-1));
			}else{
				coordsOnItinery.add(new Coord((char)(this.getColonne()-1),this.getLigne()-1));
			}
		}else {
			if(subColonne>0) {
				coordsOnItinery.add(new Coord((char)(this.getColonne()+1),this.getLigne()+1));
			}else{
				coordsOnItinery.add(new Coord((char)(this.getColonne()-1),this.getLigne()+1));
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

