package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * World that is represented by a labyrinth of tiles in which an {@code Animal}
 * can move.
 * 
 */

public abstract class World {

	/* tiles constants */
	public static final int FREE = 0;
	public static final int WALL = 1;
	public static final int START = 2;
	public static final int EXIT = 3;
	public static final int NOTHING = -1;
	
	/* labyrinth */
	private int[][] labyrinth;
	private Vector2D dep;
	private Vector2D arr;

	/**
	 * Constructs a new world with a labyrinth. The labyrinth must be rectangle.
	 * 
	 * @param labyrinth
	 *            Structure of the labyrinth, an NxM array of tiles
	 */

	public World(int[][] labyrinth) {
		int nbC = labyrinth[0].length;
		int nbL = labyrinth.length; 
		this.labyrinth = new int[nbL][nbC];
		boolean rect = true;

		for (int i = 1; i < nbL; i++) {
			if(labyrinth[i].length != nbC){
				rect = false;
			}
		}

		if(rect == false){
			System.out.println("Le labyrinth n'est pas rectangle");
		}
		else{
			for (int y = 0; y < nbL; y++) {
				for (int x = 0; x < nbC; x++) {
					if(labyrinth[y][x] == START){
						dep = new Vector2D(x, y);
					}
					else if(labyrinth[y][x] == EXIT){
						arr = new Vector2D(x, y);
					}
					this.labyrinth[y][x] = labyrinth[y][x];
				}
			}
		}
	}

	/**
	 * Determines whether the labyrinth has been solved by every animal.
	 * 
	 * @return <b>true</b> if no more moves can be made, <b>false</b> otherwise
	 */

	abstract public boolean isSolved();

	/**
	 * Resets the world as when it was instantiated.
	 */

	abstract public void reset();

	/**
	 * Returns a copy of the list of all current animals in the world.
	 * 
	 * @return A list of all animals in the world
	 */

	abstract public List<Animal> getAnimals();

	/**
	 * Checks in a safe way the tile number at position (x, y) in the labyrinth.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return The tile number at position (x, y), or the NONE tile if x or y is
	 *         incorrect.
	 */

	public final int getTile(int x, int y) {
		if((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight())){
			return labyrinth[y][x];
		}
		else{
			return NOTHING; 
		}
	}

	/**
	 * Determines if coordinates are free to walk on.
	 * 
	 * @param x
	 *            Horizontal coordinate
	 * @param y
	 *            Vertical coordinate
	 * @return <b>true</b> if an animal can walk on tile, <b>false</b> otherwise
	 */

	public final boolean isFree(int x, int y) {
		if(getTile(x, y) == NOTHING || getTile(x, y) == WALL){
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Computes and returns the available choices for a position in the
	 * labyrinth. The result will be typically used by {@code Animal} in
	 * {@link ch.epfl.maze.physical.Animal#move(Direction[]) move(Direction[])}
	 * 
	 * @param position
	 *            A position in the maze
	 * @return An array of all available choices at a position
	 */

	public final Direction[] getChoices(Vector2D position) {
		int x = position.getX();
		int y = position.getY();
		ArrayList<Direction> choices = new ArrayList<>();

		if(isFree(x, y+1)){
			choices.add(Direction.DOWN);
		}

		if(isFree(x, y-1)){
			choices.add(Direction.UP);
		}

		if(isFree(x+1, y)){
			choices.add(Direction.RIGHT);
		}

		if(isFree(x-1, y)){
			choices.add(Direction.LEFT);
		}

		Direction[] choicesEND;

		if (choices.isEmpty()) {
			choicesEND = new Direction[1];
			choicesEND[0] = Direction.NONE;

			return choicesEND.clone();
		}
		else{
			choicesEND = new Direction[choices.size()];
			choices.toArray(choicesEND);

			return choicesEND.clone();
		}
	}

	/**
	 * Returns horizontal length of labyrinth.
	 * 
	 * @return The horizontal length of the labyrinth
	 */

	public final int getWidth() {
		return labyrinth[0].length;
	}

	/**
	 * Returns vertical length of labyrinth.
	 * 
	 * @return The vertical length of the labyrinth
	 */

	public final int getHeight() {
		return labyrinth.length;
	}

	/**
	 * Returns the entrance of the labyrinth at which animals should begin when
	 * added.
	 * 
	 * @return Start position of the labyrinth, null if none.
	 */

	public final Vector2D getStart() {
		if(dep != null){
			return dep;
		}
		else return null;
	}

	/**
	 * Returns the exit of the labyrinth at which animals should be removed.
	 * 
	 * @return Exit position of the labyrinth, null if none.
	 */

	public final Vector2D getExit() {
		if(arr != null){
			return arr;
		}
		else return null;
	}
}
