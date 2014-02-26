package ts.dw.logic;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import ts.dw.data.CreateXML;
import ts.dw.data.ReadExcel;

import com.thoughtworks.selenium.SeleneseTestBase;



public class TS_Auto_Main extends SeleneseTestBase {
	private WebDriver driver;
	private List<Element> stepsNodes;
	private Document document = null;
	//根节点
	private Element root;
	//seetings节点
	private Element settings;
	//Test节点
	private Element testElm;
	
	//延时
	private int timeout;
	/*
	 * 延时比率,如需延时时间缩短只需减小rate的值即可(一般建议在500~1000之间)
	 */
	private int rate = 1000;
	
	/*
	 * 实例化操作页面元素类
	 */
	Framework_core fc = new Framework_core();
	
	//写日志
	ToLog log = new ToLog();
	
	//生成报告
	static HTMLLOG hl = new HTMLLOG();

	/*
	 * 初始方法
	 */
	@SuppressWarnings("unchecked")
	public void setUpBeforeClass() throws Exception {
		// settings
		String ChromeDriverPath = "driver\\chromedriver.exe";
		String application_url = "";
		String browser = "";
	
		//框架开始前先读取测试用例并生成XML格式用例
		ReadExcel re = new ReadExcel();
		re.readExcel();
		CreateXML.generateXmlFile();
		
		
		//获取用例文件的路径
		String ucXMLFile = System.getProperty("user.dir") + File.separator + "UseCases" + File.separator + "xml" + File.separator +CreateXML.xmlCase;

		try {
			System.out.println("测试用例XML文件位置::" + ucXMLFile);
			document = new SAXReader().read(new File(ucXMLFile));
		} catch (DocumentException e) {
			log.toLog(e.toString());
			e.printStackTrace();
		}
		root = document.getRootElement();
		// settings element
		//读取XML中的配置信息
		settings = root.element("SETTINGS");
		if (settings != null) {
			//测试使用的浏览器
			browser = settings.attributeValue("browser");
			//测试网址
			application_url = settings.attributeValue("application_url");
			//设置延时
			timeout = Integer.parseInt(settings.attributeValue("timeout"));
		}

		// TEST element
		testElm = root.element("TEST");
		System.out.println("测试用例名称为::" + testElm.attributeValue("name"));
		System.out.println("延时设置为::" + timeout + "秒");
		
		if(browser.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver",ChromeDriverPath);
			driver = new ChromeDriver();
			
		}
		if(browser.equals("IE")){
			driver = new InternetExplorerDriver();
		}
		if(browser.equals("Firefox")){
			driver = new FirefoxDriver();
		}
		
		System.out.println("正在启动TS_AutoTest框架。。。");
		stepsNodes = testElm.element("STEPS").elements();
		driver.get(application_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	/*
	 * 结束方法
	 */
	public void tearDownAfterClass() throws Exception {
		if (driver != null) {
			System.out.println("=============================================================>");
			System.out.println("停止TS_AutoTest框架!");
			System.out.println("=============================================================>");
			System.out.println("已生成报告!");
			Thread.sleep(timeout*2*rate);
			driver.quit();
		}
	}

	/*
	 * 测试核心
	 */
	public void test() {
		try {
			//生成HTMLLOG模版
			hl.generateReport();
			//写报告
			hl.sendStatusToReport(testElm.attributeValue("name"), "0", "测试步骤", "测试数据", "预期结果", "实际结果");
		} catch (Exception e) {
			log.toLog(e.toString());
			e.printStackTrace();
		}
		
		Element elm = null;
		// 循环解析测试步骤
		Iterator<Element> iterator = null;
		iterator = stepsNodes.iterator();
		while (iterator != null && iterator.hasNext()) {// while循环开始
			elm = iterator.next();
			System.out.println("=============================================================>");
			System.out.println("step index=" + elm.attributeValue("index"));
			System.out.println("step name=" + elm.attributeValue("name")+":  "+elm.element("VALUE").getText());
			System.out.println("step type=" + elm.attributeValue("type"));
			System.out.println("step verify=" + elm.attributeValue("verify"));
			
			/*
			 * 获取操作类型以及预期结果
			 */
			String type = elm.attributeValue("type");
			String verify = elm.attributeValue("verify");
			if (type == null) {
				SeleneseTestBase.fail("必须定义type属性，请检查XML测试用例。");
			}
			
			//得到预期结果和实际结果的对比结果
			int actual;
			//结果pass or fail
			String result = "";
			
			
			//判断操作类型,调用相应操作方法
			if (type.equals("Type.Click")) {
				pause(timeout*rate);
				// 处理Click单击操作
				try {
					String xpValue = elm.element("XPATH").getText();
					//传入驱动类型,Xpath以及预期结果
					actual = fc.fun_click(driver, By.xpath(xpValue),verify);
					if(actual==1){
						System.out.println("【Pass】");
						result = "Pass";
					}else{
						System.out.println("【Fail】");
						result = "Fail";
					}
				} catch (Exception e) {
					fc.takeScreenShot(driver);
					//失败则写进失败日志
					log.toLog(e.toString());
					System.out.println("【Fail】");
					//执行结果
					result = "Fail";
				}
			} else if (type.equals("Type.Input")) {
				pause(timeout*rate);
				// 处理Input 在输入框执行输入操作
				try {
					String inValue = elm.element("VALUE").getText();
					String xpValue = elm.element("XPATH").getText();
					actual = fc.fun_input(driver, By.xpath(xpValue), inValue,verify);
					if(actual==1){
						System.out.println("【Pass】");
						result = "Pass";
					}else{
						System.out.println("【Fail】");
						result = "Fail";
					}
				} catch (Exception e) {
					fc.takeScreenShot(driver);
					//失败则写进失败日志
					log.toLog(e.toString());
					System.out.println("【Fail】");
					//执行结果
					result = "Fail";
				}
			} else if(type.equals("Type.Select")){
				pause(timeout*rate);
				try{
					String inValue = elm.element("VALUE").getText();
					String xpValue = elm.element("XPATH").getText();
					actual = fc.fun_select(driver,By.xpath(xpValue),inValue);
					if(actual==1){
						System.out.println("【Pass】");
						result = "Pass";
					}else{
						System.out.println("【Fail】");
						result = "Fail";
					}
				}catch(Exception e){
					fc.takeScreenShot(driver);
					//失败则写进失败日志
					log.toLog(e.toString());
					System.out.println("【Fail】");
					//执行结果
					result = "Fail";
				}
			}else if(type.equals("Type.Redirect")){
				// 处理重定向操作
				pause(timeout*rate);
				try{
					String reValue = elm.element("VALUE").getText();
					actual = fc.fun_Redirect(driver, reValue);
					if(actual==1){
						System.out.println("【Pass】");
						result = "Pass";
					}else{
						System.out.println("【Fail】");
						result = "Fail";
					}
				}catch (Exception e) {
					fc.takeScreenShot(driver);
					//失败则写进失败日志
					log.toLog(e.toString());
					System.out.println("Fail");
					//执行结果
					result = "Faill";
				}
			}else if(type.equals("Type.CaptureScreenshot")){
				pause(timeout*rate);
				fc.takeScreenShot(driver);
				result = "Pass";
			}
			//每一步执行完成后记录结果并写入日志
			try {
				hl.sendStatusToReport("测试步骤", elm.attributeValue("index"), elm.attributeValue("name"), elm.element("VALUE").getText(), elm.attributeValue("verify"), result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// 循环解析测试步骤；while循环结束
	}
	
	
	/*
	 * main方法
	 */
	public static void main(String args[]){
		TS_Auto_Main ts = new TS_Auto_Main();
		try {
			ts.setUpBeforeClass();
			ts.test();
			ts.tearDownAfterClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
