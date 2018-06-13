/**
 * @author Ricardo Ferreira Ribeiro (250798)
 * @author Clarise Estelle Fleurimont (246866)
 * @date 14 déc. 2015
 */
/**
 * 
 */
package ch.epfl.maze.physical;

import java.util.ArrayList;
import java.util.Random;

import ch.epfl.maze.util.Direction;

public interface RandomMovement {

	/**
	 * Methode creating a randomised movement used by the Mouse, Predators and Preys
	 * @param choices
	 * @param previousChoice
	 * @return
	 */
	public static Direction randomMove(Direction[] choices, Direction previousChoice) {
		int nbrChoices = choices.length;

		if (nbrChoices == 0){
			return Direction.NONE;
		}
		else if(previousChoice == null){
			previousChoice =  RandomMovement.randomDirection(choices);
			return previousChoice;
		}
		else{
			if(nbrChoices == 1){
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
					if(!choices[i].isOpposite(previousChoice)){
						possibility.add(choices[i]);
					}
				}
				Direction[] poss = new Direction[possibility.size()];
				possibility.toArray(poss);
				previousChoice =  RandomMovement.randomDirection(poss);

				return previousChoice;
			}
		}		
	}

	/**
	 * Creates a random Direction
	 * @param choices
	 * @return
	 */
	public static Direction randomDirection(Direction[] choices){
		int nbrChoices = choices.length;
		Random rand = new Random();
		int val = rand.nextInt(nbrChoices);
		Direction dir = choices[val];
		return dir;
	}
}
