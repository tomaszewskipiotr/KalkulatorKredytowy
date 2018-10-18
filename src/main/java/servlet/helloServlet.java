package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello")
public class helloServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public helloServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setStatus(418);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		PrintWriter out = response.getWriter();
		java.text.DecimalFormat df=new java.text.DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		int rateType = Integer.parseInt(request.getParameter("rodzaj"));
		float creditAmount=Float.parseFloat(request.getParameter("kwotaKredytu"));
		float creditAmounts=Float.parseFloat(request.getParameter("kwotaKredytu"));
		float howMany=Float.parseFloat(request.getParameter("iloscRat"));
		float interest=Float.parseFloat(request.getParameter("oprocentowanie"));
		float oplataStala=Float.parseFloat(request.getParameter("oplataStala"));
		 if (rateType == 1) {
		{
			double var=1+(interest/1200);
			double rate=((creditAmount-oplataStala)*Math.pow(var,howMany))* (var-1)/(Math.pow(var, howMany)-1);
			double interestFee=((rate*howMany)-creditAmount);
			out.println("<table border=\"1\"><tr><td>Nr</td><td>Kwota</td><td>Kwota odsetek</td><td>Rata</td>");
			for(int j=0;j<howMany;j++) {
			out.println("<tr><td>"+(j+1)+"</td><td>"+creditAmount+"</td><td>"+df.format(interestFee)+"</td><td>"+df.format(rate)+"</td>");
			}
			}
		}
		 if (rateType == 2) {
		{
			float capitalPart = creditAmount/howMany ;
			  for(int i=0;i<howMany;i++) {
				  float interestPart=creditAmount * (interest/1200);
				  float rate = capitalPart+interestPart;
				  float interestFee=((rate*howMany)-creditAmounts);
			  creditAmount=creditAmount-capitalPart;
			 out.println("<table border=\"1\"><tr><td>Nr</td><td>Kwota</td><td>Kwota odsetek</td><td>Rata</td>");
			 out.println("<tr><td>"+(i+1)+"</td><td>"+creditAmounts+"</td><td>"+df.format(interestFee)+"</td><td>"+df.format(rate)+"</td>");
				}
			  
			 
		}
		}
		out.append("</table>");
		out.append("<form action=\"PDF\" method=\"post\">\r\n" + 
				"		<input type=\"hidden\" value='"+creditAmount+"' name=\"kwota\">\r\n" + 
				"		<input type=\"hidden\" value='"+howMany+"' name=\"ilosc\">\r\n" + 
				"		<input type=\"hidden\" value='"+interest+"' name=\"oprocentowanie\">\r\n" + 
				"		<input type=\"hidden\" value='"+oplataStala+"' name=\"oplata\">\r\n" + 
				"		<input type=\"hidden\" value='"+rateType+"' name=\"rodzaj\">\r\n" + 
				"		<input type=\"submit\" value=\"PDF\">\r\n" + 
				"	</form>");
		out.append("</body>");
		
		}
	}

	
	

