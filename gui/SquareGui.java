package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Coord;
import nutsAndBolts.PieceSquareColor;

/**
 * @author francoiseperrin
 * 
 * Classe d'affichage des carrés du damier
 * leur couleur est initialisé par les couleurs par défaut du jeu
 *
 */
class SquareGui extends BorderPane implements CheckersSquareGui {

	private int coord ;
	private PieceGui piece ;
	
	
	public SquareGui() {
		super();
	}
	
	public SquareGui(int coord , PieceGui piece) {
		super();
		setPiece(piece);
		setCoord(coord);
	}
	
	public int getCoord() {
		return coord;
	}

	public void setCoord(int coord) {
		this.coord = coord;
	}

	public PieceGui getPiece() {
		return piece;
	}

	public void setPiece(PieceGui piece) {
		this.piece = piece;
	}

	/**
	 *Retourne l'indice du carré sur la grille (N� de 0 � 99)
	 */
	@Override
	public int getSquareCoord() {
		int index = -1;
		Pane parent = (Pane) this.getParent();
		index = parent.getChildren().indexOf(this);
		return index;
	}

}
