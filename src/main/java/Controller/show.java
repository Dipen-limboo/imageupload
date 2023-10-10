package Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/show")
public class show extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html/images/jpeg");
		PrintWriter out = response.getWriter();
		List<User> list = new ArrayList<>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?useSSL = false", "root", "0564");
			PreparedStatement ps = conn.prepareStatement("select * from contacts");
			
			ResultSet rs = ps.executeQuery();
			User usr = null;
			while (rs.next()) {
				usr = new User();
				usr.setId(rs.getInt(1));
				usr.setFname(rs.getString("first_name"));
				usr.setLname(rs.getString("last_name"));
				
				Blob blob = rs.getBlob("photo");
				InputStream ins = blob.getBinaryStream();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte[] byt = new byte[4096];
				int byteread = -1;
				
				while((byteread = ins.read(byt)) != -1) {
					os.write(byt,  0, byteread);
				}
				
				byte [] imageByte = os.toByteArray();
				String images = Base64.getEncoder().encodeToString(imageByte);
				
				
				
				usr.setImage(images);
				list.add(usr);
			}
			request.setAttribute("UserList", list);
			System.out.println(list);
			request.getRequestDispatcher("image.jsp").forward(request, response);
			
		} catch (Exception e) {
			System.out.println("message" +e.getMessage());
			e.printStackTrace();
		}
	}

	

}
