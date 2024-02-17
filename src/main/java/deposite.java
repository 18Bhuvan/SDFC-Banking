
import java.sql.*;
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
 * Servlet implementation class deposite
 */
@WebServlet("/deposite")
public class deposite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deposite() {
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
		float amount = Float.parseFloat(request.getParameter("amt"));
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","bhuvandb","bhuvandb");
			PreparedStatement ps1=con.prepareStatement("update sdfcbank set AMOUNT = AMOUNT + ? where account_number=? and name=? and password=?");
			    ps1.setFloat(1, amount);
	            ps1.setLong(2, account_number);
	            ps1.setString(3, name);
	            ps1.setString(4, password);

	            int i = ps1.executeUpdate();

	            if (i > 0) {
	                out.println("<html>");
	                out.println("<head>");
	                out.println("<style>");
	                out.println("div.center {");
	                out.println("  display: flex;");
	                out.println("  flex-direction: column;");
	                out.println("  justify-content: center;");
	                out.println("  align-items: center;");
	                out.println("  height: 100vh;");
	                out.println("}");
	                out.println("div.message {");
	                out.println("  border: 3px solid green;");
	                out.println("  font-size: 40px;");
	                out.println("  background-color: #00CED1;");
	                out.println("  padding: 20px;");
	                out.println("  text-align: center;");
	                out.println("  margin-bottom: 20px;");
	                out.println("}");
	                out.println("</style>");
	                out.println("</head>");
	                out.println("<body>");
	                out.println("<div class='center'>");
	                out.println("<div class='message'>Hii.. <span style='color: red'>" + name + "</span>, You Deposited Successfully. <b style='color:red;'>Thank you.</b></div>");

	                // Retrieve the updated amount after the deposit
	                PreparedStatement psAmount = con.prepareStatement("select AMOUNT from sdfcbank where account_number=?");
	                psAmount.setLong(1, account_number);
	                ResultSet rs = psAmount.executeQuery();
	                if (rs.next()) {
	                    float newAmount = rs.getFloat(1);
	                    float previousAmount = (float) (newAmount - amount);
	                    out.println("<div class='message'>Previous Amount: " + previousAmount + "</div>");
	                    out.println("<div class='message'>Deposit Amount: " + amount + "</div>");
	                    out.println("<div class='message'>New Amount: " + newAmount + "</div>");
	                }
	                out.println("</div>");
	                out.println("</body>");
	                out.println("</html>");
	            } else {
	                out.println("<html>");
	                out.println("<head>");
	                out.println("<style>");
	                out.println("div.center {");
	                out.println("  display: flex;");
	                out.println("  flex-direction: column;");
	                out.println("  justify-content: center;");
	                out.println("  align-items: center;");
	                out.println("  height: 100vh;");
	                out.println("}");
	                out.println("div.message {");
	                out.println("  border: 3px solid green;");
	                out.println("  font-size: 40px;");
	                out.println("  background-color: #00CED1;");
	                out.println("  padding: 20px;");
	                out.println("  text-align: center;");
	                out.println("  margin-bottom: 20px;");
	                out.println("}");
	                out.println("</style>");
	                out.println("</head>");
	                out.println("<body>");
	                out.println("<div class='center'>");
	                out.println("<div class='message'>You entered something wrong, try again....</div>");
	                out.println("</div>");
	                out.println("</body>");
	                out.println("</html>");
	            }
	            con.close();
	        } catch (Exception ex) {
	            out.print(ex);
	        }
	    }
	}
