//written by Snow 9/27/13
//references http://psnbtech.blogspot.com/2012/01/tutorial-java-creating-snake-game-part_8065.html

package snake;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;


public class GameBoard {

	//# of pixels each tile horizontally and vertically
	public static final int squareSize = 25;
	//# of tiles the map is
	public static final int boardSize = 20;
	//images
	public static Image body, headu, headd, headl, headr, apple; //tail
	//tile types
	public static enum squareType {
		BODY(body),
		HEADU(headu),
		HEADD(headd),
		HEADL(headl),
		HEADR(headr),
		APPLE(apple),
		//TAIL(tail),
		EMPTY(null);
		//image of the tile
		private Image squareImage;
		//tile type constructor
		private squareType(Image image) {
			this.squareImage = image;
		}
		//get image method
		public Image getSquareType() {
			return squareImage;
		}
	}
	//tile type array to store them all
	private squareType[] squares;
	//basic board constructor
	public GameBoard() {
		squares = new squareType[boardSize*squareSize];
		body = Toolkit.getDefaultToolkit().createImage("body.png");
		headu = Toolkit.getDefaultToolkit().createImage("headu.png");
		headd = Toolkit.getDefaultToolkit().createImage("headd.png");
		headl = Toolkit.getDefaultToolkit().createImage("headl.png");
		headr = Toolkit.getDefaultToolkit().createImage("headr.png");
		apple = Toolkit.getDefaultToolkit().createImage("apple.png");
		//tail = Toolkit.getDefaultToolkit().createImage("tail.png");
	}
	//reset board function
	public void resetBoard() {
		for(int i = 0; i < squares.length; i++) {
			squares[i] = squareType.EMPTY;
		}
	}
	//tile set function, x coordinate, y coordinate and type
	public void setSquare(int x, int y, squareType type) {
		squares[y * boardSize + x] = type;
	}
	//get tile function
	public squareType getSquareType(int x, int y) {
		return squares[y * boardSize + x];
	}
	//draws the game board
	public void draw(Graphics2D g) {
		//loop through all tiles
		for(int i = 0; i < squares.length; i++) {
			//calculate x and y coordinates of tile
			int x = i % boardSize;
			int y = i / boardSize;
			//if tile is empty, don't render it
			if(squares[i].equals(squareType.EMPTY)) {
				continue;
			}
			//if tile is a fruit, make a fruit in it, else mehh
			if(squares[i].equals(squareType.APPLE)) {
				g.drawImage(apple, x*squareSize, y*squareSize, squareSize, squareSize, null);
				//g.setColor(squareType.SNAKE.getColor());
			}	else if (squares[i].equals(squareType.HEADU)) {
				g.drawImage(headu, x*squareSize, y*squareSize, squareSize, squareSize, null);
			}   else if (squares[i].equals(squareType.HEADD)) {
				g.drawImage(headd, x*squareSize, y*squareSize, squareSize, squareSize, null);
			} 	else if (squares[i].equals(squareType.HEADL)) {
				g.drawImage(headl, x*squareSize, y*squareSize, squareSize, squareSize, null);
			}   else if (squares[i].equals(squareType.HEADR)) {
				g.drawImage(headr, x*squareSize, y*squareSize, squareSize, squareSize, null);
			} 	else {
				g.drawImage(body, x*squareSize, y*squareSize, squareSize, squareSize, null);
				//fillRect(x * squareSize + 1, y * squareSize + 1, squareSize - 2, squareSize - 2);
			}
		}
	}
}
