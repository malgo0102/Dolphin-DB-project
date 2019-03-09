import java.util.*;
import java.io.*;

public class Administrator implements Savable{
   private String name = "ADMINISTRACJA";
//setters
   public void setName(String name){this.name = name;}
//getters
   public String getName(){return name;}
/*// constructor // there is a default constructor in the class, that doesn't do anything
   public Administrator(String name){
      this.name = name;
   }*/
//methods


   @Override
   public void Save() {
       try {
           //Open the file
           File file = new File(getUniqueIdentifier() + ".csv");
           FileWriter fileW = new FileWriter(file);
           BufferedWriter buffW = new BufferedWriter(fileW);

           //Write the age and name, separated by ';'
           buffW.append("name;"+name+"\n");
           
           //Always close your file resources!
           buffW.close();
       }
       catch (Exception e) {
           System.out.println("Error while saving person with unique identifier: " + getUniqueIdentifier());
           e.printStackTrace();
       }
   }
   @Override
   public void Load(String unique_id) {
       try {
           // Open the file
           File file = new File(unique_id + ".csv");
           FileReader fileReader = new FileReader(file);
           BufferedReader reader = new BufferedReader(fileReader);

           //Name is always on first line, split it and assign it to the instance variable (e.g. name;Philip)
           String nameLine = reader.readLine();
           // Parse the integer from the second part of the split line (e.g. age;34)
           name = nameLine.split(";")[1];

           //Always close your file resources!
           reader.close();
       }
       catch (Exception e) {
           System.out.println("Error while loading administrator with unique identifier: " + unique_id);
           e.printStackTrace();
       }
   }
   @Override
    public String getUniqueIdentifier() {
        return name;
    }

}