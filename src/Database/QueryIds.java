package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;

public class QueryIds {

    /**
     * Returns a hashtable of all the country
     * @param id the id
     * @param name the name
     * @param table the table to query
     * @return a hashtable of all the ids and names of the given table
     * @throws SQLException if the query fails
     */
    public static Hashtable<String, Integer> country(String id, String name, String table) throws SQLException {
        Hashtable<String, Integer> ids = new Hashtable<>();
        try{
            Statement statement = DatabaseConnection.getConnection().createStatement();
            // Query the id and name columns from table
            String query = "SELECT " + id + ", " + name + " FROM " + table;
            ResultSet rs = statement.executeQuery(query);
            // Add the id and name to the hashtable
            while(rs.next()){
                ids.put(rs.getString(name), rs.getInt(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    /**
     * Returns a hashtable of all regions
     * @param id the id
     * @param name the name
     * @param countryId the country id
     * @param table the table to query
     * @return a hashtable of all the ids and names of the given table
     * @throws SQLException if the query fails
     */
    public static Hashtable<String, Integer> region(String id, String name, String countryId, String table) throws SQLException {
        Hashtable<String, Integer> ids = new Hashtable<>();
        try{
            Statement statement = DatabaseConnection.getConnection().createStatement();
            // Query the id and name columns from table where countryId = countryId
            String query = "SELECT " + id + ", " + name + " FROM " + table + " WHERE COUNTRY_ID = " + countryId;
            ResultSet rs = statement.executeQuery(query);
            // Add the id and name to the hashtable
            while(rs.next()){
                ids.put(rs.getString(name), rs.getInt(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
