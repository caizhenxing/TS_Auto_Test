package ts.dw.data;

import java.io.FileInputStream;
import java.util.Properties;
public class ReadIni {
	
	
	
	public static void main(String args[]) {
		/*
		 * getSetting()[0]:指定的浏览器
		 * getSetting()[1]:指定的URL
		 * getSetting()[2]:指定的测试用例
		 * getSetting()[3]:延时
		 */
		System.out.println(ReadIni.getSetting()[3]);
	}
	
	public static String[] getSetting(){
		Properties ppt = new Properties();
		try {
			ppt.load(new FileInputStream("config\\myconfig.ini"));
		} catch (Exception e) {
			
		}
		//get properties
		String borwser = ppt.getProperty("browser");
		//System.out.println("Browser:"+borwser);
		String url = ppt.getProperty("Application_url");
		//System.out.println("URL:"+url);
		String testcase = ppt.getProperty("TestCase");
		//System.out.println("testcase:"+testcase);
		String timeout = ppt.getProperty("timeout");
		String settings[] = {borwser,url,testcase,timeout};
		return settings;
	}
}

