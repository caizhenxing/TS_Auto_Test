package ts.dw.reptile;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ts.dw.data.ReadIni;

/*
 * 解析HTML类
 */
public class Reptile {
	static String url = ReadIni.getSetting()[1];
	public static void toGetUI() throws IOException{
		Document doc = Jsoup.connect(url).get();
		Element body = doc.body();
		Elements input = body.select("input");
		for(int i=0;i<input.size();i++){
			if(!input.get(i).id().isEmpty()){
				System.out.println("id:" + input.get(i).id());
			}else if(!input.get(i).className().isEmpty()){
				System.out.println("classname:" + input.get(i).className());
			}
		}
	}
	
	public static void main(String args[]) throws IOException{
		Reptile.toGetUI();
	}
}
