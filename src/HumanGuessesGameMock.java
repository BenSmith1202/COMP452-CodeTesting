//A Mock version of the game used for testing.
//Allows manually injecting the target number
public class HumanGuessesGameMock extends HumanGuessesGame {


    // Constructor for testing - allows injecting a specific target value
    HumanGuessesGameMock(int target){
        super();
        this.target = target;
    }


}