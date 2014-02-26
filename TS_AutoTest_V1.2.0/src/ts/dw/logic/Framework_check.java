package ts.dw.logic;

/*
 * 返回一个结果
 * 1代表pass
 * 0代表faile
 */
public class Framework_check {
	public static int checkResult(String verify,String actual){
		
		if(verify.equals(actual)){
			return 1;
		}else{
			return 0;
		}

	}
}
