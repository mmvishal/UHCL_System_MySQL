import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class Faculty extends User
{
    private ArrayList<Course> courses_undertaken_by_faculty = new ArrayList<Course>();
    
    //Database attributes
    final static String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/mallam5664?useSSL=false";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;
	static ResultSet resultSet1 = null;
	
	
	//date format
	static SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
	
	static Scanner s = new Scanner(System.in);
    
    public Faculty(String name, String user_major, String user_name, String password)
    {
        super(name, user_major, user_name, password, 'F');
    }

    public ArrayList<Course> getCourses()
    {
        return courses_undertaken_by_faculty;
    }
    
    public static void faculty_blackboard(String faculty_name)
    {
    	int i=1;
    	ArrayList<String> courses = new ArrayList<String>();
    	String course_choice= "";
    	String notes_choice ="";
    	boolean notes_success = false;
    	try {
			//connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//select course_name from courses where course_major in (select major from users where Name = 'Jean') AND 
			//(course_name NOT IN (select course_name from students_enrolled where student_name = 'Jean'))
			String str1 = "select course_name from courses where faculty = " + "'" + faculty_name + "';";
			resultSet = statement.executeQuery(str1);

			if(resultSet.next() == false)
			{
				System.out.println("You have no courses for further Registration");
			}
			else 
			{
				do 
				{
					courses.add(resultSet.getString(1));
				} while(resultSet.next());
				
				while(!course_choice.equals("x"))
				{
					System.out.println("\nWelcome to UHCL Blackboard!");
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
						while(!notes_choice.equals("x"))
                        {
                            System.out.println("\nPlease select your options:");
                            System.out.print("v : view my course notes\n");
                            System.out.print("p : post new course note\n");
                            System.out.print("x : leave the course\n");

                            notes_choice = s.next();

                            if(notes_choice.equals("v"))
                            {
                            	//select notes,date_time from course_notes where course_name = 'Course_name';
                            	String str2 = "select notes,date_Time from courses_notes where course_name = " + "'" + courses.get((Integer.parseInt(course_choice) - 1)) + "'";
                            	
                            	resultSet1 = statement.executeQuery(str2);
                            	//System.out.println(str2);
                            	System.out.println("Course notes of " + courses.get((Integer.parseInt(course_choice) - 1)) + ":");
                            	if(resultSet1.next() == false)
                            	{
                            		System.out.println("None\n");
                            		//i=1;
                            	}
                            	else 
                            	{
                            		do{
                            			System.out.println(resultSet1.getString(2) + ": " + resultSet1.getString(1));
                            		}while(resultSet1.next());
                            		System.out.print("\n");
                            		//i=1;
                            	}
                            }                            
                            else if(notes_choice.equals("p"))
                            {
                            	System.out.print("Please enter your new note:\n");
                            	s.nextLine();
                                String notes = s.nextLine();
                                String dateString = DateAndTime.DateTime().toString();
                                
                                //Adding notes to the course
                                //Insert into courses_notes values ('course_name', 'notes', 'date_time');
                                String str2 = "INSERT INTO courses_notes VALUES ('" + courses.get((Integer.parseInt(course_choice) - 1)) + "'," + '"' + notes + '"' + ",'" + dateString + "');";
                                statement.executeUpdate(str2);
                                System.out.print("Your note is added to the course. Your students can see it now.\n");
                            }
                        }
					}
					notes_choice = " ";
					i=1;
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