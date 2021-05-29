package com.sangamone.function;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Properties;


public class LogoutUpdate {

	public static String updateLogout(String email) {
		
	    try {
            InputStream input = LogoutUpdate.class.getClassLoader().getResourceAsStream("/config.properties");
            Properties properties = new Properties();
            properties.load(input);
            String driver = properties.getProperty("driver");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            String url = properties.getProperty("url");
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, user, password);
            PreparedStatement pst=con.prepareStatement("update persons set sessionID=? where email=?");
            pst.setString(1, null);
            pst.setString(2, email);
            pst.executeUpdate();
	}
	 catch (Exception e) {
         System.out.println(e);
     }
	    return email;
}
}
