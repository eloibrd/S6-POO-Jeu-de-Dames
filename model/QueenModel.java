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
	public int compareTo(PieceModel p1){
		if(p1.getLigne() < this.getLigne()){
			return -1;
		}else if(p1.getLigne() == this.getLigne()){
			if(p1.getColonne() - 'a' < this.getColonne() - 'a'){
				return 1;
			}else{
				return -1;
			}
		}else{
			return 1;
		}
	}

	@Override
	public List<Coord> getCoordsOnItinerary(Coord targetCoord) {
		Coord initCoord = new Coord(this.getColonne(),this.getLigne()) ;
		List<Coord> coordsOnItinerary = new ArrayList<Coord>();
		List<Integer> lineList = new ArrayList<Integer>();
		List<Character> colList = new ArrayList<Character>();
		if(initCoord.getLigne()<targetCoord.getLigne()) {
			int line = initCoord.getLigne()+1;
			while(line<targetCoord.getLigne()) {
				lineList.add(line);
				line++;
			}
		}else {
			int line = targetCoord.getLigne()+1;
			while(line<initCoord.getLigne()) {
				lineList.add(line);
				line++;
			}
		}
		
		if(initCoord.getColonne()<targetCoord.getColonne()) {
			char col = (char) (initCoord.getColonne()+1);
			while(col<targetCoord.getColonne()) {
				colList.add(col);
				col++;
			}
		}else {
			char col = (char) (targetCoord.getColonne()+1);
			while(col<initCoord.getColonne()) {
				colList.add(col);
				col++;
			}
		}
		
		for(int i =0; i<lineList.size();i++) {
			coordsOnItinerary.add(new Coord(colList.get(0),lineList.get(0)));
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
				for(Coord crd : listCoord) {
					System.out.println(crd.toString());
				}
				if(listCoord.contains(targetCoord)) {
					return true;
				}
//			}else {
//				
//			}
		}
		
		return ret;
	}
}

