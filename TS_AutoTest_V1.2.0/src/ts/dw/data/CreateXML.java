package ts.dw.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import ts.dw.pages.GetPages;


public class CreateXML {
	
	public static String xmlCase = "ts.xml";

	public static void main(String args[]) throws DocumentException {
		ReadExcel re = new ReadExcel();
		re.readExcel();	//读取Excel
		
		generateXmlFile();
	}
	
	 /**
     * 生成xml文档
     * @param user 用户信息
     * @return
	 * @throws DocumentException 
     */
    private static Document generateDocument() throws DocumentException {
        
        Document document = DocumentHelper.createDocument();
        //匹配
		GetPages.match();
        //给XML添加外部样式表
        Map<String, String> inMap = new HashMap<String, String>();
        inMap.put("type", "text/xsl");
        inMap.put("href", "./SeleniumUseCase.xsl");
        document.addProcessingInstruction("xml-stylesheet", inMap);
        
        
        //添加元素 SUITE
        Element suiteElement = document.addElement("SUITE");
        
        //给SUITE元素添加属性
        suiteElement.addAttribute("name", "致友微博 测试用例 By Kiven");
        
        //添加元素SETTINGS
        Element settingElement = suiteElement.addElement("SETTINGS");
        //给SETTINGS添加属性
        settingElement.addAttribute("browser", ReadIni.getSetting()[0]);
        settingElement.addAttribute("application_url", ReadIni.getSetting()[1]);
        settingElement.addAttribute("timeout", ReadIni.getSetting()[3]);
        //添加TEST元素
        Element testElement = suiteElement.addElement("TEST");
        //给TEST添加属性
        testElement.addAttribute("name", ReadExcel.getData(1));
        //添加TEST的子元素STEPS
        Element stepsElement = testElement.addElement("STEPS");
        
        //判断步骤数
        int stepNums = ReadExcel.stepNums();
        
        /*
         * 第一个步骤的用例编号在list中的位置:13+(stepNums-1)*6
         */
        
        
        for(int i = 1;i <= stepNums;i++){
        
	        //给STEPS添加子元素STEP
	        Element stepElement = stepsElement.addElement("STEP");
	        //给STEP添加属性
	        stepElement.addAttribute("index", ReadExcel.getData(13+(i-1)*6));
	        stepElement.addAttribute("type", GetPages.getData(2*i-1));
	        stepElement.addAttribute("name", ReadExcel.getData(15+(i-1)*6));
	        stepElement.addAttribute("verify", ReadExcel.getData(17+(i-1)*6));
	        //给STEP添加子元素
	        Element xpathElement = stepElement.addElement("XPATH");
	        Element valueElement = stepElement.addElement("VALUE");
	        //给XPATH、VALUE元素赋值
	        xpathElement.setText(GetPages.getData(2*i));
	        
	        valueElement.setText(ReadExcel.getData(16+(i-1)*6));
        }
        
        return document;
    }
    
    
    /**
     * 生成xml文件
     * @throws DocumentException 
     */
    public static void generateXmlFile() throws DocumentException {
        
        Document document = generateDocument();
        XMLWriter out = null;
        
        BufferedWriter bw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        
        try {
            File xmlFile = new File("UseCases\\xml\\"+xmlCase);//输出xml的路径
            if (xmlFile.exists()) {
            	//如果已经存在则删除
            	xmlFile.delete();
            } 
            fos = new FileOutputStream(xmlFile);
            osw = new OutputStreamWriter(fos,"UTF-8");//指定编码，防止写中文乱码
            bw = new BufferedWriter(osw);
            
            //对xml输出格式化
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            out = new XMLWriter(bw, format);
            out.write(document);
            
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(bw != null) {
                    bw.close();
                }
                if(osw != null) {
                    osw.close();
                }
                if(fos != null) {
                    fos.close();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
