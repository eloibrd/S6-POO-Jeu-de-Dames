package model;

import java.util.Comparator;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public abstract class AbstractPieceModel implements PieceModel {

	private Coord coord;
	private PieceSquareColor pieceColor;
	
	public AbstractPieceModel(Coord coord, PieceSquareColor pieceColor) {
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
	public void move(Coord coord) {
		this.coord=coord;
		
	}

	@Override
	public PieceSquareColor getPieceColor() {
		return this.pieceColor;
	}

	@Override
	public abstract boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture);

	@Override
	public abstract List<Coord> getCoordsOnItinerary(Coord targetCoord);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return " ["+getPieceColor().toString().charAt(0) + this.coord  + "]";
	}
}
