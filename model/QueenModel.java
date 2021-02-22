package model;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import nutsAndBolts.PieceSquareColor;
/**
 * @author francoiseperrin
 *
 *le mode de déplacement et de prise de la reine est différent de celui du pion
 */
public class QueenModel extends AbstractPieceModel {

	public QueenModel(Coord coord, PieceSquareColor pieceColor) {
		super(coord,pieceColor);
	}
	
	@Override
	public List<Coord> getCoordsOnItinerary(Coord targetCoord) {
		List<Coord> coordsOnItinerary = new ArrayList<Coord>();
		int initLine = this.getLigne();
		char initCol = this.getColonne();
		if(this.getLigne()>targetCoord.getLigne()) {
			//System.out.println("InitLine>TargetLine");
			if(this.getColonne()>targetCoord.getColonne()) {
				while(initLine>targetCoord.getLigne()) {
					initLine = initLine-1;
					initCol = (char)(initCol-1);
					coordsOnItinerary.add(new Coord(initCol,initLine));
				}
			}else {
				while(initLine>targetCoord.getLigne()) {
					initLine = initLine-1;
					initCol = (char)(initCol+1);
					coordsOnItinerary.add(new Coord(initCol,initLine));
				}
			}
		}else {
			//System.out.println("InitLine<TargetLine");
			if(this.getColonne()>targetCoord.getColonne()) {
				while(initLine<targetCoord.getLigne()) {
					initLine = initLine+1;
					initCol = (char)(initCol-1);
					coordsOnItinerary.add(new Coord(initCol,initLine));
				}
			}else {
				while(initLine<targetCoord.getLigne()) {
					initLine = initLine+1;
					initCol = (char)(initCol+1);
					coordsOnItinerary.add(new Coord(initCol,initLine));
				}
			}
		}

		for(Coord crd : coordsOnItinerary) {
			System.out.println("COORD ITI"+crd.toString());
		}
		return coordsOnItinerary;
	}


	
	@Override
	public boolean isMoveOk(Coord targetCoord, boolean isPieceToCapture) {
		boolean ret = false;
		ArrayList<Coord> listCoord = new ArrayList<Coord>();
		if(Coord.coordonnees_valides(targetCoord)){
			//if(!isPieceToCapture) {
				//------------------------HAUT DROITE---------------------------------
				int line = this.getLigne();
				char col = this.getColonne();
				Coord coord = new Coord(this.getColonne(),this.getLigne());
				
				while(Coord.coordonnees_valides(coord)) {
					line++;
					col++;
					coord = new Coord(col,line);
					listCoord.add(coord);
				}
				//--------------------------HAUT GAUCHE--------------------------------
				line = this.getLigne();
				col = this.getColonne();
				coord = new Coord(this.getColonne(),this.getLigne());
				while(Coord.coordonnees_valides(coord)) {
					line++;
					col--;
					coord = new Coord(col,line);
					listCoord.add(coord);
				}
				//--------------------------BAS  GAUCHE--------------------------------
				line = this.getLigne();
				col = this.getColonne();
				coord = new Coord(this.getColonne(),this.getLigne());
				while(Coord.coordonnees_valides(coord)) {
					line--;
					col--;
					coord = new Coord(col,line);
					listCoord.add(coord);
				}
				//--------------------------BAS  DROITE--------------------------------
				line = this.getLigne();
				col = this.getColonne();
				coord = new Coord(this.getColonne(),this.getLigne());
				while(Coord.coordonnees_valides(coord)) {
					line--;
					col++;
					coord = new Coord(col,line);
					listCoord.add(coord);
				}
//				for(Coord crd : listCoord) {
//					System.out.println(crd.toString());
//				}
				if(listCoord.contains(targetCoord)) {
					return true;
				}
			//}
		}
		
		return ret;
	}


}

