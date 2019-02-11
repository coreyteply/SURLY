//Corey Teply
//CS330 - Winter 2019 - Group 9
//SURLY

import java.util.*;

public class PrintCurrent{
   
   //prints a relation and all of its tuples if it exists
   public static void printRelation(int a){
   
      int numColumns = Catalog.tables.get(a).type.size();
      int numRows = Catalog.tables.get(a).tuples.size();
      int[] columnSizes = new int[numColumns]; //this will determine the size of each column
      if(numRows==0){
         System.out.println("There are no tuples in the relation: \""+Catalog.tables.get(a).relationName+"\"");
         System.out.println("The relation specs are:");
         for(int k=0; k<numColumns; k++){
            String colName = Catalog.tables.get(a).type.get(k).name;
            String typeCast = Catalog.tables.get(a).type.get(k).type;
            int maxLen = Catalog.tables.get(a).type.get(k).size;
            System.out.println("Column Name: "+colName+", of type: "+typeCast+", with max length: "+maxLen);
         }
            return;


      }else{
         for(int b=0; b<numColumns; b++){
            String columnTitle = Catalog.tables.get(a).type.get(b).name;
            int lengthColName = columnTitle.length();
            columnSizes[b] = Catalog.tables.get(a).type.get(b).size +2;
            if(lengthColName>columnSizes[b]){
               columnSizes[b]=lengthColName;
            }
         }
      }
      
      printBorder(columnSizes, numColumns, 1); //print stars
      
      //print relation name
      System.out.println("For the relation: "+Catalog.tables.get(a).relationName);
      
      printBorder(columnSizes, numColumns, 2); //print stripes \m/
      
      
      
      //print column titles
      for(int c=0; c<numColumns; c++){
         String columnTitle = Catalog.tables.get(a).type.get(c).name;
         System.out.print(columnTitle);
         int lengthColName = columnTitle.length();
         padSpace(columnSizes[c]-lengthColName); 
         
      }
      System.out.println();
      printBorder(columnSizes, numColumns, 2);
      
      //print tuples
      for(int j=0; j<numRows;j++){ //number of tuples in that relation
          for(int k=0; k<numColumns;k++){ //number of columns
             String currentTuple = Catalog.tables.get(a).tuples.get(j).tuple.get(k);
             System.out.print(currentTuple); //print tuple
             int itemLength = currentTuple.length();
             padSpace(columnSizes[k]-itemLength); 
          }
          System.out.println();
       }
       
       printBorder(columnSizes, numColumns, 2);
   }
   
   //prints the catalog if that is what is called
   public static void printCatalog(){
   
      for(int i=0; i<Catalog.tables.size(); i++){ //for all of the relations in the table
         int numColumns = Catalog.tables.get(i).type.size(); //number of columns per relation
         String relationName = Catalog.tables.get(i).relationName;
         System.out.println("For the relation: "+relationName);
         for(int k=0; k<numColumns; k++){
            String colName = Catalog.tables.get(i).type.get(k).name;
            String typeCast = Catalog.tables.get(i).type.get(k).type;
            int maxLen = Catalog.tables.get(i).type.get(k).size;
            System.out.println("Column Name: "+colName+", of type: "+typeCast+", with max length: "+maxLen);
         }
         System.out.println();
      }
   
   }
   
   //prints relation specs if there is an error from any input
   public static void printRelationSpecs(int i){
     
      int numColumns = Catalog.tables.get(i).type.size(); //number of columns per relation
      String relationName = Catalog.tables.get(i).relationName;
      System.out.println("For the relation: \""+relationName+"\"");
      for(int k=0; k<numColumns; k++){
         String colName = Catalog.tables.get(i).type.get(k).name;
         String typeCast = Catalog.tables.get(i).type.get(k).type;
         int maxLen = Catalog.tables.get(i).type.get(k).size;
         System.out.println("Column Name: "+colName+", of type: "+typeCast+", with max length: "+maxLen);
      }
      System.out.println();
  }

   
   //pads space so all columns are of the same length
   public static void padSpace(int x){
      
      for(int i=0; i<x; i++){
         System.out.print(" ");
      }
      System.out.print(" | ");
   }
   
   //prints the borders to add some flavor, mmmmm
   public static void printBorder(int[] colSize, int numCol, int type){
   
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