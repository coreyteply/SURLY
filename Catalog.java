//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

public class Catalog{

   public static LinkedList<Relation> tables=new LinkedList<>(); //LL of relations that are being stored
   
   //stores a relation if it hasn't been created yet
   public static void storeRelation(String x){
   
      Relation currentRelation = ParseRelation.parseRelation(x);
      int checkForRelation = returnRelationIndex(currentRelation.relationName);
      if(checkForRelation == -1){
         tables.add(currentRelation);
      }else{
         System.out.println("The relation: \""+currentRelation.relationName+"\" already exists. Cannot add again");
         System.out.println();
      }
   
   }
   
   //inserts a tuple into the correct relation
   public static void storeInsert(String x){
   
      Insert currentTuptoAdd = Insert.parseInsert(x);
      int currentRelation = returnRelationIndex(currentTuptoAdd.insert_2_curr_table);
      if(currentRelation>=0){ //makes sure the relation that you want to add to exists
         if(checkRelationSpecs(currentTuptoAdd.curr_tuple, currentRelation)){//makes sure the tuple meets all the specs of that relation
            tables.get(currentRelation).tuples.add(currentTuptoAdd.curr_tuple);//adds the tupls
         }else{
            System.out.println("The current tuple you want to insert doesn't meet the required specs:");
            PrintCurrent.printRelationSpecs(currentRelation);
         }
      }else{
         System.out.println("this relation: \""+currentTuptoAdd.insert_2_curr_table+"\" does not exist");
      }
   
   }
   
   //prints a relation with all of it's tuples, or the whole catalog if that's what is called
   public static void printRelation(LinkedList<String> x){
   
      int numPrints = x.size();
      for(int h=0; h < numPrints; h++){
      
         String currentPrint = x.get(h);
         if(currentPrint.contains(";")){
            currentPrint = LexicalAnalyzer.cleanTokenFinal(currentPrint);
         }
         
         int currentRelation = returnRelationIndex(currentPrint);
         
         if(currentRelation>=0){
            PrintCurrent.printRelation(currentRelation);
            System.out.println();
         }else if(currentPrint.equals("CATALOG")){
            PrintCurrent.printCatalog();
            System.out.println();
         }else{
            System.out.println("The relation: \""+currentPrint+"\" does not exist");
            System.out.println();
         }
      }
   
   }
   
   
   //returns the index of the relation you are trying to find
   public static int returnRelationIndex(String relationName){
   
      int i = 0;
      while(i<tables.size()){
         if(tables.get(i).relationName.equals(relationName)){
            return i; //return the relation indes
         }else{
            i ++;
         }
      }
      
      //if nothing is found, return -1
      return -1;
    }   
    
   public static boolean checkRelationSpecs(Tuple tup, int relationIndex){
      
      int numCols = tables.get(relationIndex).type.size(); //get number of columns
      int numElements = tup.tuple.size(); //get number of elements in the tuple
      int count = 0; //count to make sure all spec requirements are met
      if(numCols == numElements){ //if the size of the tuple fits into the number of columns needed to make tuple
         for(int i=0; i<numCols; i++){
            int allowedLength = tables.get(relationIndex).type.get(i).size; //get allowed size length
            int elementLength = tup.tuple.get(i).length(); //get length of element going into that particular column
            if(tup.tuple.get(i).contains("'")){
               elementLength=elementLength-2;
            }
            if(elementLength <= allowedLength){ //make sure the element isn't too long
               if(checkType(tup.tuple.get(i), i, relationIndex)){ //finally, check to make sure it's of the proper type
                  //System.out.println("you made it here"); 
                  count++; //if everything checks out, that element is good to be added
               }else{
                  return false;
               }
            }else{
               System.out.println("element is too long: \""+tup.tuple.get(i)+"\" with length: "+elementLength);
               return false;
            }
         }
      }   
      
      if(count==numElements){
         return true;
      }
      
      return false;   
   }
   
   public static boolean checkType(String currentElement, int colIndex, int relationIndex){
   
      String typeInput = tables.get(relationIndex).type.get(colIndex).type;
      if(typeInput.equals("CHAR")){
         return true; //returns true because everything is initially stored as a string
      }else if(typeInput.equals("NUM")){
         //try and convert all nums to ints, and if that doesn't work, throw an exception
         try{
            int currentNum = Integer.parseInt(currentElement);
         }catch(Exception e){
            System.out.println("Current element: \""+currentElement+"\" is not of type: "+typeInput);
            System.out.println(e);
            return false;
         }
         
         return true;
      }   
            
   
   
      return false;
   }
   
   public static void destroy_relation(LinkedList<String> x){
      
      int numDestroys = x.size();
      for(int i=0; i<numDestroys; i++){
         
         String currentDestroy = x.get(i);
         if(currentDestroy.contains(";")){
            currentDestroy = LexicalAnalyzer.cleanTokenFinal(currentDestroy);
         }
         
         int relationIndex = returnRelationIndex(currentDestroy);
         if(relationIndex==-1){
            System.out.println("The relation: "+x.get(i)+" has already been deleted and does not exist.");
         }else{
            tables.remove(relationIndex);
         }
      }    
       
   }
   
   public static void deleteRelation(LinkedList<String> x){
   
   
      int numRelations = x.size();
      for(int i=0; i<numRelations; i++){
         String currentDelete = x.get(i);
         if(currentDelete.contains(";")){
            currentDelete = LexicalAnalyzer.cleanTokenFinal(currentDelete);
         }
         
         int relationIndex = returnRelationIndex(currentDelete);
         int currentDeleteSize = tables.get(relationIndex).tuples.size();
         for(int j=0; j<currentDeleteSize; j++){
            tables.get(relationIndex).tuples.remove();
         }
      }
         
   
   }
      

}