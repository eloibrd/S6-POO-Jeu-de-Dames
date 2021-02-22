package model;


import java.util.Comparator;
import java.util.List;

import nutsAndBolts.PieceSquareColor;

public interface PieceModel extends Comparable<PieceModel>{
	
	
	/**
	 * @return the coord
	 */
	public char getColonne() ;
	public int getLigne() ;
	
	/**
	 * @param coord
	 * @return true si la piéce est aux coordonnées passées en paramétre
	 */
	public boolean hasThisCoord(Coord coord);
	
	/**
	 * @param coord the coord to set
	 * le d�placement d'une pi�ce change ses coordonn�es
	 */
	public void move(Coord coord);


	/**
	 * @return the pieceColor
	 */
	public PieceSquareColor getPieceColor() ;
	
	
	/**
	 * @param targetCoord
	 * @param isPieceToCapture
	 * @return true si le d�placement est l�gal
	 */
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture);

	/**
	 * @param targetCoord
	 * @return liste des coordonn�es des cases travers�es par itin�raire de d�placement
	 */
	public List<Coord> getCoordsOnItinerary(Coord targetCoord);

	@Override
	public int compareTo(PieceModel p1);
}

