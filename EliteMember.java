import java.util.*;
import java.io.*;

public class EliteMember extends Member implements Savable{
   private String type = "elite";
   private String coach;
   //private String[] discipline = {"Butterfly 100m", "Butterfly 200m", "Backstroke 100m", "Breaststroke 100m"};
   private int selectedDisciplineIndex;
   private List<Result> results = new ArrayList<>();

   static Scanner sc = new Scanner(System.in);  
//setters
   public String getType(){return type;}
   public void setType(String type){this.type = type;}
   public void setCoach(String c){this.coach = c;}
   //public void setDiscipline(String [] d){this.discipline = d;} 
   public void setSDI(int sdi){this.selectedDisciplineIndex = sdi;}
   public void setResults(List<Result> r){this.results = r;}
//getters
   public String getCoach(){return coach;}
  // public String[] getDiscipline(){return discipline;}
   public int getSDI(){return selectedDisciplineIndex;}
   public List<Result> getResults(){return results;}
//methods
   public void enterCoach(){
      System.out.println("Enter name of the coach:");
      String coach = sc.nextLine();
      setCoach(coach);
   }
   /*public int enterDiscipline(){
      System.out.println("Enter discipline(s) swimmer is active in:");
         for(int i = 0; i< discipline.length; i++){
            System.out.println("["+(i+1)+"] "+discipline[i]);
         }
         int m = sc.nextInt();
         setSDI(m);
         return getSDI();
   }*/
   public void enterResult(){
      Result r = new Result();
      r.enterDiscipline();
      r.enterDate();
      r.enterTime();
      r.Save();
      results.add(r);
      /*String name = sc.nextLine();                                                   //fix
      getDisciplines().add(Discipline);
      setDisciplines(disciplines);*/
   }
   public void enterAnotherResult(){
      System.out.println("Does Elite member swim in another discipline?\n[1] = Yes\n[2] = No");
      int m = sc.nextInt();       
      if (m == 1){
         enterResult();
         enterAnotherResult();
      }
      else if (m == 2){
      }
   }
   @Override
   public void Save() {
       try {
           //Open the file
           File file = new File(getUniqueIdentifier() + ".csv");
           FileWriter fileW = new FileWriter(file);
           BufferedWriter buffW = new BufferedWriter(fileW);

           //Write the age and name, separated by ';'
           buffW.append("type;"+type+"\n");
           buffW.append("name;"+name+"\n");
           buffW.append("age;"+age+"\n");
           buffW.append("fee;"+fee+"\n");
           buffW.append("outstanding fee;"+toPay+"\n");
           buffW.append("status;"+status+"\n");
           buffW.append("seniority;"+seniority+"\n");
           buffW.append("coach;"+coach+"\n");
           //buffW.append("discipline;"+selectedDisciplineIndex+"\n");

          // for the lists: Save the inhabitants -> One per line looking like: inhabitant;Philip Loeventoft_31
          for (int i=0;i<results.size();i++){
              Result result = results.get(i);
              //Make sure that each inhabitant is saved to disk
              result.Save();

              //Save the line containing the unique id for later loading
              buffW.append("result;"+result.getUniqueIdentifier()+"\n");
          }
           
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
           File file = new File(unique_id);
           FileReader fileReader = new FileReader(file);
           BufferedReader reader = new BufferedReader(fileReader);

           String typeLine = reader.readLine();
           this.type = typeLine.split(";")[1];
           
           //Name is always on first line, split it and assign it to the instance variable (e.g. age;34)
           String nameLine = reader.readLine();
           // Parse the integer from the second part of the split line (e.g. name;Philip)
           this.name = nameLine.split(";")[1];     /*when you write Member.xxxx then you are trying to access the static property/method xxxx on Member
                                                   when you write this.xxxx you are accessing the instance variable xxxx on the current instance
                                                   and that one can be inherited*/

           //We know that persons are simple so the second line is always age
           String ageLine = reader.readLine();
           this.age = Integer.parseInt(ageLine.split(";")[1]);

           //String statusLine = reader.readLine();
           //this.status = statusLine.split(";")[1];

           //String seniorityLine = reader.readLine();
           //this.seniority = seniorityLine.split(";")[1];          

           String feeLine = reader.readLine();
           this.fee = Double.parseDouble(feeLine.split(";")[1]);

           String toPayLine = reader.readLine();
           this.toPay = Double.parseDouble(toPayLine.split(";")[1]);

           //Always close your file resources!
           reader.close();
       }
       catch (Exception e) {
           System.out.println("Error while loading person with unique identifier: " + unique_id);
           e.printStackTrace();
       }
   }
   @Override
    public String getUniqueIdentifier() {
        return this.type + "_" + this.name + "_" + this.age;
    }
 

}