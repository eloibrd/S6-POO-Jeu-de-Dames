package controller.localController;


import java.util.Optional;

import controller.InputViewData;
import controller.Mediator;
import controller.OutputModelData;
import gui.CheckersSquareGui;
import gui.View;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.BoardGame;
import model.Coord;
import model.ModelConfig;
import nutsAndBolts.PieceSquareColor;


/**
 * @author francoiseperrin
 *
 * Le controller a 2 responsabilités :
 * 	- il écoute les clics de souris de l'utilisateur sur la vue
 * 	- il invoque la méthode moveCapturePromote() du model
 * 	  si actions (move + prise + promotion) OK sur model alors elles sont propagées sur view 
 *    (invoque méthode moveCapturePromote() de la view)
 *    
 * La view et le model ne gérant pas les coordonnées des cases de la méme maniére
 * le controller assure la conversion :
 * 	- index de 0 à 99 pour la view
 * 	- Coord (col, ligne) pour le model ['a'..'j'][10..1]
 * 
 */
public class Controller implements Mediator, BoardGame<Integer>, EventHandler<MouseEvent>  {


	private BoardGame<Coord> model;
	private View view;

	// Cette valeur est MAJ chaque fois que l'utilisateur clique sur une piéce
	// Elle doit étre conservée pour étre utilisée lorsque l'utilisateur clique sur une case
	private int toMovePieceIndex;	

	public Controller() {
		this.model =  null;
		this.view = null;
		this.setToMovePieceIndex(-1);
	}

	private void setToMovePieceIndex(int toMovePieceIndex) {
		this.toMovePieceIndex = toMovePieceIndex;
	}

	public int getToMovePieceIndex() {
		return toMovePieceIndex;
	}

	//////////////////////////////////////////////////////////////////
	//
	// Controller vu comme un médiateur entre la view et le model
	//
	//////////////////////////////////////////////////////////////////

	public void setView(View view) {
		this.view = view;
	}
	public void setModel(BoardGame<Coord> model) {
		this.model =  model;
	}

	////////////////////////////////////////////////////////////////////
	//
	// Controller vu comme un Ecouteur des événement souris sur la view
	//
	////////////////////////////////////////////////////////////////////

	@Override
	public void handle(MouseEvent mouseEvent) {
		try {
			if(mouseEvent.getSource() instanceof CheckersSquareGui)
				checkersSquareGuiHandle(mouseEvent);
			else
				checkersPieceGuiHandle(mouseEvent);
		}
		catch (Exception e) {
		}
	}

	/**
	 * @param mouseEvent
	 * Ecoute les événements sur les PieceGui
	 */
	private void checkersPieceGuiHandle(MouseEvent mouseEvent) {

		// Recherche PieceGui sélectionnée
		ImageView selectedPiece = (ImageView) mouseEvent.getSource();

		// Recherche et fixe coordonnée de la piéce sélectionnée 
		CheckersSquareGui parentSquare = (CheckersSquareGui)  selectedPiece.getParent();
		this.setToMovePieceIndex(parentSquare.getSquareCoord());

		mouseEvent.consume();
	}
	/**
	 * @param mouseEvent
	 * Ecoute les événements sur les SquareGui
	 */
	private void checkersSquareGuiHandle(MouseEvent mouseEvent) {

		// Recherche SquareGUI sélectionné
		CheckersSquareGui square = (CheckersSquareGui) mouseEvent.getSource();
		int targetSquareIndex = square.getSquareCoord();

		// Le controller va invoquer la méthode moveCapturePromotion() du model
		// et si le model confirme que la piéce a bien été déplacée à cet endroit, 
		// il invoquera une méthode de la view pour la rafraichir
		this.moveCapturePromote(this.getToMovePieceIndex(), targetSquareIndex);

		// il n'y a plus de piéce à déplacer
		this.setToMovePieceIndex(-1);

		// On à vite que le parent ne récupére l'event
		mouseEvent.consume();
	}


	//////////////////////////////////////////////////////////////////
	//
	// Controller vu comme un Substitut du model 
	// il invoque les méthodes du model 
	// aprés actions de l'utilisateur sur la vue
	//
	//////////////////////////////////////////////////////////////////

	/**
	 * Invoque méthode moveCapturePromote() du model (aprés transformation des coordonnées)
	 * Si déplacement effectif sur model, invoque méthode actionOnGui() de la view
	 * pour rafraichir affichage en fonction des données retournées par le model
	 */
	@Override
	public OutputModelData<Integer> moveCapturePromote(Integer toMovePieceIndex, Integer targetSquareIndex) {

		OutputModelData<Integer> outputControllerData = null;
		Coord targetCoord = transformIndexToCoord(targetSquareIndex);
		Coord pieceToMoveCoord = transformIndexToCoord(toMovePieceIndex);
		OutputModelData<Coord> outPutCoordData = model.moveCapturePromote(pieceToMoveCoord, targetCoord);
		if(outPutCoordData.isMoveDone ) {
			int capturedPieceIndex = transformCoordToIndex(outPutCoordData.capturedPieceCoord);
			int promotedPieceIndex = transformCoordToIndex(outPutCoordData.promotedPieceCoord);
			
			InputViewData<Integer> dataToRefreshView = new InputViewData<Integer>(toMovePieceIndex,targetSquareIndex,capturedPieceIndex,promotedPieceIndex,outPutCoordData.promotedPieceColor) ;

			view.actionOnGui(dataToRefreshView);
			view.finJeu(model.isGameOver());
		}
		// Inutile de reconstituer un objetOutputModelData<Integer>, aucun client ne le récupére en mode local
		return outputControllerData;
	}


	/**
	 * @param squareIndex
	 * @param length
	 * @return les coordonnées métier calculées é partir de l'index du SquareGUI sous la PieceGUI
	 */
	private Coord transformIndexToCoord (int squareIndex) {
		Coord coord = null;
		int  length = ModelConfig.LENGTH;
		char col = (char) ((squareIndex)%length + 'a');
		int ligne = length - (squareIndex)/length;
		coord = new Coord(col, ligne);
		return coord;
	}

	private int transformCoordToIndex (Coord coord) {
		int squareIndex = -1;
		int  length = ModelConfig.LENGTH;
		if (coord != null) {
			squareIndex = (length - coord.getLigne()) * length + (coord.getColonne()-'a');
		}
		return squareIndex;
	}

	@Override
	public PieceSquareColor isGameOver() {
		return model.isGameOver();
	}


}




