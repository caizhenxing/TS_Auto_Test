package ts.dw.pages;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import ts.dw.data.ReadExcel;

public class GetPages {
	
	//获取page xml文件的路径
	static String XMLFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "ts" + File.separator + "dw"+ File.separator+ "pages" + File.separator +"UI_OF_PAGES.xml";
	static Document document = null;
	
	static List<String> uis = new ArrayList<String>();
	
	
	/*
	 * save the ui element of testcase
	 */
	static Map<String,String> uimap = new HashMap<String,String>();
	
	/*
	 * get the testcase's operat ui element
	 */
	public static void getCaseEle(){
		ReadExcel re = new ReadExcel();
		re.readExcel();
		//all steps
		int stepNums = ReadExcel.stepNums();
		//put every step to map
		for(int i=1;i<=stepNums;i++){
			uimap.put("step"+i, ReadExcel.getData(14+(i-1)*6));
		}
	}
	
	/*
	 * test the data is right or not
	 */
	public static void getUiMapDatas(){
		for(int k=1;k<=uimap.size();k++){
			System.out.println(uimap.get("step"+k));
		}
	}
	
	/*
	 * get the data of element in xml to match with the testcase's operat ui element
	 */
	@SuppressWarnings("unchecked")
	public static void match() throws DocumentException{
	   /*
		* get the testcase's operat ui element
		*/
		getCaseEle();
		
		//first,get all xml
		document = new SAXReader().read(new File(XMLFilePath));
		//get root element
		Element targets = document.getRootElement();
		
		//第一位放置null
		uis.add(null);
		
		//match xml and testcase
		List<Element> xpaths = targets.elements("xpath");
		for(int k=1;k<=uimap.size();k++){
			for(int i=0;i<xpaths.size();i++){
				if(xpaths.get(i).attributeValue("name").equals(uimap.get("step"+k))){
					uis.add(xpaths.get(i).attributeValue("type"));//操作类型
					uis.add(xpaths.get(i).getText());             //测试数据
//					System.out.println(xpaths.get(i).attributeValue("type"));
//					System.out.println(xpaths.get(i).getText());
				}
			}
		}
	}
	
	
	public static String getData(int index){
		return uis.get(index);
	}
	
	
	
	public static void main(String args[]) throws DocumentException{
		GetPages.match();
		GetPages.getUiMapDatas();
		System.out.println(uis.size());
		System.out.println(getData(8));
	}
}
