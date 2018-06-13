package ch.epfl.maze.physical.zoo;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomMovement;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Mouse A.I. that remembers only the previous choice it has made.
 * 
 */

public class Mouse extends Animal {

	/* Previous choice */
	private Direction previousChoice;

	/**
	 * Constructs a mouse with a starting position.
	 * 
	 * @param position
	 *            Starting position of the mouse in the labyrinth
	 */

	public Mouse(Vector2D position) {
		super(position);
		previousChoice = null;
	}

	/**
	 * Second constructor creating a copy of the previous Mouse
	 * 
	 * @param autre
	 * 			previous Mouse
	 */

	public Mouse(Mouse autre){
		super(autre);
	}

	/**
	 * Moves according to an improved version of a <i>random walk</i> : the
	 * mouse does not directly retrace its steps.
	 */

	@Override
	public Direction move(Direction[] choices) {
		previousChoice = RandomMovement.randomMove(choices, previousChoice);
		return previousChoice;
	}

	@Override
	public Animal copy() {
		return new Mouse(this);
	}
}
