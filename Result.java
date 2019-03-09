import java.util.*;
import java.io.*;
import java.time.*;

public class Result implements Savable{
   enum Discipline{
      Butterfly100m, 
      Butterfly200m,
      Backstroke100m, 
      Breaststroke100m
   }   
   private String type = "result";
   private Discipline discipline;
   private int selectedDisciplineIndex;
   private LocalDate date;
   private double time;
   
   static Scanner sc = new Scanner(System.in);
//setters
   public void setType(String type){this.type = type;}
   public void setDiscipline(Discipline d){this.discipline = d;} 
   public void setSDI(int sdi){this.selectedDisciplineIndex = sdi;}
   public void setDate(LocalDate date){this.date = date;}
   public void setTime(double time){this.time = time;}
  // public void setParticipants(List<EliteMember> d){this.participants = d;}
   //public void setTop5(List<EliteMember> d){this.top5 = d;}
//getters
   public String getType(){return type;}
   public Discipline getDiscipline(){return discipline;}
   public int getSDI(){return selectedDisciplineIndex;}
   public LocalDate getDate(){return date;}
   public double getTime(){return time;}
   //public List<EliteMember> getParticipants(){return participants;}
   //public List<EliteMember> getTop5(){return top5;}
//methods
   public void enterDiscipline(){
      System.out.println("Enter members discipline:\n[1] Butterfly 100m\n[2] Butterfly 200m\n[3] Backstroke 100m,\n[4] Breaststroke 100m\n");
      int n = sc.nextInt();
      if (n == 1){
         setDiscipline(Discipline.Butterfly100m);
      }
      else if (n == 2){
         setDiscipline(Discipline.Butterfly200m);
      }
      else if (n == 3){
         setDiscipline(Discipline.Backstroke100m);
      }
      else if (n == 4){
         setDiscipline(Discipline.Breaststroke100m);
      }
      else{
         System.out.println("Invalid key, try again!\n\n");
         enterDiscipline();
      }
   }
   public void enterDate(){
      System.out.println("Enter date of the result (in format yyyy-mm-dd):");      
      try{
         String str = sc.nextLine();
         LocalDate date = LocalDate.parse(str);
         setDate(date);
      } 
      catch (Exception e){ 
         System.out.println("Invalid date, please check the format");
         enterDate();
      }  
   }
   public void enterTime(){
      System.out.println("Enter swimmers result (time in format 0.00):");
      try{
         double time = sc.nextDouble();
         setTime(time);
      }
      catch(Exception e){ 
         System.out.println("Invalid time, please check the format");
         enterTime();
      }
   }
   public void enterParticipants(){
      System.out.println("Enter participants in this discipline:");
      /*String name = sc.nextLine();                                                   //fix
      getParticipants().add(EliteMember);
      setParticipants(participants);*/
   }
   public void bestTrainingResultAndDate(String x){
         Result result = new Result();
         //EliteMember elite = new EliteMember();
         
         List<Result> results = result.getResults(x);
         Collections.sort(results, (Result r1, Result r2) -> (int)Math.round(r1.getTime()*100 - r2.getTime()*100));
         
         System.out.println("Best training result is:");
         System.out.println(results.get(0).getTime()+" "+results.get(0).getDate());
         /*
         List<Double> times=new ArrayList<Double>();
         List<LocalDate> dates=new ArrayList<LocalDate>();

         for (int i=0; i<result.getResults(x).size(); i++){
            Result currentResult = result.getResults(x).get(i); // on object, not on class 
            
            double time = currentResult.getTime();
            times.add(time);
            
            LocalDate date = currentResult.getDate();
            dates.add(date);
            //System.out.println(date);
            
            System.out.println(times.get(i)); //print all times
            System.out.println(dates.get(i)+"\n");
         } 
         System.out.println("Best training result is:");
         
         Object obj = Collections.min(times);
         System.out.println(obj);
         elite.getMembers(); 
         //search engine through elite member
        */  
   }
   public void showTop5(){
      /*String name = sc.nextLine();                                                   //fix
      getParticipants().add(EliteMember);
      setParticipants(participants);*/
   }
  /* public List<Result> getAllResults(){
      List<Result> results = new ArrayList<Result>();
      
      File[] files = new File(".").listFiles();
      //If this pathname does not denote a directory, then listFiles() returns null. 
      
      for (File file : files) {
          if (file.getName().startsWith("result")) {
              Result r = new Result();
              String filename = file.getName();
              r.Load(filename);
              results.add(r);
          }
      }
      //System.out.println(members);
      return results;                                       //look through csv's + add type like (member, result)
   }*/
  /* public void showResults(List<Result> results){  
      System.out.println("List of results:");
      for (int i=0; i<results.size(); i++){
         Result currentResult = results.get(i); // on object, not on class 
         System.out.println(currentResult.getDiscipline()+": "+currentResult.getTime()+" "+/*currentResult.getMember()+" "+currentResult.getDate());
      }
   }*/
   public void showResults(String x){
         Result result= new Result();
         for (int i=0; i<result.getResults(x).size(); i++){
            Result currentResult = result.getResults(x).get(i); // on object, not on class 
            String unique_id = currentResult.getType()+"_"+currentResult.getDiscipline()+"_"+currentResult.getDate()+"_"+currentResult.getTime();
            try{ 
               Scanner input = new Scanner(new File(unique_id + ".csv"));
               while (input.hasNextLine()){
                  System.out.println(input.nextLine());
               }
            System.out.println("\n"); 
            }
            catch (Exception e) { 
               System.out.println("Error while loading result with unique identifier: " + unique_id +"\n\n");
               e.printStackTrace();
            } 
         }
   }
   public List<Result> getResults(String x){
      List<Result> results = new ArrayList<Result>();
      
      File[] files = new File(".").listFiles();

      for (File file : files) {
         if (file.getName().startsWith(x)) {
            Result r = new Result();
             String filename = file.getName();
             r.Load(filename);
             results.add(r);
          }         
      }  
   return results;
   }

   @Override
   public void Save() {
       try {
           //Open the file
           File file = new File(getUniqueIdentifier() + ".csv");
           FileWriter fileW = new FileWriter(file);
           BufferedWriter buffW = new BufferedWriter(fileW);

          // for the lists: Save the inhabitants -> One per line looking like: inhabitant;Philip Loeventoft_31
        /*  for (int i=0;i<results.size();i++){
              Result result = results.get(i);
              //Make sure that each inhabitant is saved to disk
              result.Save();

              //Save the line containing the unique id for later loading
              buffW.append("result;"+result.getUniqueIdentifier()+"\n");
          }*/

           //Write the age and name, separated by ';'
           buffW.append("type;"+type+"\n");
           buffW.append("discipline;"+discipline+"\n");
           buffW.append("date;"+date+"\n");
           buffW.append("result;"+time+"\n");
           
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
           type = typeLine.split(";")[1];
           
           //Name is always on first line, split it and assign it to the instance variable (e.g. name;Philip)
           String disciplineLine = reader.readLine();
           discipline = Discipline.valueOf(disciplineLine.split(";")[1]);
           // Parse the integer from the second part of the split line (e.g. age;34)
          // selectedDisciplineIndex = Integer.parseInt(disciplineLine.split(";")[1]);

           String dateLine = reader.readLine();
           date = LocalDate.parse(dateLine.split(";")[1]); 
                     
           String timeLine = reader.readLine();
           time = Double.parseDouble(timeLine.split(";")[1]);
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
        return type + "_" + discipline + "_" + date + "_" + time; // result_kleofans_30 ???????????????????????
    }
 

}