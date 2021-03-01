package model;


import java.util.ArrayList;
import java.util.List;

import controller.OutputModelData;
import nutsAndBolts.PieceSquareColor;

/**
 * @author francoise.perrin
 *
 * Cette classe gére les aspects métiers du jeu de dame
 * indépendamment de toute vue
 * 
 * Elle délégue é son objet ModelImplementor 
 * le stockage des PieceModel dans une collection
 * 
 * Les piéces sont capables de se déplacer d'une case en diagonale 
 * si la case de destination est vide
 * 
 * Ne sont pas gérés les prises, les rafles, les dames, 
 * 
 * N'est pas géré le fait que lorsqu'une prise est possible
 * une autre piéce ne doit pas étre jouée
 * 
 */
public class Model implements BoardGame<Coord> {

	private PieceSquareColor currentGamerColor;	// couleur du joueur courant

	private ModelImplementor implementor;		// Cet objet sait communiquer avec les PieceModel

	public Model() {
		super();
		this.implementor = new ModelImplementor();
		this.currentGamerColor = ModelConfig.BEGIN_COLOR;

		System.out.println(this);
	}

	@Override
	public String toString() {
		return implementor.toString();
	}



	/**
	 * Actions potentielles sur le model : move, capture, promotion pion, rafles
	 */
	@Override
	public OutputModelData<Coord> moveCapturePromote(Coord toMovePieceCoord, Coord targetSquareCoord) {

		OutputModelData<Coord> outputModelData = null;

		boolean isMoveDone = false;
		Coord toCapturePieceCoord = null;
		Coord toPromotePieceCoord = null;
		PieceSquareColor toPromotePieceColor = null;

		// Si la piéce est déplaéable (couleur du joueur courant et case arrivée disponible)
		if (this.isPieceMoveable(toMovePieceCoord, targetSquareCoord)) {

			// S'il n'existe pas plusieurs piéces sur le chemin
			if (this.isThereMaxOnePieceOnItinerary(toMovePieceCoord, targetSquareCoord)) {

				//Recherche coord de l'éventuelle piéce é prendre
				toCapturePieceCoord = this.getToCapturePieceCoord(toMovePieceCoord, targetSquareCoord);

				// si le déplacement est légal (en diagonale selon algo pion ou dame)
				boolean isPieceToCapture = toCapturePieceCoord != null;
				//System.out.println("IS PIECE TO CAPTURE:"+isPieceToCapture);
				//System.out.println("IS MOVE PIECE POSSIBLE:"+this.isMovePiecePossible(toMovePieceCoord, targetSquareCoord, isPieceToCapture));
				if (this.isMovePiecePossible(toMovePieceCoord, targetSquareCoord, isPieceToCapture)) {

					// déplacement effectif de la piéce
					this.movePiece(toMovePieceCoord, targetSquareCoord);
					isMoveDone = true;

					// suppression effective de la piéce prise 
					this.remove(toCapturePieceCoord);

					// promotion éventuelle de la piéce aprés déplacement 
					if(this.implementor.findPiece(targetSquareCoord) instanceof Promotable) {
							if(((Promotable)this.implementor.findPiece(targetSquareCoord)).isPromotable()) {
								PieceSquareColor color = this.implementor.findPiece(targetSquareCoord).getPieceColor();
								this.remove(targetSquareCoord);
								QueenModel queen = new QueenModel(targetSquareCoord,color);
								toPromotePieceCoord=targetSquareCoord;
								toPromotePieceColor=color;
								this.implementor.addPiece(queen);
							}
					}

					// S'il n'y a pas eu de prise
					// ou si une rafle n'est pas possible alors changement de joueur 
					//System.out.println("IS RAFFLE POSSIBLE:"+isRaflePossible(targetSquareCoord));
					if (!isPieceToCapture || !isRaflePossible(targetSquareCoord)) {	// TODO : Test é changer atelier 4
						this.switchGamer();
					}
					
				}
			}
		}
		System.out.println(this);

		// Constitution objet de données avec toutes les infos nécessaires é la view
		outputModelData = new OutputModelData<Coord>(
				isMoveDone, 
				toCapturePieceCoord, 
				toPromotePieceCoord, 
				toPromotePieceColor);

		return outputModelData;

	}

