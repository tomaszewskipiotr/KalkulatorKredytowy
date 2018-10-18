package servlet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
@WebServlet("/PDF")
public class PDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public PDF() {
		super();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		java.text.DecimalFormat df=new java.text.DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(0);
		float creditAmount=Float.parseFloat(request.getParameter("kwotaKredytu"));
	    int howMany=Integer.parseInt(request.getParameter("iloscRat"));
		float interest=Float.parseFloat(request.getParameter("oprocentowanie"));
		float oplataStala=Float.parseFloat(request.getParameter("oplataStala"));
		int rateType = Integer.parseInt(request.getParameter("rodzaj"));
		ByteArrayOutputStream PDFstorage = new ByteArrayOutputStream();
		Document pdf = new Document();
		try {
			PdfWriter.getInstance(pdf, PDFstorage);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		pdf.open();
		PdfPTable table = new PdfPTable(5);
		ArrayList<String> column = new ArrayList<String>();
		column.add("Nr Raty");
		column.add("Kwota Kredytu");
		column.add("Kwota Odsetek");
		column.add("Ca³kowia Kwota Raty");
		for (String s :column) {
			PdfPCell header = new PdfPCell();
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(s));
			table.addCell(header);
		}
		 if (rateType == 1) {
		{
			float var=1+(interest/1200);
			float rate=(float) (((creditAmount-oplataStala)*Math.pow(var,howMany))* (var-1)/(Math.pow(var, howMany)-1));
			float interestFee=((rate*howMany)-creditAmount);
			response.getWriter().println("<table border=\"1\"><tr><td>Nr</td><td>Kwota</td><td>Kwota odsetek</td><td>Rata</td>");
			for(int j=0;j<howMany;j++) {
				  table.addCell(String.valueOf(j + 1));
				  table.addCell(String.valueOf(creditAmount));
				  table.addCell(String.valueOf(df.format(interestFee)));
				  table.addCell(String.valueOf(df.format(rate)));
			}
		}
		 }
		if (rateType == 2) {
		{
			  float capitalPart = creditAmount/howMany ;
			  for(int i=0;i<howMany;i++) {
			   float interestPart=creditAmount * (interest/1200);
			 float rate = capitalPart+interestPart;
			  float interestFee=((rate*howMany)-creditAmount);
			  creditAmount=creditAmount-capitalPart;
			  table.addCell(String.valueOf(i + 1));
			  table.addCell(String.valueOf(creditAmount));
			  table.addCell(String.valueOf(df.format(interestFee)));
			  table.addCell(String.valueOf(df.format(rate)));
				}
		}
		}
				try {
					pdf.add(table);
				} catch (DocumentException e) {
					e.printStackTrace();
				}
				pdf.close();
				response.reset();
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachement; filename=raty.pdf");
				response.setContentLength((int)PDFstorage.size());
				byte[] pdfBytes = PDFstorage.toByteArray();
				ByteArrayInputStream pdfOut = new ByteArrayInputStream(pdfBytes);
				int bytes;
				while ((bytes = pdfOut.read()) != -1) {
					response.getOutputStream().write(bytes);
				}
			}

}
