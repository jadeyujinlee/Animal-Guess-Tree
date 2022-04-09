import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileReader; 
import java.io.BufferedReader; 
import java.util.Scanner; 
 
/**
*  Implements a binary decision tree
*
*  @author Jade Lee
*  @version
*
*/
public class DecisionTree extends BinaryTree<String> {
 
  /** leaf constructor */
  public DecisionTree(String s) {
    super(s);
  }
 
  /**branch constructor */
  public DecisionTree(String s, DecisionTree yes, DecisionTree no){
    super(s, yes, no);
  }
 
  /** deep copy constructor */
  public DecisionTree(DecisionTree tree){
    super(tree.getData());
    this.setLeft(tree.getLeft());
    this.setRight(tree.getRight());
  }
 
  /** @override */
  public void setLeft(BinaryTree<String> left) {
    if (left instanceof DecisionTree) {
      super.setLeft(left);
    } else {
      throw new UnsupportedOperationException();
    }
  }
 
  /** @override */
  public DecisionTree getLeft() {
    return (DecisionTree)super.getLeft();
  }
 
  /** @override */
  public void setRight(BinaryTree<String> right) {
    if (right instanceof DecisionTree) {
      super.setRight(right);
    } else {
      throw new UnsupportedOperationException();
    }
  }
 
  /** @override */
  public DecisionTree getRight() {
    return (DecisionTree)super.getRight();
  }
 
  /**
  * method that traces a specified path through the tree
  * @param path Y/N representation of path of yes's and no's
  * @return answer 
  */
  public DecisionTree followPath(String path){
    char[] pathArray = path.toCharArray();
    DecisionTree answer;
    if(pathArray[0] == 'Y'){
      answer = getLeft();
    }
    else{
      answer = getRight();
    }
    for(int i = 1; i < pathArray.length; i++){
      //System.out.println("test");
      if(pathArray[i] == 'Y'){
        answer = answer.getLeft();
      }
      else{
        answer = answer.getRight();
      }
    }
    return answer;
  }

  /**
  * @method to add new branches and or leaves to the DecisionTree
  * @param path 
  * @param animals 
  * @param branch 
  */
  public void addAtLocation(String path, DecisionTree animals, DecisionTree branch){
    char[] pathArray = path.toCharArray();
    DecisionTree location = animals;
    for(int i = 0; i < pathArray.length; i++){
      if(i == pathArray.length - 1){
        if(pathArray[i] == 'Y'){
          location.setLeft(branch);
        }
        else{
          location.setRight(branch);
        }
      }
      else if(pathArray[i] == 'Y'){
        location = location.getLeft();         
      }
      else{
        location = location.getRight();
      }
    }
  }
  
  /**
  * @method to print postOrder of the Strings 
  * @param t 
  */
  public static String inorderStringDT(DecisionTree t) {
    if (t==null) {
      return "";
    } 
    else{
      return "("+inorderString(t.getLeft())+" "+t.getData()+" "+inorderString(t.getRight())+")";
    }
  }

  /**
  * @method breadthFirstString makes Strings into tree into breadthFirst order, not unlike a toString() method
  * @param bfString 
  * @param tree 
  * @param path 
  * @return bfString 
  */
  public String breadthFirstString(String bfString, DecisionTree tree, String path){
    if(path == ""){
      bfString = tree.getData() + "\n";
    }
    if(tree.isLeaf()){
      //should we have put something here for good form? 
    }
    else{
      bfString = bfString + path + "Y" + " " + tree.getLeft().getData() + "\n";
      bfString = bfString + path + "N" + " " + tree.getRight().getData() + "\n";
      bfString = breadthFirstString(bfString, tree.getLeft(), path + "Y");
      bfString = breadthFirstString(bfString, tree.getRight(), path + "N");
    }
    return bfString;
  }    
  
  /**
  * @method writeToFile to overwrite user inputs into file 
  * @param tree
  * @param fileName
  */
  public void writeToFile(String tree, String fileName){
    try{
      FileWriter fileWriter = new FileWriter(fileName);
      PrintWriter out = new PrintWriter (fileWriter); 
      out.print(tree); 
      out.close(); 
    }
    catch(IOException e){
      System.out.println("IOException");
    }
  }
  
  /**
  * @method readFromFile reads files using a scanner 
  * @param fileName
  * uses a delimiter of " " to separate the path "navigation" from the node of the String. 
  * @return fileTree 
  */
  public static DecisionTree readFromFile(String fileName) throws IOException{
    
    BufferedReader file = new BufferedReader(new FileReader(fileName));
    Scanner sc = new Scanner(file); 
    DecisionTree fileTree = new DecisionTree(sc.nextLine());
    while(sc.hasNext()){
      String [] lineArray = sc.nextLine().split(" "); 
      String path = lineArray[0]; 
      String data = ""; 
      for(int i = 1; i < lineArray.length; i++){
        data = data + lineArray[i] + " ";
      }
      DecisionTree branch = new DecisionTree(data);
      fileTree.addAtLocation(path, fileTree, branch); 
    }
    sc.close(); 
    return fileTree;
  } 
}
