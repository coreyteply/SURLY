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

}