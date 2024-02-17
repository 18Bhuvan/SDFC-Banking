

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class closeAcc
 */
@WebServlet("/closeAcc")
public class closeAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public closeAcc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		long account_numbr = Long.parseLong(request.getParameter("Accnum"));
		String name = request.getParameter("uname");
		String password = request.getParameter("psw"); 
				
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bhuvandb","bhuvandb");
			
			// Check if the account exists
			PreparedStatement ps = con.prepareStatement("SELECT * FROM sdfcbank WHERE ACCOUNT_NUMBER=? AND NAME=? AND PASSWORD=?");
			ps.setLong(1, account_numbr);
			ps.setString(2, name);
			ps.setString(3, password);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// Account closure message
				out.print("<style> div.c { position: absolute; top:200px; border: 3px solid green; font-size: 40px;  background-color:#00CED1; } </style>");
				out.print("<div class=c> Welcome <span style='color: red'>" + name + "</span>, your Account Number <b style='color:red;'> " + account_numbr +  " </b> has been closed. No further operations can be performed. </div>");
			} else {
				out.print("<style> div.c { position: absolute; top:200px; border: 3px solid green; font-size: 40px; left:300px; background-color:#00CED1; } </style>");
				out.print("<div class=c> Invalid account details. Please check your account number, name, and password. </div>");
			}
			
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
