package ch.epfl.maze.physical.zoo;

import java.util.ArrayList;

import ch.epfl.maze.physical.Animal;
import ch.epfl.maze.physical.RandomMovement;
import ch.epfl.maze.util.Direction;
import ch.epfl.maze.util.Vector2D;

/**
 * Panda A.I. that implements Trémeaux's Algorithm.
 * 
 */
public class Panda extends Animal implements RandomMovement {

	/* First color's "way" */
	ArrayList<Vector2D> couleur1 = new ArrayList<>();

	/* Second color's "way" */
	ArrayList<Vector2D> couleur2 = new ArrayList<>();

	/* Previous Choice */
	Direction previousChoice;

	/**
	 * Constructs a panda with a starting position.
	 * 
	 * @param position
	 *            Starting position of the panda in the labyrinth
	 */

	public Panda(Vector2D position) {
		super(position);
	}

	/**
	 * Second constructor creating a copy of the previous Panda
	 * 
	 * @param autre
	 * 			previous Panda
	 */

	public Panda(Panda autre){
		super(autre);
	}

	/**
	 * Moves according to <i>Trémeaux's Algorithm</i>: when the panda
	 * moves, it will mark the ground at most two times (with two different
	 * colors). It will prefer taking the least marked paths. Special cases
	 * have to be handled, especially when the panda is at an intersection.
	 */

	@Override
	public Direction move(Direction[] choices) {
		ArrayList<Direction> possibility = new ArrayList<>();
		ArrayList<Direction> possCol1 = new ArrayList<>();
		ArrayList<Direction> possCol2 = new ArrayList<>();
		int nbrChoices = choices.length;
		Direction dir;

		if(previousChoice == null){
			previousChoice =  RandomMovement.randomDirection(choices);
			couleur1.add(getPosition().addDirectionTo(previousChoice));
			return previousChoice;
		}
		else{
			if(nbrChoices == 0){
				return Direction.NONE;
			}
			else if(nbrChoices == 1){
				couleur2.add(getPosition().addDirectionTo(choices[0]));
				couleur1.remove(getPosition().addDirectionTo(choices[0]));
				previousChoice = choices[0];
				return choices[0];
			}
			else if (nbrChoices == 2){
				for (int i = 0; i < nbrChoices; i++) {
					if(!choices[i].isOpposite(previousChoice)){
						possibility.add(choices[i]);
					}
				}
				Direction[] poss = new Direction[possibility.size()];
				possibility.toArray(poss);
				dir =  RandomMovement.randomDirection(poss);
				possibility.clear();

				if(!couleur1.contains(getPosition().addDirectionTo(dir)) && !couleur2.contains(getPosition().addDirectionTo(dir))){
					couleur1.add(getPosition().addDirectionTo(dir));
					previousChoice = dir;
					return dir;
				}
				else if(!couleur2.contains(getPosition().addDirectionTo(dir))){
					couleur2.add(getPosition().addDirectionTo(dir));
					couleur1.remove(getPosition().addDirectionTo(dir));
					previousChoice = dir;
					return dir;
				}
				else{
					previousChoice = dir;
					return dir;
				}	
			}
			else{
				for (int i = 0; i < nbrChoices; i++) {
					if(!couleur1.contains(getPosition().addDirectionTo(choices[i])) && !couleur2.contains(getPosition().addDirectionTo(choices[i])) && !choices[i].isOpposite(previousChoice)){
						possibility.add(choices[i]);
					}
					else if(couleur1.contains(getPosition().addDirectionTo(choices[i])) && !choices[i].isOpposite(previousChoice)){
						possCol1.add(choices[i]);
					}
					else if(couleur2.contains(getPosition().addDirectionTo(choices[i])) && !choices[i].isOpposite(previousChoice)){
						possCol2.add(choices[i]);
					}
				}
				if(possibility.size() > 0){
					Direction[] poss = new Direction[possibility.size()];
					possibility.toArray(poss);
					previousChoice =  RandomMovement.randomDirection(poss);
					possibility.clear();
					couleur1.add(getPosition().addDirectionTo(previousChoice));
					return previousChoice;
				}
				else if(possCol1.size() > 0){
					if(possCol1.size() == choices.length-1){
						dir = previousChoice.reverse();
						previousChoice = dir;
						possCol1.clear();
						couleur1.remove(getPosition().addDirectionTo(dir));
						couleur2.add(getPosition().addDirectionTo(dir));
						return dir;	
					}
					else{
						possCol1.remove(getPosition().addDirectionTo(previousChoice));
						Direction[] poss = new Direction[possCol1.size()];
						possCol1.toArray(poss);
						previousChoice =  RandomMovement.randomDirection(poss);
						possCol1.clear();
						couleur1.remove(getPosition().addDirectionTo(previousChoice));
						couleur2.add(getPosition().addDirectionTo(previousChoice));
						return previousChoice;
					}
				}
				else{
					Direction[] poss = new Direction[possCol2.size()];
					possCol2.toArray(poss);
					previousChoice =  RandomMovement.randomDirection(poss);
					possCol2.clear();
					return previousChoice;
				}
			}
		}
	}

	@Override
	public Animal copy() {
		return new Panda(this);
	}
}
