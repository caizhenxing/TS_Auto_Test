package ts.dw.logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class HTMLLOG {
	static GregorianCalendar time = new GregorianCalendar();	
	public static String filePath = "";
	public static String reportGenerated = "";
	
	//写入HTML
	public void generateReport()throws Exception{
		reportGenerated="true";  
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		String timeStamp=dateFormat.format(cal.getTime());
		//String path = System.getProperty("user.dir")+"log";
		filePath = "log\\"+"Regression_Result-"+"-"+timeStamp+".html";
	    FileWriter fstream = new FileWriter(filePath);
	    System.out.println("Report's FileName: "+filePath);
	    BufferedWriter out = new BufferedWriter(fstream);
	    out.write("<html>");
	    out.write("<head>");
	    //out.write("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
	    out.write("<style type='text/css'> " 
	    	  + " #summary" 
	  	      + " {"
	  	      + " font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;"
	  	      + " width:100%;"
	  	      + " border-collapse:collapse;"
	  	      + " }"
	  	      + " #summary td, #summary th"
	  	      + " {"
	  	      + " font-size:0.8em;"
	  	      + " border:1px solid #98bf21;"
	  	      + " padding:3px 7px 2px 7px;"
	  	      + " }"
	  	      + " #summary th"
	  	      + " {"
	  	      + " font-size:1.0em;"
	  	      + " text-align:left;"
	  	      + " padding-top:5px;"
	  	      + " padding-bottom:4px;"
	  	      + " background-color:#A7C942;"
	  	      + " color:#ffffff;"
	  	      + " }"
	  	      
	  	      + " #componentWiseSummary" 
		      + " {"
		      + " font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;"
		      + " width:100%;"
		      + " border-collapse:collapse;"
		      + " }"
		      + " #summary td, #summary th"
		      + " {"
		      + " font-size:0.8em;"
		      + " border:1px solid #98bf21;"
		      + " padding:3px 7px 2px 7px;"
		      + " }"
		      + " #summary th"
		      + " {"
		      + " font-size:1.0em;"
		      + " text-align:left;"
		      + " padding-top:5px;"
		      + " padding-bottom:4px;"
		      + " background-color:#A7C942;"
		      + " color:#ffffff;"
		      + " }"
	  	      
			  + " #customers" 
		      + " {"
		      + " font-family:\"Trebuchet MS\", Arial, Helvetica, sans-serif;"
		      + " width:100%;"
		      + " border-collapse:collapse;"
		      + " }"
		      + " #customers td, #customers th"
		      + " {"
		      + " font-size:0.8em;"
		      + " border:1px solid #98bf21;"
		      + " padding:3px 7px 2px 7px;"
		      + " }"
		      + " #customers th"
		      + " {"
		      + " font-size:1.1em;"
		      + " text-align:left;"
		      + " padding-top:5px;"
		      + " padding-bottom:4px;"
		      + " background-color:#A7C942;"
		      + " color:#ffffff;"
		      + " }"
		      + " #customers tr.alt td"
		      + " {"
		      + " color:#000000;"
		      + " background-color:#EAF2D3;"
		      + " }"
	          + " </style>");
	    out.write("</head>");

	    out.write("<body text-align:center>");
	    
	    out.write("<table width=100% border=0 cellpadding=2 cellspacing=2>");
	    out.write("<tbody>");
	    out.write("<tr>");
		out.write("<td>");
		out.write("<table width=100% border=0 cellpadding=2 cellspacing=2>");
		out.write("<tbody>");
		out.write("<tr>");
		out.write("<td align=center><p class=title><h1>TS_AutoTest自动化测试报告</h1></p></td></tr>");
		out.write("</tr>" + "</tbody>" + "</table>" + "<br>");
		
	    /**
	     * Creating tables in HTML for Test Status
	     */    
	    out.write("<table id=\"customers\">");
	    out.write("<tr>");
	    out.write("<th>Test Case Name</th>");
	    out.write("<th>Test Case ID</th>");
	    out.write("<th>Test Name and Steps</th>");
	    out.write("<th>Test Data</th>");
	    out.write("<th>Expected Result</th>");
	    out.write("<th>Actual Result</th>");
	    out.write("</tr>");
	    out.write("</body>");
	    out.write("</html>");
	    out.flush();
	    out.close();  
	}
	
	/*
	 * 将每一步执行的情况写入日志文件
	 */
	public void sendStatusToReport(String T_Name,String TC_ID, String Steps,String Data,String verify,String Result)throws Exception{
  
		//先原文件末尾添加内容
		FileWriter fstream = new FileWriter(filePath, true);
	    BufferedWriter out = new BufferedWriter(fstream);
	    
	    if(Integer.parseInt(TC_ID)%2==0){
	    	if(Result.equals("Fail")){
		    	out.write("<tr bgcolor=#FF0000>");
		    }else{
		    	out.write("<tr>");
		    }
	    }else{
	    	if(Result.equals("Fail")){
		    	out.write("<tr bgcolor=#FF0000>");
		    }else{
		    	out.write("<tr bgcolor=#EAF2D3>");//red:bgcolor=#FF0000
		    }
	    }
	    
	    out.write("<td><b>" + T_Name + "</b></td>");
	    out.write("<td><b>" + TC_ID + "</b></td>");
	    out.write("<td><b>" + Steps + "</b></td>");
	    out.write("<td><b>" + Data + "</b></td>");
	    out.write("<td><b>" + verify + "</b></td>");
	    out.write("<td><b>" + Result + "</b></td>");
	    out.write("</tr>");
	    out.flush();
	    out.close();
	}
	
	public static void main(String args[]) throws Exception{
		HTMLLOG hl = new HTMLLOG();
		hl.generateReport();
		hl.sendStatusToReport("致友登录", "0", "操作步骤", "测试数据", "预期结果", "实际结果");
		hl.sendStatusToReport("", "1", "输入邮箱", "kicen@kk.com", "输入成功", "Pass");
		hl.sendStatusToReport("", "2", "输入密码", "1", "输入成功", "Fail");
		hl.sendStatusToReport("", "3", "输入邮箱", "kicen@kk.com", "输入成功", "Pass");
		hl.sendStatusToReport("", "4", "输入密码", "1", "输入成功", "Fail");
		hl.sendStatusToReport("", "5", "asd", "1", "qweq", "Pass");
	}
}
