package ch.epfl.maze.physical.pacman;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.Daedalus;
import ch.epfl.maze.physical.Predator;
import ch.epfl.maze.physical.Prey;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Pink ghost from the Pac-Man game, targets 4 squares in front of its target.
 * 
 */

public class Pinky extends Predator {

	/* Starting position */
	private final Vector2D startPos;

	/* Number of steps */
	private int steps;

	/* Previous choice */
	private Direction previousChoice;

	/* "Walking" mode ('c' for CHASE / 's' for SCATTER) */
	private char mode;

	/* Prey's position(s) */
	private Vector2D preyPos;
	private Vector2D lastPreyPos;

	/**
	 * Constructs a Pinky with a starting position.
	 * 
	 * @param position
	 *            Starting position of Pinky in the labyrinth
	 */

	public Pinky(Vector2D position) {
		super(position);
		startPos = position;
		steps = 0;
		mode = 'c';
		previousChoice = position.toDirection();
		
	}

	/**
	 * Second constructor creating a copy of the previous Pinky
	 * 
	 * @param autre
	 * 				previous Pinky
	 */

	public Pinky(Pinky autre){
		super(autre);
		startPos = autre.getPosition();
		steps = 0;
		previousChoice = autre.getPosition().toDirection();
		mode = 'c'; 
	}

	@Override
	public Direction move(Direction[] choices, Daedalus daedalus) {
		++steps;
		Vector2D cible = getCible(daedalus);

		if(steps == SCATTER_DURATION+1 && mode == 's'){
			steps = 0;
			mode ='c';
		}
		else if(steps == CHASE_DURATION+1 && mode == 'c'){
			steps = 0;
			mode = 's';
		}
		if(daedalus.getPreys().size()==0){
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
				Vector2D OB = cible;
				Vector2D OA = this.getPosition();
				Direction dir = Direction.NONE;
				dir = targetedMovement(choices, OA, OB, previousChoice);
				previousChoice = dir;
				return dir;
			}
		}
		else{
			previousChoice = targetedMovement(choices,this.getPosition(),startPos, previousChoice);
			return previousChoice;
		}
	}

	/**
	 * Chooses the common prey and calculates the Euclidian addition of 
	 * the previous and actual position of the prey and multiplies it by four.
	 * 
	 * @param daedalus
	 * @return
	 */

	public Vector2D getCible(Daedalus daedalus){
		Prey prey = commonChosenPrey(daedalus);
		Vector2D cible;
		if(lastPreyPos == null){
			preyPos = prey.getPosition();
			lastPreyPos = preyPos;
		}
		else{
			lastPreyPos = preyPos;
			preyPos = prey.getPosition();
		}
		cible = preyPos.sub(lastPreyPos);
		cible = preyPos.add(cible.mul(4));
		return cible;
	}

	/**
	 * Gets the starting position of Pinky
	 * 
	 * @return startPos
	 */

	public Vector2D getStartPos(){
		return startPos;
	}

	@Override
	public Animal copy() {
		return new Pinky(this);
	}
}
