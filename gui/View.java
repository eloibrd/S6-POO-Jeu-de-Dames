package gui;

import java.util.Optional;

import controller.InputViewData;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import nutsAndBolts.PieceSquareColor;

/**
 * @author francoise.perrin
 * 
 * Cette classe est la fen�tre du jeu de dames
 * Elle d�l�gue a un objet Board la gestion de l'affichage du damier
 * et affiche les axes le long du damier
 * 
 */

public class View extends BorderPane {

	// le damier compos� de carr�s noirs et blancs
	// sur lesquels sont positionn�s des pi�ces noires ou blanches
	Pane board ;

	public View (EventHandler<MouseEvent> clicListener) {
		super();
		// les cases et le pi�ces sur le damier seront �cout�es par l'objet
		// pass� en param�tre au constructeur
		board = new Board(clicListener);

		
		// cr�ation d'un fond d'�cran qui contient le damier + les axes 
		BorderPane checkersBoard = new BorderPane();	

		// la taille du damier est fonction de taille de la Scene
		board.prefWidthProperty().bind(this.widthProperty());
		board.prefHeightProperty().bind(this.heightProperty());

		// ajout du damier au centre du fond d'�cran
		checkersBoard.setCenter(board);

		// ajout des axes sur les cot�s du damier
		checkersBoard.setTop(createHorizontalAxis());
		checkersBoard.setBottom(createHorizontalAxis());
		checkersBoard.setLeft(createVerticalAxis());
		checkersBoard.setRight(createVerticalAxis());

		// ajout du fond d'�cran �la vue
		this.setCenter(checkersBoard);


		
	}

	///////////////////////////////////////////////////////////////////////////////////// 
	// Méthode invoquée depuis le Controller pour propager les déplacements
	// effectués sur le model sur la vue
	/////////////////////////////////////////////////////////////////////////////////////
	
	public void actionOnGui(InputViewData<Integer> dataToRefreshView) {
		((Board)this.board).actionOnGui(dataToRefreshView);
		
	}
	
	//////////////////////////////////////////////////////////////////////////
	

	private GridPane createHorizontalAxis() {
		GridPane pane = new GridPane();
		pane.prefWidthProperty().bind(this.widthProperty());
		for (char c = 'a'; c<='j'; c++){
			Label label1 = new Label(String.valueOf(c));
			label1.setAlignment(Pos.CENTER);
			label1.prefWidthProperty().bind(pane.prefWidthProperty().divide(GuiConfig.SIZE));
			pane.add(label1, c-'a', 0);
		}
		return pane;
	}

	private GridPane createVerticalAxis() {
		GridPane pane = new GridPane();
		pane.prefHeightProperty().bind(this.heightProperty());
		for (int c = 10; c>=1; c--){
			Label label1 = new Label(String.valueOf(c));
			label1.prefHeightProperty().bind(pane.prefHeightProperty().divide(GuiConfig.SIZE));
			pane.add(label1, 0, 10-c+1);
		}
		return pane;
	}
	
	public void finJeu(PieceSquareColor color) {
		if( color != null){
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Partie Finie");
			String colorText = (color==PieceSquareColor.WHITE)? "blancs" : "noirs";
			String contentText = "Les " +colorText + " gagnent la partie !";
			alert.setContentText(contentText);
			Optional<ButtonType> result = alert.showAndWait();
			if(!result.isPresent())
				System.exit(0);
			else if(result.get() == ButtonType.OK)
				System.exit(0);
		}
	}

	
}


