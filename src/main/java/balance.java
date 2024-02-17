

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class balance
 */
@WebServlet("/balance")
public class balance extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public balance() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String name=request.getParameter("uname");
		out.print("<table border='1'>");	
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bhuvandb","bhuvandb");
			PreparedStatement ps=con.prepareStatement("select * from sdfcbank where name=?");
			ps.setString(1,name);
			ResultSet rs=ps.executeQuery();
			ResultSetMetaData rss=rs.getMetaData();
			int n=rss.getColumnCount();
			for(int i=1;i<=n;i++)
				out.print("<td><font color=blue size=3>"+rss.getColumnName(i));
			out.print("<tr>");
			while(rs.next())
			{
				for(int i=1;i<=n;i++)
					out.print("<td><br>"+rs.getString(i));
				out.print("<tr>");
			}
			
			out.print("<table>");
			con.close();
		}
		catch(Exception ex)
		{
			out.print(ex);	
		}
	}
	}



