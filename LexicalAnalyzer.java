//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class LexicalAnalyzer{

   public static Scanner input;
   public static Scanner currentLine;
   public static String currentToken;
   public static int currentParse = 0; // 0 = scanNext, 1=handleRelation, 2=handleInsert, 3=handlePrint, 4=handleDestroy, 5=handleDelete
   public static StringBuilder relation;
   public static StringBuilder insert;
   public static LinkedList<String> printx;
   public static LinkedList<String> destroy;
   public static LinkedList<String> deletex;

    public static void parseInput(Scanner inputFile) throws NoSuchElementException{
   
      input = inputFile; //make local copy of this instance in this class
      currentToken = null;
      
      //get to the first token in the file, then start scanning
      while(nullToken()){
         currentLine = new Scanner(input.nextLine());
         if(currentLine.hasNext()){
            currentToken = currentLine.next();
            scanNext();
         }
      }
   }
   
   public static boolean nullToken(){
      if(currentToken==null){
         return true;
         }
         return false;
   }
   
   //gets you your next token
   public static void updateNext(){
      
      if(currentLine.hasNext()){
         currentToken = currentLine.next();
      }else if(input.hasNextLine() && !(currentLine.hasNext())){
         currentLine = new Scanner(input.nextLine());
         updateNext();
      }else{
         endFile();
      }
   }  
         
   //looks through the file, and goes into the desired methods based on the current token
   public static void scanNext(){
   
      if(currentToken.contains("/*")){
         currentParse=0;
         updateNext();
         handleComment();
      }else if(currentToken.equals("RELATION")){
         relation = new StringBuilder();
         handleRelation();
      }else if(currentToken.equals("INSERT")){
         insert = new StringBuilder();
         handleInsert();
      }else if(currentToken.equals("PRINT")){
         printx = new LinkedList<>();
         updateNext();
         handlePrint();
      }else if(currentToken.equals("DESTROY")){
         destroy = new LinkedList<>();
         updateNext();
         handleDestroy();
      }else if(currentToken.equals("DELETE")){
         deletex = new LinkedList<>();
         updateNext();
         handleDelete(); 
      }else if(fileDone()){
         endFile();
      }else{
         updateNext();
         scanNext();
     }
   }
   
   //this just goes over everything that's within the comment, doesn't save anything
   public static void handleComment(){
      
      if(currentToken.equals("*/")){
         updateNext();
         
         //then sends you back to the right method you were in
         if(currentParse==0){
            scanNext();
         }else if(currentParse==1){
            handleRelation();
         }else if(currentParse==2){
            handleInsert();
         }else if(currentParse==3){
            handlePrint();
         }else if(currentParse==4){
            handleDestroy();
         }else if(currentParse==5){
            handleDelete();
         }   
      }else if(currentToken.contains("*/") && currentToken.contains(";")){
         if(currentParse==0){
            scanNext();
         }else if(currentParse==1){
            handleRelation();
         }else if(currentParse==2){
            handleInsert();
         }else if(currentParse==3){
            handlePrint();
         }else if(currentParse==4){
            handleDestroy();
         }else if(currentParse==5){
            handleDelete();
         }
      }else{
         updateNext();
         handleComment();
      }
   }
   
   public static void handleRelation(){
   
      //cleans up the token if need be
      if(dopedToken()){
         currentToken = cleanToken();
         handleRelation();
      }else{
      
         if(currentToken.contains(";")){ //if it has a semi colon, then it sends it to the catalog class to be stored
            relation.append(currentToken);
            String newRelation = relation.toString();
            Catalog.storeRelation(newRelation);
            updateNext();
            scanNext();
         }else if(currentToken.contains("/*")){//ignores everything within the comment
            currentParse=1;
            handleComment();
         }else{
            relation.append(currentToken);
            relation.append(" ");
            updateNext();
            handleRelation();
         }
      }
   }
   
   public static void handleInsert(){
      
         if(currentToken.contains(";")){
            insert.append(currentToken);
            String newInsert = insert.toString();
            Catalog.storeInsert(newInsert);
            updateNext();
            scanNext();
         }else if(currentToken.contains("/*")){
            currentParse=2;
            handleComment();
         }else{
            insert.append(currentToken);
            insert.append(" ");
            updateNext();
            handleInsert();
         }
      
   }   
   
   public static void handlePrint(){
      
      if(dopedToken()){
         currentToken = cleanToken();
         handlePrint();
      }else{
         if(currentToken.contains(";")){
            printx.add(currentToken);
            Catalog.printRelation(printx);
            updateNext();
            scanNext();
         }else if(currentToken.contains("/*")){
            currentParse=3;
            handleComment();
         }else{
            printx.add(currentToken);
            updateNext();
            handlePrint();
         }
      }   
   }
   
   public static void handleDestroy(){
    
	  if(dopedToken()){
         currentToken = cleanToken();
         handleDestroy();
      }else{
         if(currentToken.contains(";")){
            destroy.add(currentToken);
            Catalog.destroy_relation(destroy);
            updateNext();
            scanNext();
         }else if(currentToken.contains("/*")){
            currentParse=4;
            handleComment();
         }else{
            destroy.add(currentToken);
            updateNext();
            handleDestroy();
         }
      }
   }


   public static void handleDelete(){
    
	  if(dopedToken()){
         currentToken = cleanToken();
         handleDelete();
      }else{
         if(currentToken.contains(";")){
            deletex.add(currentToken);
            Catalog.deleteRelation(deletex);
            updateNext();
            scanNext();
         }else if(currentToken.contains("/*")){
            currentParse=5;
            handleComment();
         }else{
            deletex.add(currentToken);
            updateNext();
            handleDelete();
         }
      }
   }

   
   public static boolean dopedToken(){
   
      if(currentToken.contains(",") || currentToken.contains("(") || currentToken.contains(")") || currentToken.contains("'") ||
               currentToken.contains("<") || currentToken.contains(">")  || currentToken.contains("=")){
         return true;
      }else{
         return false;
      }
      
   }
   
   //cleans up a token if it has any sort of puncuation on it
   public static String cleanToken(){
   
      int length = currentToken.length();
      int count=0;
      StringBuilder newToken = new StringBuilder();
      while(count<length){
         if(currentToken.charAt(count)==',' || currentToken.charAt(count)=='(' || currentToken.charAt(count)==')' || 
               /*currentToken.charAt(count)== ' ' ||*/ currentToken.charAt(count)=='<' || currentToken.charAt(count)=='>' || 
                 currentToken.charAt(count)== '='){
            count++;
         }else{
            newToken.append(currentToken.charAt(count));
            count++;
         }
      }
      
      return newToken.toString();
   }
   
   //gets rid of the semi colon
   public static String cleanTokenFinal(String x){
      
      int length = x.length();
      int count=0;
      StringBuilder cleanBoi = new StringBuilder();
      while(count<length){
         if(x.charAt(count)==';'){
            count++;
         }else{
            cleanBoi.append(x.charAt(count));
            count++;
         }
      }
         
      return cleanBoi.toString();
   }

      
         
   public static void endFile(){
      //System.out.println("you are done reading dis file");
   }      
   
   //checks to see if the file is done
   public static boolean fileDone(){
      if(!(input.hasNextLine())){
         return true;
      }else{
         return false;
      }
   }   


}