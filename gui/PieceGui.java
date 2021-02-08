package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nutsAndBolts.PieceSquareColor;


/**
 * @author francoise.perrin
 * 
 * Cette classe permet de donner une image aux pi�ces
 *
 */

public class PieceGui extends ImageView implements CheckersPieceGui {
	
	private PieceSquareColor pieceColor ;
	
	
	public PieceGui() {
		super();
	}
	
	public PieceGui(PieceSquareColor color , Image image ) {
		super();
		setPieceColor(color);
		setPieceImage(image);
	}
	
	@Override
	public void promote(Image image) {
		
		// ToDo Atelier 2, utile pour Atelier 3
		setPieceImage(image);
	}

	@Override
	public boolean hasSameColorAsGamer(PieceSquareColor gamerColor) {
		return gamerColor == this.pieceColor;
	}

	public PieceSquareColor getPieceColor() {
		return pieceColor;
	}

	public void setPieceColor(PieceSquareColor pieceColor) {
		this.pieceColor = pieceColor;
	}

	public void setPieceImage(Image pieceImage) {
		this.setImage(pieceImage);
	}
	
}