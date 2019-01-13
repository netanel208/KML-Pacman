package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class accesses the database and compares our results to the results of the rest of the class
 * In this class there is one function that prints for a particular game our best result
 * against the average of the class
 * @author Carmel
 * @author Netanel
 */
public class ComparingRes {
	
	
	/**
	 * This method prints for a particular game our best result
	 * against the average of the class
	 * @param map the number of the map 
	 */
	public static  void printAvg ( int map)
	{
		String jdbcUrl="jdbc:mysql://ariel-oop.xyz:3306/oop"; 
		String jdbcUser="student";
		String jdbcPassword="student";
		String mapName = ""+map;
		double bestPoint=0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);


			Statement statement = connection.createStatement();

			//select data to be our id and the mapName 
			String ourCustomersQuery = "SELECT * FROM logs WHERE FirstID=315858340 AND SecondID=208252684 AND SomeDouble= "+mapName+";";
			ResultSet resultSet = statement.executeQuery(ourCustomersQuery);
			while(resultSet.next())
			{
				// calculate our best point
				double point= resultSet.getDouble("Point");
				if ( point > bestPoint)
					bestPoint=point;
			}
			
			resultSet.close();		

			// select data to be mapName
			double sumPoint=0;
			int numOfrow = 0;
			String allCustomersQuery = "SELECT * FROM logs WHERE SomeDouble= "+mapName+";";
			ResultSet result = statement.executeQuery(allCustomersQuery);
			while(result.next())
			{
				numOfrow++; // number of row
				double point= result.getDouble("Point"); // calculate the sum of point
				sumPoint+=point;
			
			}
			
			double avg= sumPoint/numOfrow; // calculate the average of the point
			System.out.println("The average of the class is: "+avg);
			System.out.println("Our result is using the automated system: "+bestPoint);

			result.close();		
			statement.close();		
			connection.close();		
		}

		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void main(String [] args)
	{

		int e1= 2128259830;
		int e2= 1149748017;
		int e3= -683317070;
		int e4= 1193961129;
		int e5= 1577914705;
		int e6 = -1315066918;
		int e7 =-1377331871;
		int e8 =306711633;
		int e9 = 919248096;
		
		System.out.println("**game number 1**");
		printAvg(e1);
		
		System.out.println("**game number 2**");
		printAvg(e2);
		
		System.out.println("**game number 3**");
		printAvg(e3);
		
		System.out.println("**game number 4**");
		printAvg(e4);
		
		System.out.println("**game number 5**");
		printAvg(e5);
		
		System.out.println("**game number 6**");
		printAvg(e6);
		
		System.out.println("**game number 7**");
		printAvg(e7);
		
		System.out.println("**game number 8**");
		printAvg(e8);
		
		System.out.println("**game number 9**");
		printAvg(e9);
		

	}
}


