package ts.dw.logic;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import ts.dw.data.ReadIni;

/*
 * 处理页面元素
 * 得到实际结果
 * 判断测试是否通过
 */
public class Framework_core {
	
	WebElement element;
	Select select;
	Alert alert;
	int timeout;
	String text;
	//最终的测试结果
	int reslut;
	/*
	 * 延时比率,建议100~1000之间
	 */
	private static int rate = 100;
	
	
	/*
	 * 按钮等点击操作
	 * 得到实际结果与预期结果相比较
	 */
	public int fun_click(WebDriver driver,By location,String verify){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(location);
		element.click();
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 文本框输入操作
	 */
	public int fun_input(WebDriver driver,By location,String value,String verify){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(location);
		element.clear();
		element.sendKeys(value);
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 下拉选择框
	 */
	public int fun_select(WebDriver driver,By location,int index){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		select = new Select(driver.findElement(location));
		/*
		 * 按索引
		 */
		select.selectByIndex(index);
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reslut = 1;
		return reslut;
	}
	/*
	 * 重构方法
	 * 按值
	 */
	public int fun_select(WebDriver driver,By location,String value){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		select = new Select(driver.findElement(location));
		/*
		 * 按值
		 */
		select.selectByValue(value);
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 单选项
	 */
	public int fun_radio(WebDriver driver,By location){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(location);
		/*
		 * 判断是否选中
		 */
		if(element.isSelected()){
			//已选中则清空
			//element.clear();
		}else{
			//未选中则选择
			element.click();
		}
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 多选项
	 */
	public int fun_checkbox(WebDriver driver,By location){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(location);
		// 判断多选项是否可用
		if(element.isEnabled()){
			if(element.isSelected()){
				//已选中
			}else{
				//未选中
				element.click();
			}
		}
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 处理JS弹出框
	 */
	public void fun_alert(WebDriver driver,boolean option){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		alert = driver.switchTo().alert();
		if(option){
			//确定
			alert.accept();
		}else{
			//取消
			alert.dismiss();
		}
	}
	
	/*
	 * 非JS弹窗
	 */
	public void fun_windows(WebDriver driver,String type,By location,String value,String verify){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.switchTo().window(driver.getWindowHandles().iterator().next());
		if(type.equals("Type.Click")){
			fun_click(driver, location, verify);
		}else if(type.equals("Type.Input")){
			fun_input(driver, location, value, verify);
		}
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 重定向
	 */
	public int fun_Redirect(WebDriver driver,String url){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.switchTo().window(driver.getWindowHandles().iterator().next());
		driver.navigate().to(url);
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		reslut = 1;
		return reslut;
	}
	
	/*
	 * 获取文字
	 */
	public String fun_getText(WebDriver driver,By location){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(location);
		text = element.getText();
		try {
			Thread.sleep(Integer.parseInt(ReadIni.getSetting()[3])*rate);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	/*
	 * fileName 保存截图的文件名
	 */
	public void takeScreenShot(WebDriver driver){
		String dir_name = "screenshot";		//定义一个截图存放的目录名,此处为当前目录的screenshot目录下
		
		//判断目录是否存在
		if(!(new File(dir_name).isDirectory())){
			//如果不存在则新建目录
			new File(dir_name).mkdir();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
		//格式化当前时间，例如20120406-165210
		String time = sdf.format(new Date());
		
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);   
        try{
           //将截图存放到指定目录,并以当前时间戳作为文件名保存
           FileUtils.copyFile(scrFile, new File(dir_name + File.separator + time + ".png"));
           System.out.println("【Pass】");
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
	
	
	/*
	 * 判断一个元素是否存在
	 */
	public boolean isElementExsit(WebDriver driver, By locator) {
		boolean flag = false;
		try {
			WebElement element=driver.findElement(locator);
			flag=null!=element;
			System.out.println("元素: " + locator.toString()+ " 存在!");
		}catch(NoSuchElementException e) {
			System.out.println("元素: " + locator.toString()+ " 不存在!");
			flag = false;
		}
		return flag;
	}	
}
