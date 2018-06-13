package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pac-Man character, from the famous game of the same name.
 * 
 */

public class PacMan extends Prey {

	/**
	 * Constructs a PacMan with the given position
	 * @param position
	 * 				given position
	 */

	public PacMan(Vector2D position) {
		super(position);
	}

	/**
	 * Second constructor creating a copy of he previous PacMan
	 * 
	 * @param autre
	 * 			previous PacMan
	 */

	public PacMan(PacMan autre){
		super(autre);
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		return super.move(choices);
	}

	@Override
	public Animal copy() {
		return new PacMan(this);
	}
}
