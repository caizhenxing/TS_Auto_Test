package ts.dw.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import ts.dw.data.ReadExcel;

public class CheckPoint {
	
	//获取page xml文件的路径
	static String XMLFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "ts" + File.separator + "dw"+ File.separator+ "pages" + File.separator +"CheckPoint.xml";
	static Document document = null;
	
	static List<String> ckp = new ArrayList<String>();
	
	
	/*
	 * save the verify result of testcase
	 */
	static Map<String,String> ckpmap = new HashMap<String,String>();
	
	/*
	 * get the testcase's verify result
	 */
	public static void getCaseEle(){
		ReadExcel re = new ReadExcel();
		re.readExcel();
		//all steps
		int stepNums = ReadExcel.stepNums();
		//put every step to map
		for(int i=1;i<=stepNums;i++){
			ckpmap.put("step"+i, ReadExcel.getData(17+(i-1)*6));
		}
	}
	
	/*
	 * test the data is right or not
	 */
	public static void getUiMapDatas(){
		for(int k=1;k<=ckpmap.size();k++){
			System.out.println(ckpmap.get("step"+k));
		}
	}
	
	public static void main(String args[]) throws DocumentException{
		CheckPoint.getCaseEle();
		CheckPoint.getUiMapDatas();
	}
}
