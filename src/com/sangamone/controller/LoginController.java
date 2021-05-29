package com.sangamone.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail=request.getParameter("email");
		String pass = request.getParameter("password");
		PrintWriter out = response.getWriter();
		
		try {
			InputStream input = LoginController.class.getClassLoader().getResourceAsStream("/config.properties");
			Properties properties = new Properties();
			properties.load(input);
			String user=properties.getProperty("user");
			String password = properties.getProperty("password");
			String driver=properties.getProperty("driver");
			String url=properties.getProperty("url");
			Class.forName(driver);
			Connection con =DriverManager.getConnection(url, user,password);
			
			PreparedStatement ps=null;
			
			ResultSet rs2=null;
			/*
			String sql="select * from persons where email=?  and password=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, uemail);
			ps.setString(2, pass);
			*/
			 rs2 = ps.executeQuery();
		
	
			String sql2 ="select * from persons where email='"+uemail+"' and password='"+pass+"'" ;
			
			PreparedStatement ps2 = con.prepareStatement(sql2);
	//ResultSet rs2 = ps2.executeQuery();
			
			if(rs2.next()) {
				int loginAttempt=0;
				String name = rs2.getString("username");
				String email = rs2.getString("email");
				HttpSession session = request.getSession();
				String sId=session.getId();
				session.setAttribute("name", name);
				session.setAttribute("email", email);
				response.sendRedirect("home.jsp");
				String sql5= "select loginAttempt from persons where email=?";
				ps = con.prepareStatement(sql5);
				ps.setString(1, uemail);
				rs2 = ps.executeQuery();
				if(rs2.next()) {
			    loginAttempt=rs2.getInt(1);
				}
				String sql6 = "update persons SET loginAttempt=? , loginAttemptFailed=? , sessionId=? where email=? ";
				ps = con.prepareStatement(sql6);
				ps.setInt(1, (loginAttempt+1));
				ps.setInt(2, 0);
				ps.setString(3, sId);
				ps.setString(4, uemail);
				ps.executeUpdate();

			}
			else {
				int count=0;
				String sql3= "select loginAttemptFailed from persons where email=?";
				PreparedStatement ps3 = con.prepareStatement(sql3);
				ps3.setString(1, uemail);
				ResultSet rs3 = ps3.executeQuery();
				if(rs3.next()) {
				count=rs3.getInt(1);
				}else {
					count=0;
				}
				if(count >=0 && count<=3) {
				String sql4 = "update persons SET loginAttemptFailed=? where email=? ";
				PreparedStatement ps4 = con.prepareStatement(sql4);
				ps4.setInt(1, (count+1));
				ps4.setString(2, uemail);
				ps4.executeUpdate();
				}
				
				if(count>=3) {
					out.println("Your account has been blocked due to wrong credential, Please contact your main branch");
					String sql4 = "update persons SET password=? where email=? ";
					PreparedStatement ps4 = con.prepareStatement(sql4);
					ps4.setString(1, null);
					ps4.setString(2, uemail);
					ps4.executeUpdate();
					
				}else {
				out.println("wrong username/password");
				}
				
			}
		} catch (Exception e) {
			out.println("Something went wrong..");
			
		}
	}
}
