package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Red ghost from the Pac-Man game, chases directly its target.
 * 
 */

public class Blinky extends Predator {
	
	/* Choix précédent */
	private Direction previousChoice;

	/* Nombre de pas */
	private int steps;

	/* Mode de déplacement ('s' pour SCATTER / 'c' CHASE) */
	private char mode;

	/* Vecteur de position initiale de Blinky */
	private final Vector2D posInitiale;

	/**
	 * Constructs a Blinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Blinky in the labyrinth
	 */

	public Blinky(Vector2D position) {
		super(position);
		previousChoice = position.toDirection();
		steps = 0;
		mode = 'c';
		posInitiale = position;
	}

	/**
	 * Construct a copy of the previous Blinky
	 * @param autre
	 * 				previous Blinky
	 */

	public Blinky(Blinky autre){
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
			previousChoice = targetedMovement(choices, this.getPosition(), prey.getPosition(), previousChoice);
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
		return new Blinky(this);
	}
}
