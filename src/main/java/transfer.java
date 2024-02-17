
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
 * Servlet implementation class transfer
 */
@WebServlet("/transfer")
public class transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
        long account_number=Long.parseLong(request.getParameter("Accnum"));
        String name=request.getParameter("uname");
        String password=request.getParameter("psw");
        long Target_Account_No=Long.parseLong(request.getParameter("tarno"));
        float amount=Float.parseFloat(request.getParameter("amt"));
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "bhuvandb", "bhuvandb");
            PreparedStatement ps=con.prepareStatement("select Amount from sdfcbank where account_number=? and name=? and password=?");
            ps.setLong(1,account_number);
            ps.setString(2,name);
            ps.setString(3,password);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                float temp=rs.getFloat(1);
                if(amount<=temp){
                    out.print("<div class=c>Your account balance before transfer: " + temp + "</div>");
                    PreparedStatement pss=con.prepareStatement("update sdfcbank set Amount=Amount-? where account_number=? and name=? and password=?");
                    pss.setFloat(1,amount);
                    pss.setLong(2,account_number);
                    pss.setString(3,name);
                    pss.setString(4,password);
                    int i=pss.executeUpdate();
                    
                    PreparedStatement psss=con.prepareStatement("update sdfcbank set Amount=Amount+? where account_number=?");
                    psss.setFloat(1,amount);
                    psss.setLong(2,Target_Account_No);
                    int j=psss.executeUpdate();
                    
                    PreparedStatement psssTarget=con.prepareStatement("select Amount from sdfcbank where account_number=?");
                    psssTarget.setLong(1,Target_Account_No);
                    ResultSet rsTarget=psssTarget.executeQuery();
                    
                    float afterTransferTargetBalance = 0;
                    if(rsTarget.next()){
                        afterTransferTargetBalance = rsTarget.getFloat(1);
                    }
                                    
                    out.print("<div class=c> Your account balance Deducted to: " + amount + "</div>");
                    out.print("<div class=c> Target account balance after transfer: " +  afterTransferTargetBalance + "</div>");
                    
                }
                else{
                    out.print("<div class=c>Your entered amount is less than your account balance: " + amount + "</div>");
                }
            }
            con.close();
        }
        catch(Exception ex){
            out.println(ex);
        }
	}

}
