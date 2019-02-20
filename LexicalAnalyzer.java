//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class LexicalAnalyzer{

   private DataBase DB;
   private Scanner input;
   private Scanner currentLine;
   private String currentToken;
   private int currentParse = 0; // 0 = scanNext, 1=handleRelation, 2=handleInsert, 3=handlePrint, 4=handleDestroy, 5=handleDelete
   private StringBuilder relation;
   private StringBuilder insert;
   private LinkedList<String> printx;
   private LinkedList<String> destroy;
   private LinkedList<String> deletex;

    public void parseInput(Scanner inputFile) throws NoSuchElementException{
   
      DB = new DataBase();
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
   
   private boolean nullToken(){
      if(currentToken==null){
         return true;
         }
         return false;
   }
   
   //gets you your next token
   private void updateNext(){
      
      if(currentLine.hasNext()){
         currentToken = currentLine.next();
        // System.out.println(currentToken);
      }else if(input.hasNextLine() && !(currentLine.hasNext())){
         currentLine = new Scanner(input.nextLine());
         updateNext();
      }else{
         endFile();
      }
   }  
         
   //looks through the file, and goes into the desired methods based on the current token
   private void scanNext(){
   
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
   private void handleComment(){
      
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
   
   private void handleRelation(){
   
      //cleans up the token if need be
      if(dopedToken()){
         currentToken = cleanToken();
         handleRelation();
      }else{
      
         if(currentToken.contains(";")){ //if it has a semi colon, then it sends it to the catalog class to be stored
            relation.append(currentToken);
            String newRelation = relation.toString();
            DB.storeRelation(newRelation);
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
   
   private void handleInsert(){
      
         if(currentToken.contains(";")){
            insert.append(currentToken);
            String newInsert = insert.toString();
            DB.storeInsert(newInsert);
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
   
   private void handlePrint(){
      
      if(dopedToken()){
         currentToken = cleanToken();
         handlePrint();
      }else{
         if(currentToken.contains(";")){
            colonoscopy();
            printx.add(currentToken);
            DB.printRelation(printx);
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
   
   private void handleDestroy(){
    
	  if(dopedToken()){
         currentToken = cleanToken();
         handleDestroy();
      }else{
         if(currentToken.contains(";")){
            colonoscopy();
            destroy.add(currentToken);
            DB.destroy_relation(destroy);
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


   private void handleDelete(){
    
	  if(dopedToken()){
         currentToken = cleanToken();
         handleDelete();
      }else{
         if(currentToken.contains(";")){
            colonoscopy();
            deletex.add(currentToken);
            DB.deleteRelation(deletex);
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

   
   private boolean dopedToken(){
   
      if(currentToken.contains(",") || currentToken.contains("(") || currentToken.contains(")") || currentToken.contains("'") ||
               currentToken.contains("<") || currentToken.contains(">")  || currentToken.contains("=")){
         return true;
      }else{
         return false;
      }
      
   }
   
   //cleans up a token if it has any sort of puncuation on it
   private String cleanToken(){
   
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
   private void colonoscopy(){
   
      int length = currentToken.length();
      int i=0;
      StringBuilder toReturn = new StringBuilder();
      while(i<length){
         if(currentToken.charAt(i)==';'){
            i++;
         }else{
            toReturn.append(currentToken.charAt(i));
            i++;
         }
      }
      currentToken = toReturn.toString();
   
   }
      
         
   private void endFile(){
      //System.out.println("you are done reading dis file");
   }      
   
   //checks to see if the file is done
   private boolean fileDone(){
      if(!(input.hasNextLine())){
         return true;
      }else{
         return false;
      }
   }   


}