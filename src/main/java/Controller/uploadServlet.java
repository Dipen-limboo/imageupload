package Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)

public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String fName = request.getParameter("firstName");
		String lName = request.getParameter("lastName");
		InputStream ins = null;
		String message = null;
		Part file = request.getPart("photo");
		if (file != null) {
			System.out.println(file.getName());
			System.out.println(file.getContentType());
			System.out.println(file.getSize());
			
			ins = file.getInputStream();
		}
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?useSSL=false", "root", "0564");
			PreparedStatement ps = conn.prepareStatement("insert into contacts (first_name, last_name, photo) values (?, ?, ?)");
			ps.setString(1, fName);
			ps.setString(2, lName);
			if(file != null) {
				ps.setBlob(3, ins);
			}
			int status = ps.executeUpdate();
			if (status > 0) {
				out.println("add sucessfully");
				response.sendRedirect("show");
			}
			else {
				out.print("Fail to add");
			}
		} catch (Exception e) {
			System.out.println("message: " +e.getMessage());
			e.printStackTrace();
		}
	}

}
