import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class User
{

    public String name_of_user;
    public String major_of_user;
    public String user_name_of_user;
    public String password_of_user;
    public char user_type;

    public User(String name, String user_major, String user_name, String password, char type)
    {
        name_of_user = name;
        major_of_user = user_major;
        user_name_of_user = user_name;
        password_of_user= password;
        user_type = type;
    }
    
    public static String[] loginUser(String username, String password) {
    	String[] user = new String[2];
		final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/mallam5664?useSSL=false";
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			
			String str = "SELECT Type,Name FROM users WHERE Username=" + '"'+ username + '"' + "AND Password=" + '"' + password + '"' + ';';
			System.out.println("Please wait....");
			
			// connect to the database
			connection = DriverManager.getConnection(DATABASE_URL, "mallam5664", "1894990");
			statement = connection.createStatement();
			
			//executing query
			//String str = "select * from users where Username="+username+"AND Password="+password
			resultSet = statement.executeQuery(str);
			if(resultSet.next() == false)
			{
				user[1] = "Fail";
			}
			else 
			{
				do {
						user[0] = resultSet.getString(2);
						user[1] = resultSet.getString(1);
				}while(resultSet.next());
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
		return user;
    }
}