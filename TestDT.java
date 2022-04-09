import java.io.IOException; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.PrintWriter; 

public class TestDT {
  public static void main(String[] args) {
    DecisionTree yes = new DecisionTree("Cat");
    DecisionTree no = new DecisionTree("Dog"); 
    DecisionTree yes1 = new DecisionTree("Lizard");
    DecisionTree no1 = new DecisionTree("Shark");
    DecisionTree yes2 = new DecisionTree("Bunny");
    DecisionTree no2 = new DecisionTree("Elephant");

    DecisionTree YES = new DecisionTree("Is it a feline?", yes, no);
    DecisionTree NO = new DecisionTree("Is it a reptile?", yes1, no1); 
    DecisionTree NOAGAIN = new DecisionTree("Does it hop?", yes2, no2);

    DecisionTree TEST = new DecisionTree("Is it a mammal", YES, NO); 

    
    // try{
      
    //   FileWriter fileWriter = new FileWriter("tree.txt");
    //   PrintWriter out = new PrintWriter(fileWriter);
    //   TEST.breadthFirst(TEST, " ", out, fileWriter); 
    // }
    // catch(IOException e){
    //   System.out.println("IOException"); 
    // }
    String stringTree = "";
    stringTree = TEST.breadthFirstString(stringTree, TEST, "");
    //TEST.readToFile("tree.txt"); 
    //System.out.println(TEST.breadthFirst(stringTree, TEST, ""));
    //TEST.animalPrintWriter(); 

    //DecisionTree copy = new DecisionTree(tree); 
    try{
      DecisionTree.readFromFile("AnimalTree.txt");
    }
    catch(IOException e){
      System.out.println("IOException");
    }
  }
}