package ch.epfl.maze.physical;

import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Predator that kills a prey when they meet with each other in the labyrinth.
 * 
 */

abstract public class Predator extends Animal implements RandomMovement {
	private Direction previousChoice;

	/* constants relative to the Pac-Man game */
	public static final int SCATTER_DURATION = 14;
	public static final int CHASE_DURATION = 40;

	/**
	 * Constructs a predator with a specified position.
	 * 
	 * @param position
	 *            Position of the predator in the labyrinth
	 */

	public Predator(Vector2D position) {
		super(position);
		previousChoice = null;	
	}

	/**
	 * Second constructor which creates a copy of the previous predator
	 * 
	 * @param autre
	 * 				previous predator
	 */

	public Predator(Predator autre){
		super(autre);
	}

	/**
	 * Chooses a prey that the ghosts will all chase until they catch it
	 * 
	 * @param daedalus
	 * @return common choses prey
	 */

	protected Prey commonChosenPrey(Daedalus daedalus){
		return daedalus.getPreys().get(0);
	}

	/**
	 * Creates a targeted movement used by the ghost (mainly in the scatter mode)
	 * 
	 * @param choices
	 * @param posAct
	 * @param posCible
	 * @param prev
	 * @return dir
	 * 			new Direction targeting the posIn
	 */

	public Direction targetedMovement(Direction[] choices, Vector2D posAct, Vector2D posCible, Direction prev){
		int nbrChoice = choices.length;
		Vector2D OB = posCible;
		Vector2D OA = posAct;
		double distance = Double.POSITIVE_INFINITY;
		Direction dir = Direction.NONE;

		if (nbrChoice == 0) {
			return dir;
		}
		if (nbrChoice == 1) {
			previousChoice = choices[0];
			return previousChoice;
		}
		for (int i = 0; i < nbrChoice; i++) {
			OA = posAct.addDirectionTo(choices[i]);
			if(!choices[i].isOpposite(prev) && OB.sub(OA).dist() < distance){
				distance = OB.sub(OA).dist();
				dir = choices[i];
			}
		}
		previousChoice = dir;

		return dir;
	}

	/**
	 * Moves according to a <i>random walk</i>, used while not hunting in a
	 * {@code MazeSimulation}.
	 * 
	 */

	@Override
	public final Direction move(Direction[] choices) {
		previousChoice = RandomMovement.randomMove(choices, previousChoice);
		return previousChoice;
	}

	/**
	 * Retrieves the next direction of the animal, by selecting one choice among
	 * the ones available from its position.
	 * <p>
	 * In this variation, the animal knows the world entirely. It can therefore
	 * use the position of other animals in the daedalus to hunt more
	 * effectively.
	 * 
	 * @param choices
	 *            The choices left to the animal at its current position (see
	 *            {@link ch.epfl.maze.physical.World#getChoices(Vector2D)
	 *            World.getChoices(Vector2D)})
	 * @param daedalus
	 *            The world in which the animal moves
	 * @return The next direction of the animal, chosen in {@code choices}
	 */

	abstract public Direction move(Direction[] choices, Daedalus daedalus);
}
