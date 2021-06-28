import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;


public class Student extends User
{
    public ArrayList<Course> registered_courses = new ArrayList<Course>();
    
    final static String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/mallam5664?useSSL=false";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;
	static ResultSet resultSet1 = null;

	static Scanner s = new Scanner(System.in);
	
    public Student(String name, String user_major, String user_name, String password)
    {
        super(name, user_major, user_name, password, 'S');
    }


    public static void print_registered_courses(String student_name)
    {
    	try {
			// connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//select course_name,faculty from courses where course_name in (select course_name from students_enrolled where student_name = 'Student_name');
			String str1 = '(' + "select course_name from students_enrolled where student_name =" + '"' + student_name + '"' + ')';
			String str2 = "select course_name, faculty from courses where course_name in ";
			resultSet = statement.executeQuery(str2 + str1);
			if(resultSet.next() == false)
			{
				System.out.println("You do not have any course registered!");
			}
			else
			{
				do {
					System.out.println(resultSet.getString(1) + ", Instructor: " + resultSet.getString(2));
				}while(resultSet.next());
			}
		}
		catch (SQLException e) {
			// handle the exceptions
			System.out.println("Database Error");
		}
		finally
		{
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }

    
    public static void student_blackboard(String student_name)
    {
    	int i=1;
    	ArrayList<String> courses = new ArrayList<String>();
    	String course_choice= "";
    	char notes_choice =' ';
    	try {
			//connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//select course_name from students_enrolled where student_name = 'student_name';
			String str1 = "select course_name from students_enrolled where student_name = " + "'" + student_name + "';";
			resultSet = statement.executeQuery(str1);

			if(resultSet.next() == false)
			{
				System.out.println("\nYou have no Registered courses");
                System.out.println("Register for courses and come back later!");
                System.out.println("Logging you off! have a nice day");
			}
			else 
			{
				do 
				{
					courses.add(resultSet.getString(1));
				} while(resultSet.next());
				
				while(!course_choice.equals("x"))
				{
					System.out.println("\nWelcome to UHCl Blackboard!");
					System.out.println("Select your course:");
					for(int j = 0; j < courses.size(); j++)
					{
						System.out.println(i + ". " + courses.get(j));
						i++;
					}
					System.out.println("x:leave Blackboard");
					
					course_choice = s.next();
					
					if(course_choice.matches("\\d+"))
					{   
						while(notes_choice != 'x')
                        {
							//System.out.println(courses + course_choice);
                            System.out.println("Please select your options:");
                            System.out.print("v : view course notes\n");
                            System.out.print("x : leave the course\n");

                            notes_choice = s.next().charAt(0);
                            //System.out.println(notes_choice);
                            
                            //view course notes
                            if(notes_choice == 'v')
                            {
                            	String str2 = "select notes,date_Time from courses_notes where course_name = " + "'" + courses.get((Integer.parseInt(course_choice) - 1)) + "'";
                            	resultSet1 = statement.executeQuery(str2);
                            	
                            	System.out.println("Course notes of " + courses.get((Integer.parseInt(course_choice) - 1)) + ":");
                            	if(resultSet1.next() == false)
                            	{
                            		System.out.println("None\n");
                            	}
                            	else 
                            	{
                            		do{
                            			System.out.println(resultSet1.getString(2) + ": " + resultSet1.getString(1));
                            		}while(resultSet1.next());
                            		System.out.print("\n");
                            	}
                            }
                        }
						notes_choice = ' ';
                        i=1;
					}
				}
				
			}
		}
		catch (SQLException e) {
			// handle the exceptions
			System.out.println(e);
		}
		finally
		{
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

    }
}