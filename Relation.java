//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

import java.util.*;

public class Relation{

   String relationName; 
   LinkedList<Attribute> type;
   LinkedList<Tuple> tuples;

   public Relation(String x, LinkedList<Attribute> items){
      
      relationName=x;
      type = items;
      tuples=new LinkedList<>();   
         
   }
   
   public Relation(){
      relationName = "Catalog";
      type = catalogColumns();
      tuples=new LinkedList<>();
   }
      
   
   void printRelation(){
   
      int numColumns = type.size();
      int numRows = tuples.size();
      int[] columnSizes = getColSizes(numColumns);
      printBorder(columnSizes, numColumns, 1); //print stars
      System.out.println(DBConstants.relation+relationName);
      printBorder(columnSizes, numColumns, 2); //print stripes \m/
      printColumnTitles(numColumns, columnSizes);
      printBorder(columnSizes, numColumns, 2);
      printTuples(numRows, numColumns, columnSizes);
      printBorder(columnSizes, numColumns, 2);

      
   }
   
   //this gets the size of the columns for the relation
   private int[] getColSizes(int numCols){
      
      int[] colWidths = new int[numCols];
      
      for(int b=0; b<numCols; b++){
         String columnTitle = type.get(b).name;
         int lengthColName = columnTitle.length();
         colWidths[b] = type.get(b).size +2;
         if(lengthColName>colWidths[b]){
            colWidths[b]=lengthColName;
         }
      }
   
      return colWidths;
   }
   
   //prints the column titles
   private void printColumnTitles(int x, int[] colWidths){
   
      for(int c=0; c<x; c++){
         String columnTitle = type.get(c).name;
         System.out.print(columnTitle);
         int lengthColName = columnTitle.length();
         padSpace(colWidths[c]-lengthColName); 
         
      }
      System.out.println();   
   }
   
   private void printTuples(int r, int c, int[] w){
   
      for(int j=0; j<r;j++){ //number of tuples in that relation
          for(int k=0; k<c;k++){ //number of columns
             String currentTuple = tuples.get(j).tuple.get(k);
             System.out.print(currentTuple); //print tuple
             int itemLength = currentTuple.length();
             padSpace(w[k]-itemLength); 
          }
          System.out.println();
       }
   
   }
   
   //prints relation specs if there is an error from any input when inserting tuples
   void printRelationSpecs(){
     
      int numColumns = type.size(); //number of columns per relation
      System.out.println("For the relation: \""+relationName+"\"");
      for(int k=0; k<numColumns; k++){
         String colName = type.get(k).name;
         String typeCast = type.get(k).type;
         int maxLen = type.get(k).size;
         System.out.println("Column Name: "+colName+", of type: "+typeCast+", with max length: "+maxLen);
      }
      System.out.println();
  }
  
   private LinkedList<Attribute> catalogColumns(){
   
      LinkedList<Attribute> oneCol = new LinkedList<>();
      String colName = "Names of Relations in DB";
      String cast = "CHAR";
      int len = 20;
      Attribute toAdd = new Attribute(colName, cast, len);
      oneCol.add(toAdd);
      return oneCol;
   
   }

   //pads space so all columns are of the same length
   private void padSpace(int x){
      
      for(int i=0; i<x; i++){
         System.out.print(" ");
      }
      System.out.print(" | ");
   }
   
   //prints the borders to add some flavor, mmmmm
   private void printBorder(int[] colSize, int numCol, int type){
   
      if(type==1){
         for(int m=0; m<numCol; m++){
            int currentColLen = colSize[m];
            for(int n=0; n<currentColLen;n++){
               System.out.print("*");
            }
         }
         
         for(int m=0; m<3*numCol-1;m++){
            System.out.print("*");
         }

      
         System.out.println();
      
      }else{
         for(int m=0; m<numCol; m++){
            int currentColLen = colSize[m];
            for(int n=0; n<currentColLen;n++){
               System.out.print("-");
            }
         }
         
         for(int m=0; m<3*numCol-1;m++){
            System.out.print("-");
         }
         System.out.println();
      }   
   
   }
}