package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Blue ghost from the Pac-Man game, targets the result of two times the vector
 * from Blinky to its target.
 * 
 */

public class Inky extends Predator {

	/* Previous choice */
	private Direction previousChoice;

	/* Number of steps */
	private int steps;

	/* "Walking" mode ('s' for SCATTER / 'c' for CHASE) */
	private char mode = 'c';

	/* Initial position */
	private final Vector2D posInitiale; 

	/**
	 * Constructs a Inky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Inky in the labyrinth
	 */

	public Inky(Vector2D position) {
		super(position);
		previousChoice = position.toDirection();
		steps = 0;
		mode = 'c';
		posInitiale = position;
	}

	/**
	 * Second constructor creating a copy of the previous Inky
	 * 
	 * @param autre
	 * 			previous Inky
	 */

	public Inky(Inky autre){
		super(autre);
		previousChoice = autre.getPosition().toDirection();
		steps = 0;
		mode = 'c';
		posInitiale = autre.getPosition();
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		++steps;
		Prey prey = commonChosenPrey(daedalus);

		if (this.getPosition() == posInitiale) {
			steps = 0;
			mode = 'c';
		} else if (this.getPosition() == prey.getPosition()) {
			return Direction.NONE;
		}

		if (steps <= SCATTER_DURATION && mode == 's') {
			previousChoice = targetedMovement(choices, this.getPosition(), posInitiale, previousChoice);
			if (steps == SCATTER_DURATION) {
				steps = 0;
				mode = 'c';
			} else {
				mode = 's';
			}
		} else if (steps <= CHASE_DURATION && mode == 'c') {
			Blinky blinky = null;

			for (int j = 0; j < daedalus.getPredators().size(); j++) {
				if (daedalus.getPredators().get(j).getClass() == Blinky.class) {
					blinky = (Blinky) daedalus.getPredators().get(j);
				} 
			}

			if (blinky == null) {
				previousChoice = targetedMovement(choices, this.getPosition(), prey.getPosition(), previousChoice);
			}

			previousChoice = targetedMovement(choices, this.getPosition(), blinky.getPosition().add(blinky.getPosition()), previousChoice);
			if (steps == CHASE_DURATION) {
				steps = 0;
				mode = 's';
			} else {
				mode = 'c';
			}
		}

		return previousChoice;
	}

	@Override
	public Animal copy() {
		return new Inky(this);
	}
}