	private boolean isRaflePossible(Coord initCoord) {
		if(this.implementor.findPiece(initCoord) instanceof PawnModel) {
			Coord target1 = new Coord((char)(initCoord.getColonne()+2),initCoord.getLigne()+2);
			Coord target2 = new Coord((char)(initCoord.getColonne()-2),initCoord.getLigne()+2);
			Coord target3 = new Coord((char)(initCoord.getColonne()-2),initCoord.getLigne()-2);
			Coord target4 = new Coord((char)(initCoord.getColonne()+2),initCoord.getLigne()-2);
			boolean res1 = false;
			if(Coord.coordonnees_valides(target1) && !this.implementor.isPiecehere(target1)) {
				res1 = getToCapturePieceCoord(initCoord,target1) != null;
			}
			boolean res2 = false;
			if(Coord.coordonnees_valides(target2)  && !this.implementor.isPiecehere(target2)) {
				res2= getToCapturePieceCoord(initCoord,target2) != null;
			}
			boolean res3 = false;
			if(Coord.coordonnees_valides(target3) &&  !this.implementor.isPiecehere(target3)) {
				res3= getToCapturePieceCoord(initCoord,target3) != null;
			}
			boolean res4 = false;
			if(Coord.coordonnees_valides(target4) && !this.implementor.isPiecehere(target4)) {
				res4= getToCapturePieceCoord(initCoord,target4) != null;
			}
			
			return res1 || res2 || res3 || res4;
		}else {
			ArrayList<Coord> coords = ((QueenModel)this.implementor.findPiece(initCoord)).getAllPossibleCoordItinerary();
			boolean ret = false;
			for(Coord coord : coords) {
				if(Coord.coordonnees_valides(coord) &&  !this.implementor.isPiecehere(coord)) {
					ret= getToCapturePieceCoord(initCoord,coord) != null;
					if(ret) {
						break;
					}
				}
			}
			return ret;
		}
		
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true si la PieceModel é déplacer est de la couleur du joueur courant 
	 * et que les coordonnées d'arrivées soient dans les limites du tableau
	 * et qu'il n'y ait pas de piéce sur la case d'arrivée
	 */
	boolean isPieceMoveable(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" aprés test unitaires
		boolean bool = false;

		// TODO : à compléter atelier 4 pour gérer les rafles 

		bool = 	this.implementor.isPiecehere(toMovePieceCoord) 
				&& this.implementor.getPieceColor(toMovePieceCoord) == this.currentGamerColor 
				&& Coord.coordonnees_valides(targetSquareCoord) 
				&& !this.implementor.isPiecehere(targetSquareCoord) ;

		return bool ;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return true s'il n'existe qu'1 seule piéce à prendre d'une autre couleur sur la trajectoire
	 * ou pas de piéce é prendre
	 */
	private boolean isThereMaxOnePieceOnItinerary(Coord toMovePieceCoord, Coord targetSquareCoord) {
		boolean onePiece= false;
		List<Coord> coordList = this.implementor.getCoordsOnItinerary(toMovePieceCoord, targetSquareCoord);
		List<Coord> pieceList = new ArrayList<Coord>();
		for(Coord coord : coordList) {
			if(this.implementor.isPiecehere(coord)) {
				pieceList.add(coord);
			}
		}
		if(pieceList.size()==1 && this.implementor.getPieceColor(pieceList.get(0)) != this.currentGamerColor){
			onePiece=true;
		}else if(pieceList.size()==0) {
			onePiece=true;
		}
		return onePiece;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * @return les coord de la piéce à prendre, null sinon
	 */
	private Coord getToCapturePieceCoord(Coord toMovePieceCoord, Coord targetSquareCoord) {
//		Coord toCapturePieceCoord = null;
//		if(isThereMaxOnePieceOnItinerary(toMovePieceCoord,targetSquareCoord)) {
//			List<Coord> coordList = this.implementor.getCoordsOnItinerary(toMovePieceCoord, targetSquareCoord);
//			if(!coordList.isEmpty()) {
//				List<Coord> pieceList = new ArrayList<Coord>();
//				for(Coord coord : coordList) {
//					System.out.println("COORD:"+coord);
//					if(this.implementor.isPiecehere(coord)) {
//						pieceList.add(coord);
//					}
//				}
//				if(pieceList.size()!=0) {
//					return pieceList.get(0);
//				}
//			}
//		}
//		return toCapturePieceCoord;
		Coord toCapturePieceCoord = null;
		List<Coord> coordsOnItinerary = this.implementor.getCoordsOnItinerary(toMovePieceCoord, targetSquareCoord);

		if (coordsOnItinerary != null) { 
			int count = 0;
			Coord potentialToCapturePieceCoord = null;
			for (Coord coordOnItinerary : coordsOnItinerary) {
				if (this.implementor.isPiecehere(coordOnItinerary)) {
					count++;
					potentialToCapturePieceCoord = coordOnItinerary;
				}
			}
			// Il n'existe qu'1 seule pièce à prendre d'une autre couleur sur la trajectoire
			if (count == 0 
					|| (count == 1 && this.currentGamerColor != 
					this.implementor.getPieceColor(potentialToCapturePieceCoord))) {
				toCapturePieceCoord = potentialToCapturePieceCoord;
			}
		}

		return toCapturePieceCoord;
	}

	/**
	 * @param initCoord
	 * @param targetCoord
	 * @param isPieceToCapture
	 * @return true si le déplacement est légal
	 * (s'effectue en diagonale, avec ou sans prise)
	 * La PieceModel qui se trouve aux coordonnées passées en paramétre 
	 * est capable de répondre écette question (par l'intermédiare du ModelImplementor)
	 */
	boolean isMovePiecePossible(Coord toMovePieceCoord, Coord targetSquareCoord, boolean isPieceToCapture) { // TODO : remettre en "private" aprés test unitaires
		return this.implementor.isMovePieceOk(toMovePieceCoord, targetSquareCoord, isPieceToCapture ) ;
	}

	/**
	 * @param toMovePieceCoord
	 * @param targetSquareCoord
	 * Déplacement effectif de la PieceModel
	 */
	void movePiece(Coord toMovePieceCoord, Coord targetSquareCoord) { // TODO : remettre en "private" aprés test unitaires
		this.implementor.movePiece(toMovePieceCoord, targetSquareCoord);
	}

	/**
	 * @param toCapturePieceCoord
	 * Suppression effective de la piéce capturée
	 */
	private void remove(Coord toCapturePieceCoord) { 
		this.implementor.removePiece(toCapturePieceCoord);
	}

	void switchGamer() { // TODO : remettre en "private" aprés test unitaires
		this.currentGamerColor = (PieceSquareColor.WHITE).equals(this.currentGamerColor) ?
				PieceSquareColor.BLACK : PieceSquareColor.WHITE;

	}


}