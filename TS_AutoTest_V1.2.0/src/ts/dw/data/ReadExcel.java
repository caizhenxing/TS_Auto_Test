package ts.dw.data;

// 读取Excel的类 
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;




/* 重要信息:
 * getData(1):测试用例标题
 * getData(2):使用的浏览器
 * getData(3):目标URL
 * getData(7):"用例编号"
 * getData(15):用例步骤的第一步(1)     第二步的则+6    15+(stepNum-1)*6
 * getData(16):用例步骤第一步的操作类型     第二步的则+6
 * getData(17):用例第一步的操作内容    第二步的则+6
 * getData(18):用例第一步的输入数据(适合type.input操作类型)    第二步的则+6
 * getData(19):用例第一步的预期结果    第二步的则+6
 * 
 */

public class ReadExcel{
	static String result;
	static Workbook book;
	static Sheet sheet;
	static int colNums,rowNums;	//列、行
	//保存Excel中的数据
	private static List<String> datas = new ArrayList<String>();
	
    public static void main(String args[]){
    	ReadExcel re = new ReadExcel();
    	re.readExcel();
    	System.out.println(stepNums());
    	ReadExcel.getData(15);	//获取第list中二个数据
    }
    
    
    //将Excel中的数据存入list中
    public int readExcel(){
    	List<String> datas = new ArrayList<String>();
    	try{
            book = Workbook.getWorkbook(new File("UseCases\\"+ReadIni.getSetting()[2]));
            //获得工作表对象 
            sheet = book.getSheet(0);
            
            //获取列数和行数
            colNums = sheet.getColumns();
            rowNums = sheet.getRows();
            //System.out.println(colNums+" 列");
    		//System.out.println(rowNums+" 行");
	
    		//将list第一个位置填充为null
    		datas.add(null);
    		
            //循环得到第column列第row行的单元格,第一行第一列不存
    		for(int row_index = 0;row_index < rowNums;row_index++){
    			for(int col_index = 0;col_index< colNums;col_index++){
    				//第col_index列，第row_index行,getCell(列,行)
    				Cell cell = sheet.getCell(col_index,row_index);
    				datas.add(cell.getContents());
//    				System.out.print(cell.getContents()+"  ");
    			}
//    			System.out.println();
    		}
    		ReadExcel.setDatas(datas);
            book.close();
         }catch(Exception e){
           e.printStackTrace();
         }
         return rowNums;
    }
    
    /*
     * 获取list中的数据
     * @param list索引:1-6为用例名称,15开始为具体用例
     * @return 该索引保存的数据
     */
    public static String getData(int index){
    	/*
    	 * Excel中每行6列
    	 * datas.size()-1 为实际单元格数
    	 * (datas.size()-1-2*6) 为实际步骤占的单元格数
    	 * 所以表格中一个用几个操作步骤的算法:(datas.size()-1-2*6)/6
    	 */
    	//System.out.print(datas.size());	//调试
    	//System.out.print(datas.get(index));	//调试
    	return datas.get(index);
    }
    
    
    /*
     * 返回Excel中用例步骤的行数
     */
    public static int stepNums(){
    	return (getDatas().size()-1-2*6)/6;
    }


	public static List<String> getDatas() {
		return datas;
	}


	public static void setDatas(List<String> datas) {
		ReadExcel.datas = datas;
	}
	
}


