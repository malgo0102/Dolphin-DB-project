import java.io.*;
import java.util.*;
import java.text.*;
import java.nio.file.*;

public class Main{
   static Scanner sc = new Scanner(System.in);
   //String name = sc.nextLine(); 
   public static void main(String[] args){
   welcome();
   //System.out.println("Enter name: ");
   } 
   public static void welcome(){
      System.out.println("-----------Welcome-----------");
      System.out.println("Please choose from the following: (Enter number only)");
      System.out.println(" [1] = Register a new Member");
      System.out.println(" [2] = Search / View existing Members");
      //System.out.println(" [3] = Modify existing Members");
      System.out.println(" [4] = View results");                  //fixx
      System.out.println(" [5] = View Top 5 Elite Members");
      System.out.println(" [6] = Show outstanding payments");
      System.out.println(" [7] = Exit program");
      int m = sc.nextInt();
         
      if (m == 1){
         //
         System.out.println("Please choose from the following: (Enter number only)");
         System.out.println(" [1] = Register a new regular Member");
         System.out.println(" [2] = Register a new Elite Member");
         int n = sc.nextInt();
            
         if (n == 1){
            Member member = new Member();
            member.registerMember();
            member.Save();
         }
         else if (n == 2){
            EliteMember eliteM = new EliteMember();
            registerEliteMember(eliteM);          
            eliteM.Save();
         }
         else {
            System.out.println("Invalid key, try again!\n\n");
            welcome();
         }
      }
      else if (m == 2){
         System.out.println("[1] = Search based on name and age");
         System.out.println("[2] = Show all members");  
         
         int n = sc.nextInt();
         if (n == 1){
            Member member = new Member();
            member.readFile();
         }
         else if (n == 2){
            Member member = new Member();
      
            for (int i=0; i<member.getMembers().size(); i++){
               Member currentMember = member.getMembers().get(i); // on object, not on class 
               String unique_id = currentMember.getType()+"_"+currentMember.getName()+"_"+currentMember.getAge();
               try{ 
                  Scanner input = new Scanner(new File(unique_id + ".csv"));
                  while (input.hasNextLine()){
                     System.out.println(input.nextLine());
                  }
               System.out.println("\n"); 
               }
               catch (Exception e) { 
                  System.out.println("Error while loading member with unique identifier: " + unique_id +"\n\n");
                  e.printStackTrace();
               } 
            }
         }
      }
      //else if (m == 3){
         //
      //}
      else if (m == 4){
         System.out.println("Show results in:\n[1] = Butterfly 100m\n[2] = Butterfly 200m\n[3] = Backstroke 100m\n[4] = Breaststroke 100m\n[5] = All results ----- not done"); 
         String x = "";
         Result r = new Result();
         int n = sc.nextInt();
         if (n == 1){
            x = "result_Butterfly100m";
            r.bestTrainingResultAndDate(x);
            //r.showResults(x);
         }
         else if (n == 2){
            x = "result_Butterfly200m";
            r.bestTrainingResultAndDate(x);
            //r.showResults(x);
         }
         else if (n == 3){
            x = "result_Backstroke100m";
            r.bestTrainingResultAndDate(x);
            //r.showResults(x);
         }
         else if (n == 4){
            r.bestTrainingResultAndDate(x);
            x = "result_Breaststroke100m";
            //r.showResults(x);
         }
        // else if (n == 5){
          //  x = "result";
            //r.bestTrainingResultAndDate(x);
            //r.showResults(x);
        // }
      }
      else if (m == 5){
         //      
      }
      else if (m == 6){
        /* ArrayList<Member> members = new ArrayList<Member>();
         for(int i = 0; i < members.size(); ++i){
            Member member = new Member();
            members.add(member);
         }  */
         Member member = new Member();
         member.showOutstandingPayment(member.getMembers());
      }
      else if (m == 7){
         System.out.println("Bye-bye!");
      }
      else {
         System.out.println("Invalid key, try again!\n\n");
         welcome();
      }
   } 
   public static void registerEliteMember(EliteMember eliteM){
      eliteM.registerMember();
      eliteM.enterCoach();
      //eliteM.enterDiscipline();
      eliteM.enterResult();
      eliteM.enterAnotherResult();
   }
}