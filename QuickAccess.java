package resources;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.sql.*;


public class QuickAccess {

	private static HashMap<Integer, Integer> dictFolders = new HashMap<Integer, Integer>();
	private static HashMap<Integer, String> dictFiles = new HashMap<Integer, String>();
	private static LocalDateTime myObj ;
	private static Connection Conn;
	private static Statement stmt;
	private static ResultSet rs;
	
	
	public static void connect(){ 
        try {  
        	Conn = DriverManager.getConnection("jdbc:sqlite:QuickAccess.db"); 
        	stmt = Conn.createStatement();
            
            String recentFileTable = "CREATE TABLE IF NOT EXISTS recent_file(ID INTEGER PRIMARY KEY AUTOINCREMENT, File_Name TEXT NOT NULL, Date_FileOpen DATETIME NOT NULL)";
            String freqFolderTable = "CREATE TABLE IF NOT EXISTS folder_frequence(ID INTEGER PRIMARY KEY AUTOINCREMENT, Folder_Name TEXT NOT NULL, Date_FolderOpen DATETIME NOT NULL)";
            
            stmt.execute(recentFileTable);  
            stmt.execute(freqFolderTable);  
           
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
	}
	
	
	
	static void addFile(String fileOpen) throws SQLException {
		myObj = LocalDateTime.now();
		int idToDelete = 0;

		try {
			rs = stmt.executeQuery("SELECT ID, Date_FileOpen FROM recent_file");
			while (rs.next()) {
				int id = rs.getInt("ID");
				String date = rs.getString("Date_FileOpen");
				dictFiles.put(id, date);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		if(dictFiles.size() < 2) {
			stmt.executeUpdate(String.format("INSERT INTO recent_file(File_Name, Date_FileOpen) VALUES('%s', '%s')", fileOpen, myObj));
		}
		else {
			stmt.executeUpdate(String.format("INSERT INTO recent_file(File_Name, Date_FileOpen) VALUES('%s', '%s')", fileOpen, myObj));
			
			String min = Collections.min(dictFiles.values());
			for(int key:dictFiles.keySet()) {
				if(min.equals(dictFiles.get(key))) {
					idToDelete = key;
				}
			}
			
			stmt.executeUpdate(String.format("DELETE FROM recent_file WHERE ID=%s", idToDelete));
		}

		dictFiles.clear();

	}
	
	
	
	
	static ArrayList<File> getListFiles() throws IOException, SQLException{
		ArrayList<File> listFiles = new ArrayList<File>();
		rs = stmt.executeQuery("SELECT File_Name FROM recent_file");
		while (rs.next()) {
			String name = rs.getString("File_Name");
			File filename = new File(name);
			listFiles.add(filename);
		}
	    
		return  listFiles;
	}

	
	
	
	
	//close connect
	//Conn.close();

}
