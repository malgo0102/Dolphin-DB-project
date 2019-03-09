import java.util.*;
import java.io.*;

public class Member implements Savable{
   enum Status{
      Active, 
      Passive
   }
   enum Seniority{
      Junior, 
      Senior
   }
   protected String type = "member";
   protected String name;
   protected int age;
   protected double fee;
   protected double toPay;
   protected Status status;
   protected Seniority seniority;
 
   static Scanner sc = new Scanner(System.in);
//setters
   public void setType(String type){this.type = type;}
   public void setName(String name){this.name = name;}
   public void setAge(int a){this.age = a;} 
   public void setFee(double f){this.fee = f;}
   public void setToPay(double t){this.toPay = t;}
   public void setStatus(Status st){this.status = st;}
   public void setSeniority(Seniority s){this.seniority = s;}
//getters
   public String getType(){return type;}
   public String getName(){return name;}
   public int getAge(){return age;}
   public double getFee(){return fee;}
   public double getToPay(){return toPay;}
   public Status getStatus(){return status;}
   public Seniority getSeniority(){return seniority;}
//methods
   public String enterName(){
      System.out.println("Enter name:");
      String name = sc.nextLine();
      this.name = name; //accessing from the instance
      return name;
   }
   public void enterAge(){
      System.out.println("Enter age:");
      int age = sc.nextInt();
      setAge(age); // accessing the public interface
   } 
   /*For active members, the fee for junior swimmers (below 18 years) is dkk 1000 a year, 
   for senior swimmers (18 years plus) it is dkk 1600 a year. 
   Members older than 60 years are granted a discount of 25% on the senior rate. 
   For passive membership, the fee is dkk 500 a year.*/
   public void countFee(){    
      if (getStatus() == Status.Active){
         if (getAge() < 18){
            setFee(1000.0);
            System.out.println("Current yearly payment is: "+getFee());
         }
         else if (getAge() > 60){
            setFee(1600.0*0.75);
            System.out.println("Current yearly payment is: "+getFee());
         }
         else{
            setFee(1600.0);
            System.out.println("Current yearly payment is: "+getFee());
         }  
      }
      else{
         setFee(500.0);
         System.out.println("Current yearly payment is: "+getFee());
      }
   }
   public double outstandingFee(){
      System.out.println("Customer payed (enter amount):");
      double payed = sc.nextDouble();
      toPay = getFee()-2*getFee()+payed;
      System.out.println("Current account balance: "+ toPay);
      return toPay;
   }
   public void enterStatus(){
      System.out.println("Enter member status:\n[1] for Active\n[2] for Passive");
      int n = sc.nextInt();
      if (n == 1){
         setStatus(Status.Active);
      }
      else if (n == 2){
         setStatus(Status.Passive);
      }
      else{
         System.out.println("Invalid key, try again!\n\n");
         enterStatus();
      }
   }
   public void registerSeniority(){
      if (getAge() < 18){
         setSeniority(Seniority.Junior);
      }
      else{
         setSeniority(Seniority.Senior);
      } 
   }
   
   //outstanding payments                                                     fix
   public void showOutstandingPayment(List<Member> members){  
      System.out.println("Oustanding payments:");
      for (int i=0; i<members.size(); i++){
         Member currentMember = members.get(i); // on object, not on class 
         if(currentMember.toPay<0){
            System.out.println(currentMember.getName()+": "+currentMember.getToPay());
         }
      }
   }
   
   public void registerMember(){ 
      this.enterName();
      this.enterAge();
      this.enterStatus();
      this.registerSeniority(); ///hHHhahahahahahahahhaha
      this.countFee();
      this.outstandingFee();
   }

   public List<Member> getMembers(){
      List<Member> members = new ArrayList<Member>();
      
      File[] files = new File(".").listFiles();
      //If this pathname does not denote a directory, then listFiles() returns null. 
      
      for (File file : files) {
          if (file.getName().startsWith("member")) {
              Member m = new Member();
              String filename = file.getName();
              m.Load(filename);
              members.add(m);
          }
          if (file.getName().startsWith("elite")) {
              EliteMember e = new EliteMember();
              String filename = file.getName();
              e.Load(filename);
              members.add(e);
          }
      }
      //System.out.println(members);
      return members;                                       //look through csv's + add type like (member, result)
   }

   public void readFile(){
      System.out.println("Are you searching for regular or elite members:");
      System.out.println(" [1] = Search for a regular Member");
      System.out.println(" [2] = Search for an Elite Member");
     
      int m = sc.nextInt();
      sc.nextLine(); 
     
      if (m == 1){
         String type = "member";
      }
      else if (m == 2){
         String type = "elite";
      }
      String unique_id = type + "_" + name + "_" + age;
      System.out.println("Enter name:");
      String name = sc.nextLine();
      System.out.println("Enter age:");
      int age = sc.nextInt();
      try{
         Scanner input = new Scanner(new File(unique_id + ".csv"));
         while (input.hasNextLine()){
            System.out.println(input.nextLine());
         }
      }
      catch (Exception e) { 
         System.out.println("Error while loading member with unique identifier: " + unique_id +"\n\n");
         e.printStackTrace();
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
           buffW.append("type;"+type+"\n");                    // hard code "member"???
           buffW.append("name;"+name+"\n");
           buffW.append("age;"+age+"\n");
           buffW.append("fee;"+fee+"\n");
           buffW.append("outstanding fee;"+toPay+"\n");
           buffW.append("status;"+status+"\n");
           buffW.append("seniority;"+seniority+"\n");           
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
           String nameLine = reader.readLine();
           // Parse the integer from the second part of the split line (e.g. age;34)
           name = nameLine.split(";")[1];

           //We know that persons are simple so the second line is always age
           String ageLine = reader.readLine();
           age = Integer.parseInt(ageLine.split(";")[1]);
           
           //String statusLine = reader.readLine();
           //status = statusLine.split(";")[1];

           //String seniorityLine = reader.readLine();
           //seniority = seniorityLine.split(";")[1];          

           String feeLine = reader.readLine();
           fee = Double.parseDouble(feeLine.split(";")[1]);

           String toPayLine = reader.readLine();
           toPay = Double.parseDouble(toPayLine.split(";")[1]);

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
        return this.type + "_" + name + "_" + age;
    }
   
}





 /*  //modify fees
   public void changeJuniorFee(){
      System.out.println("Set new junior fee:");
      double f = sc.nextDouble();
      setFee(f);
      System.out.println("New price: "+f+" was set.");
   }
   public void changeSeniorFee(){
      System.out.println("Set new senior fee:");
      double f = sc.nextDouble();
      setFee(f);
      System.out.println("New price: "+f+" was set.");
   }
   public void changeSixtyPlusFee(){
      System.out.println("Set new sixty plus fee:");
      double fee = sc.nextDouble();
      System.out.println("Set new sixty plus discount:");
      double discount = sc.nextDouble();
      setFee(fee*discount);                                    //how to count that????????????????????????????
      System.out.println("New price: "+fee+" was set with "+discount+" % discount.");
   }
   public void changePassiveFee(){
      System.out.println("Set new passive member fee:");
      double f = sc.nextDouble();
      setFee(f);
      System.out.println("New price: "+f+" was set.");
   }*/