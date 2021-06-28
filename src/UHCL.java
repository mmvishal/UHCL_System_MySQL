import java.util.*;
import java.io.*;


public class UHCL
{
    /*public static ArrayList<Course> allCourses = new ArrayList<Course>();
    public static ArrayList<User> allUsers = new ArrayList<User>();
    public static ArrayList<Faculty> allFaculty = new ArrayList<Faculty>();
    public static ArrayList<Student> allStudents = new ArrayList<Student>();*/

    /**
     * @param args
     */
    public static void main(String args[])
    {
    	char eService_choice;
    	char choice = ' ';

        String login_id;
        String password;



        String[] user;
        boolean login_success = false;

        Scanner s = new Scanner(System.in);

        while(choice != 'x')
        {
        	System.out.print("\n");
            System.out.print("Go Hawks!\n");
            System.out.print("1 : e-Service\n");
            System.out.print("2 : Blackboard\n");
            System.out.print("x : Leave\n");

            choice = s.next().charAt(0);

            //e-service
            if(choice == '1')
            {
                System.out.print("\nPlease enter your login ID : \n");
                login_id = s.next();
                System.out.print("Please enter your password : \n");
                password = s.next();

                user = User.loginUser(login_id, password);
                
                if(user[1].equals("F"))
                {
                	login_success = true;
                	while(login_success)
                    {
                        System.out.println("\nWelcome to UHCL eService : ");
                        System.out.print("v : view my courses\n");
                        System.out.print("x : Logout\n");

                        eService_choice = s.next().charAt(0);

                        if(eService_choice == 'v')
                        {
                            Course.courses_registered(user[0]);
                        }
                        else if(eService_choice == 'x')
                        {
                            login_success = false;
                        }
                    }
                }
                
                else if(user[1].equals("S"))
                {
                	login_success = true;
                	while(login_success)
                    {
                		System.out.println("\nWelcome to UHCL eService :");
                        System.out.print("v : view my courses\n");
                        System.out.print("r : register a course\n");
                        System.out.print("x : Logout\n");

                        eService_choice = s.next().charAt(0);

                        if(eService_choice == 'v')
                        {
                            Student.print_registered_courses(user[0]);
                        }
                
                        else if(eService_choice == 'r')
                        {	
                        	Course.register_course(user[0]);
                        }	
                    
                        else if(eService_choice == 'x')
                        {
                             login_success = false;
                        }
                     }
                }
                else
                {
                    System.out.println("\nAlert: Incorrect Login ID or Password!");
                }
            }
        
        	
            //BlackBoard
            else if(choice == '2')
            {
            	System.out.print("\nPlease enter your login ID : \n");
                login_id = s.next();
                System.out.print("Please enter your password : \n");
                password = s.next();

                user = User.loginUser(login_id, password);

                if(user[1].equals("F"))
                {
                	Faculty.faculty_blackboard(user[0]);
                }
                else if(user[1].equals("S"))
                {
                     Student.student_blackboard(user[0]);
                	
                }
                else
                {
                    System.out.println("\nAlert: Incorrect Login ID or Password!");
                }
            }
        }
     }
}