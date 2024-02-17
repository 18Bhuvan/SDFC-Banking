

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
/**
 * Servlet implementation class account
 */
@WebServlet("/account")
public class account extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public account() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
	    long account_number=Long.parseLong(request.getParameter("Accnum"));
	    String name=request.getParameter("uname");
	    String password=request.getParameter("psw");
	    String confirm_password=request.getParameter("psw");
	    double amount=Double.parseDouble(request.getParameter("amt"));
	    String address=request.getParameter("address");
	    long mobile_number=Long.parseLong(request.getParameter("mno"));
	    
	    try
	    {
	    	Class.forName("oracle.jdbc.driver.OracleDriver");
	    	Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bhuvandb","bhuvandb");
	    	PreparedStatement ps=con.prepareStatement("insert into sdfcbank values(?,?,?,?,?,?,?)");
	    	ps.setLong(1, account_number);
	    	ps.setString(2,name);
	    	ps.setString(3, password);
	    	ps.setString(4, confirm_password);
	    	ps.setDouble(5, amount);
	    	ps.setString(6, address);
	    	ps.setLong(7, mobile_number);
	    	
	    	int i=ps.executeUpdate();
	    	out.print(i+"    "+"new account opened succesfully");
	    	con.close();
	    }
	    catch(Exception ex)
	    {
	    	out.print(ex);
	    }
	}

	}


