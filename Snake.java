//written by Snow 10/2/13
//references http://psnbtech.blogspot.com/2012/01/tutorial-java-creating-snake-game-part_07.html

package snake;

import java.awt.Point;
import java.util.LinkedList;

import snake.GameBoard.squareType;

public class Snake {
	//an enum to get the direction the snake is moving
	public static enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		NONE
	}
	//storing the current direction the snake is moving so we can't move before the game updates the frame
	private Direction currentDirection;
	private Direction temporaryDirection;
	private GameBoard board;
	private LinkedList<Point> points;
	public Snake(GameBoard board) {
		this.board = board;
		this.points = new LinkedList<Point>();
	}
	//method called whenever the game is started
	public void resetSnake() {
		this.currentDirection = Direction.NONE;
		this.temporaryDirection = Direction.NONE;
		Point head = new Point(GameBoard.boardSize / 2, GameBoard.boardSize / 2);
		points.clear();
		points.add(head);
		board.setSquare(head.x, head.y, squareType.HEADR);
	}
	//making sure we don't crash into ourselves
	public void setDirection(Direction direction) {
		if(direction.equals(Direction.UP) && currentDirection.equals(Direction.DOWN)) {
			return;
		} else if(direction.equals(Direction.DOWN) && currentDirection.equals(Direction.UP)) {
			return;
		} else if(direction.equals(Direction.LEFT) && currentDirection.equals(Direction.RIGHT)) {
			return;
		} else if(direction.equals(Direction.RIGHT) && currentDirection.equals(Direction.LEFT)) {
			return;
		}
		this.temporaryDirection = direction;
	}
	//how we move the snake and check for eaten fruit
	public squareType updateSnake() {
		//set direction from keyboard input
		this.currentDirection = temporaryDirection;
		//sets the snake's head's location
		Point head = points.getFirst();
		//gets the current direction of the snake and returns false if its in a wall
		switch (currentDirection){
		case UP:
			if(head.y <=0) {
				return null;
			}
			points.push(new Point(head.x, head.y - 1));
			break;
		case DOWN:
			if(head.y >= GameBoard.boardSize - 1) {
				return null;
			}
			points.push(new Point(head.x, head.y + 1));
			break;
		case LEFT:
			if(head.x <= 0) {
			    return null;
			}
			points.push(new Point(head.x - 1, head.y));
			break;
		case RIGHT:
			if(head.x >= GameBoard.boardSize - 1) {
			    return null;
			}
			points.push(new Point(head.x + 1, head.y));
			break;
		case NONE:
			return squareType.EMPTY;
		}
		//updates head variable
		head = points.getFirst();
		//gets the square type at head location before it was the head and changes the snakes size accordingly
		squareType lastType = board.getSquareType(head.x, head.y);
		if(!lastType.equals(squareType.APPLE)) {
			Point last = points.removeLast();
			board.setSquare(last.x, last.y, squareType.EMPTY);
			lastType = board.getSquareType(head.x, head.y);
		}
		//sets the body to body squareType
		if (points.size()>1) {
			Point temp = points.get(1);
			int z=1;
			while(z<points.size()) {
				board.setSquare(temp.x, temp.y, squareType.BODY);
				temp = points.get(z);
				z++;
			}
		}
		//old transform code
		//AffineTransform origAt = g.getTransform();
		//AffineTransform rot = new AffineTransform();
		//rot.rotate(Math.PI / 2, 262.5,262.5);
		//g.dispose();
		//g.transform(rot);
		//g.setTransform(origAt);
		/*
		//set the square at the head and tail locations to a snake head and tail
		if (points.size()>2)
			board.setSquare(points.getLast().x, points.getLast().y, squareType.TAIL);
		*/
		if (currentDirection.equals(Direction.UP)) {
			board.setSquare(head.x, head.y, squareType.HEADU);
		} else if (currentDirection.equals(Direction.DOWN)) {
			board.setSquare(head.x, head.y, squareType.HEADD);
		} else if (currentDirection.equals(Direction.RIGHT)) {
			board.setSquare(head.x, head.y, squareType.HEADR);
		} else {
			board.setSquare(head.x, head.y, squareType.HEADL);
		}
		return lastType;
	}
	//snake length method
	public int getSnakeLength() {
		return points.size();
	}
	//snake direction method
	public Direction getCurrentDirection() {
		return currentDirection;
	}
}
