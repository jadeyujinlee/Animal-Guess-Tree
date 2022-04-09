import java.util.Scanner;
import java.io.IOException; 

/**
* @class AnimalGame that plays a 20 questions style animal guessing game
*/
public class AnimalGuess{
  
  /**
  * @main runs methods to play the guessing game
  * @param args 
  * @throws IOException 
  */
  public static void main(String[] args) throws IOException{
    String fileName = args[0]; 
    boolean playAgain = true;
    boolean correctInput = false;
    Scanner sc = new Scanner(System.in);
    try{
      DecisionTree animals = DecisionTree.readFromFile(args[0]);
      String guess = "";
    
    //**
    /* Starter questions and responses hardcoded before we read from file.
    */
    //DecisionTree animals = new DecisionTree();
    // DecisionTree animals = new DecisionTree("Is your animal a canine?");
    // DecisionTree dog = new DecisionTree("Dog");
    // animals.setLeft(dog);
    // DecisionTree mouse = new DecisionTree("Mouse");
    // animals.setRight(mouse);
    // String guess = "Mouse";
 
    
    //**
    /* @String path keeps track of how to access the current 
    location in the tree
    */
      String path = "";
      
      //** 
      /* while loop every time the user says yes to play another round
      */
      while(playAgain){  
        System.out.println("Think of an animal.");
        System.out.println("I'll try to guess it.");
  
        // call a method that asks all the questions stored in the tree
        // it will ask for responses
        // these responses will determine the path it takes through the tree
        path = askQuestions(animals, guess, sc);
      
        while(correctInput == false){
          String guessedIt = sc.nextLine();
          if(guessedIt.equals("yes") || guessedIt.equals("Yes") || guessedIt.equals("y") || guessedIt.equals("Y") || guessedIt.equals("ye")){
            correctInput = true;
            
            System.out.println("Yay, I guessed it!");       
          }
          else if(guessedIt.equals("no") || guessedIt.equals("No") || guessedIt.equals("n") || guessedIt.equals("N")){
            
            correctInput = true;
            System.out.println("I got it wrong.");
            System.out.println("Please help me to learn.");
            System.out.println("What was your animal?");     
            String userAnimal = sc.nextLine();
            System.out.println("Type a yes or no question that would distinguish between " + animals.followPath(path).getData() + " and " + userAnimal);
            String question = sc.nextLine();
            System.out.println("Would you answer yes to this question for the " + userAnimal + "?");
            String isYes = sc.nextLine();
  
            // the method below will add the information we just gathered to the tree at a specified location
            addToTree(question, isYes, userAnimal, guess, animals, path);
  
            //System.out.println("TEST: "+DecisionTree.inorderStringDT(animals));
          
          }
          else{
            System.out.println("Try inputting something like yes or no");
            correctInput = false;
          }
        }
        correctInput = false;
  
        System.out.println("Play again?");
        String stringTree = ""; 
        stringTree = animals.breadthFirstString(stringTree, animals, "");
        animals.writeToFile(stringTree, fileName);
      
  
        while(correctInput == false){
          String play = sc.nextLine();
          if(play.equals("yes") || play.equals("Yes") || play.equals("y") || play.equals("Y") || play.equals("ye")){
            correctInput = true;
            playAgain = true;// thus we will loop again
          }
          else if(play.equals("no") || play.equals("No") || play.equals("n") || play.equals("N")){
            correctInput = true;
            playAgain = false;
            System.out.println("Okay, bye!"); 
          }
          else{
            System.out.println("Try inputting something like yes or no");
            correctInput = false;
          }
        }  
        correctInput = false;  
      } 
      sc.close();
      }
    catch(IOException e){
      System.out.println("IOException");
    }
    

  }
 
  /**
  * method that adds information to the decision tree at specified location
  *
  * @param question a question user provides to distinguish 2 animals
  * @param isYes string yes or no that tells us if the user's question is true or false for the animal they were thinking of
  * @param userAnimal the animal the user was thinking of
  * @param guessAnimal the animal the program incorrectly guessed
  * @param animals the big decision tree of everything
  * @param path a string comprised of Y's and N's, denoting which direction to take as yoy go down the tree to reach the specified location
  * @return String 
  */
  public static void addToTree(String question, String isYes, String userAnimal, String guessAnimal, DecisionTree animals, String path){
 
    // left means YES, right means NO
    guessAnimal = animals.followPath(path).getData();
    DecisionTree leftLeaf;
    DecisionTree rightLeaf;
 

    if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){//the user's question is true for the user's animal
      leftLeaf = new DecisionTree(userAnimal);
      rightLeaf = new DecisionTree(guessAnimal);
    }
    else{ //the user's question is not true for their animal
      leftLeaf = new DecisionTree(guessAnimal);
      rightLeaf = new DecisionTree(userAnimal);
    }
 
    //creates a branch that holds the question and the yes and no answers
    DecisionTree branch = new DecisionTree(question);
    branch.setLeft(leftLeaf);
    branch.setRight(rightLeaf);
 
    //adds the branch at location specified by path
    animals.addAtLocation(path, animals, branch);
  }
 
  /**
  * method that travels through the decision tree, asking the question stored in each node
  * ilicits a yes or no response from user for each question
  * this yes or no determines which direction to go as we continue to travel down the tree
  * when the leaves of the tree are reached (aka the actual guesses, not questions, ask "is your animal a [leaf]?"
  * @param animals the big decision tree of questions/answers
  * @param guess will be the animal the computer guesses
  * @param sc scanner to read input
  * @return path the path that tells us which directions to take as we go down the list. We will use this to find the location of where we will add a new branch
  * @return path 
  */
  public static String askQuestions(DecisionTree animals, String guess, Scanner sc){
    boolean correctInput2 = false;
    
    String isYes;
 
    String path = "";
 
    // asks the first question stored in the tree and get a response
    System.out.println(animals.getData());
    while(correctInput2 == false){
      isYes = sc.nextLine();
      if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){
        correctInput2 = true;
        path = path + "Y";    
      }
      else if(isYes.equals("no") || isYes.equals("No") || isYes.equals("n") || isYes.equals("N")){
        correctInput2 = true;
        path = path + "N";  
      }
      else{
        System.out.println("Try inputting something like yes or no");
        correctInput2 = false;
      }
    }
    correctInput2 = false;
   
    // now ask the rest of the questions, until we hit a leaf on the tree
    while(animals.followPath(path).isBranch()){
      System.out.println(animals.followPath(path).getData());
      while(correctInput2 == false){
        isYes = sc.nextLine();      
        if(isYes.equals("yes") || isYes.equals("Yes") || isYes.equals("y") || isYes.equals("Y") || isYes.equals("ye")){
          correctInput2 = true;
          path = path + "Y";
        }
        else if(isYes.equals("no") || isYes.equals("No") || isYes.equals("n") || isYes.equals("N")){
          correctInput2 = true;
          path = path + "N";
        }
        else{
          System.out.println("Try inputting something like yes or no");
          correctInput2 = false;
        }
      } 
      correctInput2 = false;   
    }
 
    guess = animals.followPath(path).getData();
    System.out.println("Is your animal a " + guess + "?");
     
    return path;
  }
  
}