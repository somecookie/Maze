package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomMovement;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Hamster A.I. that remembers the previous choice it has made and the dead ends
 * it has already met.
 * 
 */

public class Hamster extends Animal implements RandomMovement{

	/* Table of dead ends */
	private Vector2D[] interMed = new Vector2D[1];

	/* Dead ends */
	private ArrayList<Vector2D> impasses = new ArrayList<>();

	/* Previous choice of the Hamster */
	private Direction previousChoice;

	/**
	 * Constructs a hamster with a starting position.
	 * 
	 * @param position
	 *            Starting position of the hamster in the labyrinth
	 */

	public Hamster(Vector2D position) {
		super(position);
		interMed[0] = null;
		previousChoice = null;	
	}

	/**
	 * Second constructor creating a copy of the previous Hamster
	 * 
	 * @param autre
	 * 				previous Hamster
	 */

	public Hamster(Hamster autre){
		super(autre);
	}

	/**
	 * Moves without retracing directly its steps and by avoiding the dead-ends
	 * it learns during its journey.
	 */

	@Override
	public Direction move(Direction[] choices) {
		int nbrChoices = choices.length;
		
		if (nbrChoices == 0){
			return Direction.NONE;
		}
		else if(previousChoice == null){
			previousChoice = RandomMovement.randomDirection(choices);
			if(nbrChoices > 2){
				interMed[0] = getPosition().addDirectionTo(previousChoice);
			}
			return previousChoice;
		}
		else{
			if(nbrChoices == 1){
				impasses.add(interMed[0]);
				interMed[0] = null;
				previousChoice = choices[0];
				return choices[0];
			}
			else if(nbrChoices == 2)
			{
				if(!choices[0].isOpposite(previousChoice)){
					previousChoice = choices[0];
					return choices[0];
				}
				else{
					previousChoice = choices[1];;
					return choices[1];
				}
			}
			else{
				ArrayList<Direction> possibility = new ArrayList<>();

				for (int i = 0; i < nbrChoices; i++) {
					if(!impasses.contains(getPosition().addDirectionTo(choices[i])) && !choices[i].isOpposite(previousChoice)){
						possibility.add(choices[i]);
					}
				}
				Direction[] choices2 = new Direction[possibility.size()];
				possibility.toArray(choices2);

				if(choices2.length == 0 && impasses.contains(getPosition().addDirectionTo(previousChoice.reverse()))){
					return Direction.NONE;
				}
				else if(choices2.length == 0){
					impasses.add(interMed[0]);
					interMed[0] = null;
					previousChoice = previousChoice.reverse();
					return previousChoice;
				}
				else{
					previousChoice = RandomMovement.randomDirection(choices);
					interMed[0] = getPosition().addDirectionTo(previousChoice);
					return previousChoice;
				}
			}	
		}
	}

	@Override
	public Animal copy() {
		return new Hamster(this);
	}
}
