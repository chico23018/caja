package caja;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conezione {
public static Connection conetar() {
		
		
		try {
			Connection cd = DriverManager.getConnection("jdbc:mysql://localhost/caja", "root", "martinez230");
			
			return cd;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
}
}