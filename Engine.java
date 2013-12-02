//written by Snow 9/26/13
//references http://psnbtech.blogspot.com/2012/01/tutorial-java-creating-snake-game-part_6119.html

package snake;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import snake.GameBoard.squareType;
import snake.Snake.Direction;

public class Engine extends KeyAdapter {
	//canvas instance to draw to
	private Canvas canvas;
	//instance of snake in engine constructor
	private Snake snake;
	//instance of the gameboard
	private GameBoard board;
	//gotta keep score
	private int score;
	private static int ups = 10;
	//game over man!
	private boolean gameOver;
	//engine instance
	private Button startButton,settingsButton,exitButton;
	private Frame f,game;
	private Panel mypanel;

	public Engine(Canvas canvas) {
		this.f = new Frame("Menu!");
		this.mypanel = new Panel();
		this.startButton = new Button("Start Game");
		this.settingsButton = new Button("Settings");
		this.exitButton = new Button("Exit");
		mypanel.add(startButton);
		mypanel.add(settingsButton);
		mypanel.add(exitButton);
		f.add(mypanel);
		f.pack();
		f.setVisible(true);
		f.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){System.exit(0);}
				}
		);
		startButton.addActionListener(this);
		settingsButton.addActionListener(this);
		exitButton.addActionListener(this);
		this.game = new Frame("Snake!");
		game.setVisible(false);
		game.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){System.exit(0);}
				}
		);
		//eng = new Engine(canvas);
		this.canvas = canvas;
		this.board = new GameBoard();
		this.snake = new Snake(board);
		//this.menu = new Menu(menu);
		//resets game after losing
		resetGame();
		//listens for this
		canvas.addKeyListener(this);
	}
	//game constructor
	public void startGame() {
		canvas.createBufferStrategy(2);
		//introduce the 2d graphics engine
		Graphics2D g = (Graphics2D)canvas.getBufferStrategy().getDrawGraphics();
		//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//setting up the game loop
		long start = 0L;
		long sleepDuration = 0L;
		while(true){
			//set the start time
			start = System.currentTimeMillis();
			//updates the window
			update();
			//displaces the graphics
			render(g);
			canvas.getBufferStrategy().show();
			//clear the screen to draw a fresh frame next time
			g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			//set the time that the loop finishes
			sleepDuration = (1000L/ups) - (System.currentTimeMillis() - start);
			//if the sleep duration is more than 0 do it
			if(sleepDuration>0) {
				try {
					Thread.sleep(sleepDuration);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	//updates the frame
	private void update() {
		if(gameOver || !canvas.hasFocus()) {
			return;
		}
		squareType snakeSquare = snake.updateSnake();
		if(snakeSquare == null || snakeSquare.equals(squareType.BODY)) {
			gameOver = true;
		} else if(snakeSquare.equals(squareType.APPLE)) {
			score += 1;
			spawnApple();
		}
	}
	//render that frame!
	private void render(Graphics2D g) {
		board.draw(g);
		g.setColor(Color.BLACK);
		if(gameOver) {
			String message = new String("Final Score: " + score);
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 250);
			message = new String("Play again? Press Enter");
			g.drawString(message, canvas.getWidth() / 2 - (g.getFontMetrics().stringWidth(message) / 2), 350);
		} else {
			g.drawString("Score:" + score, 20, 20);
		}
	}
	//reset method
	private void resetGame() {
		board.resetBoard();
		snake.resetSnake();
		score = 0;
		gameOver = false;
		spawnApple();
	}
	//spawn a the fruit!
	private void spawnApple() {
		int random = (int)(Math.random() * ((GameBoard.boardSize * GameBoard.boardSize) - snake.getSnakeLength()));
		int emptyFound = 0, index = 0;
		while(emptyFound < random) {
			index++;
			if(board.getSquareType(index % GameBoard.boardSize, index / GameBoard.boardSize).equals(squareType.EMPTY)) {
				emptyFound++;
			}
		}
		board.setSquare(index % GameBoard.boardSize, index / GameBoard.boardSize, squareType.APPLE);
	}
	//Overriding keyPressed from KeyAdapter
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			snake.setDirection(Direction.UP);
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			snake.setDirection(Direction.DOWN);
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			snake.setDirection(Direction.LEFT);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			snake.setDirection(Direction.RIGHT);
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
			resetGame();
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Start Game") {
			f.setVisible(false);
			game.setVisible(true);
		}
		if (e.getActionCommand() == "Exit")
			System.exit(0);
		if (e.getActionCommand() == "Settings")
	}
	//main function
	public static void main(String[] args) {
		Canvas canvas = new Canvas();
		Engine eng = new Engine(canvas);
		canvas.setPreferredSize(new Dimension(GameBoard.boardSize * GameBoard.squareSize, GameBoard.boardSize * GameBoard.squareSize));
		eng.game.pack();
		eng.game.startGame();
	}
}
