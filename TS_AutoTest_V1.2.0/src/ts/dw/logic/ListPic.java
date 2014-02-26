package ts.dw.logic;

import java.io.File;

public class ListPic {
	
	//写日志
	//static ToLog log = new ToLog();
	
	 public static void listPic(String picPath) throws Exception{
        File f=null;       
        f=new File(picPath);
        File[] list=f.listFiles();
        for(int i=0;i<list.length;i++)
        	if(!list[i].isDirectory()){
//        		System.out.println("文件");
//              System.out.println("path=" + list[i].getPath());
//              System.out.println("absolutepath=" + list[i].getAbsolutePath());
//              System.out.println("name=" + list[i].getName());
//        		System.out.println(list[i].getName());
        		
        		//将图片写入日志文件
        		//log.toLog("<img src='"+picPath+list[i].getName()+"'/>");
        	}       
       }
//	 public static void main(String args[]) throws Exception{
//		 listPic("F:\\JavaWorkSpace\\XMLSeleniumDemo\\screenshot\\");
//	 }
}
