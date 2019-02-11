//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class ReadFile{

   public static File inputFile;
   public static Scanner toParse;

   public static void readFile(String[] x) throws FileNotFoundException{
   
     if(handleArguments(x)){
      toParse = new Scanner(inputFile);
      LexicalAnalyzer.parseInput(toParse);
      }
   }
   
   public static final String USAGE = "Usage: SURLY input_file.";

   static boolean handleArguments(String[] args){

      if(args.length != 1){
         System.out.println("Wrong number of command line arguments."); //makes sure the user inputs the correct number of args
         System.out.println(USAGE);
         return false;
       }
      
      inputFile = new File(args[0]);
      
      //tests to read the file
      if(!inputFile.canRead()){
         System.out.println("The file " + args[0] + " cannot be opened for input.");
         System.out.println(USAGE);
         return false;
      }
      
      //if file isn't empty, read the file
      if(inputFile.length()==0){
         System.out.println("The file you inputted: "+args[0]+" is empty.");
         System.out.println(USAGE);
         return false;
      }
      
      return true;
   }
   
   

}