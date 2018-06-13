package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Animal inside a {@code World} that can move depending on the available
 * choices it has at its position.
 * 
 */

abstract public class Animal {

	/*
	 * Position of the animal
	 */
	private Vector2D position;

	/**
	 * Constructs an animal with a specified position.
	 * 
	 * @param position
	 *            Position of the animal in the labyrinth
	 */

	public Animal(Vector2D position) {
		int x = position.getX();
		int y = position.getY();

		this.position = new Vector2D(x,y);
	}

	/**
	 * Second constructor for the copy with an animal that needs to be copied as an argument
	 * 
	 * @param autre
	 * 				Animal to copy
	 */

	public Animal(Animal autre){
		position = autre.position;
	}

	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)})
	 * @return The next direction of the animal, chosen in {@code choices}
	 */

	abstract public Direction move(Direction[] choices);

	/**
	 * Updates the animal position with a direction.
	 * <p>
	 * <b>Note</b> : Do not call this function in {@code move(Direction[]
	 * choices)} !
	 * 
	 * @param dir
	 *            Direction that the animal has taken
	 */

	public final void update(Direction dir) {
		position = position.addDirectionTo(dir);
	}

	/**
	 * Sets new position for Animal.
	 * <p>
	 * <b>Note</b> : Do not call this function in {@code move(Direction[]
	 * choices)} !
	 * 
	 * @param position
	 */

	public final void setPosition(Vector2D position) {
		
		this.position = position;
	}

	/**
	 * Returns position vector of animal.
	 * 
	 * @return Current position of animal.
	 */

	public final Vector2D getPosition() {
		return position;
	}

	/**
	 * Creates a copy of the animal
	 * 
	 * @return a copy of the animal
	 */
	abstract public Animal copy();
}
