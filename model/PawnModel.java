package model;


import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public class PawnModel implements PieceModel{

	private Coord coord;
	private PieceSquareColor pieceColor;

	public PawnModel(Coord coord, PieceSquareColor pieceColor) {
		super();
		this.coord = coord;
		this.pieceColor=pieceColor;

	}

	@Override
	public char getColonne() {
		return this.coord.getColonne();
	}

	@Override
	public int getLigne() {
		return this.coord.getLigne();
	}

	@Override
	public boolean hasThisCoord(Coord coord) {
		return this.coord.equals(coord);
	}

	@Override
	public PieceSquareColor getPieceColor() {
		return this.pieceColor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getPieceColor()+"["+this.getColonne()+","+this.getLigne()+"]";
	}

	@Override
	public void move(Coord coord) {
		this.coord=coord;
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

		int subColonne = targetCoord.getColonne() - this.coord.getColonne();
		
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

	
}

