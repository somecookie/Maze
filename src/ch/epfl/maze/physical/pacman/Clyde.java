package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Orange ghost from the Pac-Man game, alternates between direct chase if far
 * from its target and SCATTER if close.
 * 
 */

public class Clyde extends Predator {

	/* Clyde's starting position */
	private final Vector2D startPos;

	/* Clyde's number of steps */
	private int steps;

	/* Clyde's previous choice */
	private Direction previousChoice;

	/* Clyde's mode of "walk" */
	private char mode;

	/**
	 * Constructs a Clyde with a starting position.
	 * 
	 * @param position
	 *            Starting position of Clyde in the labyrinth
	 */

	public Clyde(Vector2D position) {
		super(position);
		startPos = position;
		steps = 0;
		previousChoice = position.toDirection();
		mode = 'c';
	}

	/**
	 * Second constructor creating a copy of the previous Clyde
	 * 
	 * @param autre
	 * 			previous Clyde
	 */

	public Clyde(Clyde autre){
		super(autre);
		startPos = autre.getPosition();
		steps = 0;
		previousChoice = autre.getPosition().toDirection();
		mode = 'c'; 
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		steps++;
		Prey prey = commonChosenPrey(daedalus);

		if(steps == SCATTER_DURATION){
			steps = 0;
			mode ='c';
		}
		else if(steps == CHASE_DURATION){
			steps = 0;
			mode = 's';
		}
		if(daedalus.isSolved()){
			return super.move(choices);
		}

		else if(mode == 'c'){
			int nbrChoice = choices.length;
			if(nbrChoice == 1){
				previousChoice = choices[0];
				return choices[0];
			}
			else if(nbrChoice == 2){
				if(!choices[0].isOpposite(previousChoice)){
					previousChoice = choices[0];
					return choices[0];
				}
				else{
					previousChoice = choices[1];
					return choices[1];
				}
			}
			else{
				Vector2D OB = prey.getPosition();
				Vector2D OA = this.getPosition();
				Direction dir = Direction.NONE;
				if(OB.sub(OA).dist() <= 4){
					previousChoice = targetedMovement(choices, getPosition(), startPos, previousChoice);
					return previousChoice;
				}
				else{
					dir = targetedMovement(choices, OA, OB, previousChoice);
					previousChoice = dir;
					return dir;
				}
			}
		}
		else{
			previousChoice = targetedMovement(choices, this.getPosition(), startPos, previousChoice);
			return previousChoice;
		}
	}

	@Override
	public Animal copy() {
		return new Clyde(this);
	}
}
