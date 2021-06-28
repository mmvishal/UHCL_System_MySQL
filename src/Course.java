import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Course
{
    public String  course_name;
    public String  facutly_name;
    public String  course_major;
    public ArrayList<String> enrolled_students_names = new ArrayList<String>();
    public ArrayList<String> notes = new ArrayList<String>();
    public ArrayList<String> time = new ArrayList<String>();
    public String status;

    final static String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/mallam5664?useSSL=false";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet resultSet = null;
	static ResultSet resultSet1 = null;
    
	static Scanner s = new Scanner(System.in);
	
    public Course(String course, String facutly, String major)
    {
        course_name = course;
        facutly_name = facutly;
        course_major = major;
        status = "OPEN";
    }
    
    
    //Sudents Registered to courses
    public static void courses_registered(String faculty_name)
    {
    	ArrayList<String> courses = new ArrayList<String>();
    	try {
			// connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//String str1 = '(' + "select course_name from courses where faculty =" + '"' + faculty_name + '"' + ')';
			//String str2 = "select course_name,student_name from students_enrolled where course_name in";
			String str = "select course_name  from courses where faculty = '" + faculty_name + "';" ;
			resultSet = statement.executeQuery(str);
			if(resultSet.next() != false)
			{
				do {
					courses.add(resultSet.getString(1));
				}while(resultSet.next());
				
				for(int i = 0; i < courses.size(); i++)
				{
					//select student_name from students_enrolled where course_name = 'course_name';
					String str1 = "SELECT student_name FROM students_enrolled where course_name = '" + courses.get(i) + "';";
					resultSet1 = statement.executeQuery(str1);
					//System.out.println(courses.get(i));
					if(resultSet1.next() != false)
					{
						System.out.println(courses.get(i));
						System.out.println("Students Enrolled: ");
						do {
							System.out.println(resultSet1.getString(1));
						}while(resultSet1.next());
						System.out.println();
					}
					else {
						do {
							System.out.println(courses.get(i));
							System.out.println("Students enrolled:");
						}while(resultSet1.next());
						System.out.println();
					}
					
				}
				
//				while(resultSet.next()) {
//						if(!current_course.equals(resultSet.getString(1)))
//						{
//							System.out.println("\n" + resultSet.getString(1));
//							System.out.println("Students enrolled:");
//							
//						}
//						current_course = resultSet.getString(1);
//						System.out.println(resultSet.getString(2));
//				}
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
    
    
    //List courses available for students
    public static void register_course(String student_name)
    {
    	int i=1;
    	String course_choice;
    	
    	try {
			//connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//select course_name from courses where course_major in (select major from users where Name = 'Jean') AND 
			//(course_name NOT IN (select course_name from students_enrolled where student_name = 'Jean'))
			String str1 = "select course_name from students_enrolled where student_name = " + "'" + student_name + "'))";
			String str2 = " AND (course_name NOT IN (";
			String str3 = "select major from users where Name = " + "'" + student_name + "')" ;
			String str4 = "select course_name from courses where course_major in " + '(';
			//System.out.println(str4 + str3 + str2 + str1);
			resultSet = statement.executeQuery(str4 + str3 + str2 + str1);

			if(resultSet.next() == false)
			{
				System.out.println("You have no courses for further Registration");
			}
			else 
			{
				System.out.println("Welcome to register a new course!");
            	System.out.println("These are the courses available to you:");
            	ArrayList<String> courses = new ArrayList<String>();
				do 
				{
					System.out.println(i + ". " + resultSet.getString(1));
					courses.add(resultSet.getString(1));
					i++;
				} while(resultSet.next());
				System.out.println("Or any other key to exit");
				course_choice = s.next();
				if(course_choice.matches("\\d+"))
				{
					String str5 = "insert into students_enrolled VALUES ('" + courses.get((Integer.parseInt(course_choice) - 1)) + "', '" + student_name + "');";
					//System.out.println(str5);
					statement.executeUpdate(str5);
					System.out.println("The course is added to your schedule!");
				}
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
    
}