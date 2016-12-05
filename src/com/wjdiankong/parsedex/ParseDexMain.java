package com.wjdiankong.parsedex;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class ParseDexMain {
	
	public static void main(String[] args){
		
		byte[] srcByte = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream("dex/Hello.dex");
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
			srcByte = bos.toByteArray();
		}catch(Exception e){
			System.out.println("read res file error:"+e.toString());
		}finally{
			try{
				fis.close();
				bos.close();
			}catch(Exception e){
				System.out.println("close file error:"+e.toString());
			}
		}
		
		if(srcByte == null){
			System.out.println("get src error...");
			return;
		}
		
		System.out.println("ParseHeader:");
		ParseDexUtils.praseDexHeader(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse StringIds:");
		ParseDexUtils.parseStringIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse StringList:");
		ParseDexUtils.parseStringList(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse TypeIds:");
		ParseDexUtils.parseTypeIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse ProtoIds:");
		ParseDexUtils.parseProtoIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse FieldIds:");
		ParseDexUtils.parseFieldIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse MethodIds:");
		ParseDexUtils.parseMethodIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse ClassIds:");
		ParseDexUtils.parseClassIds(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse MapList:");
		ParseDexUtils.parseMapItemList(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse Class Data:");
		ParseDexUtils.parseClassData(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
		System.out.println("Parse Code Content:");
		ParseDexUtils.parseCode(srcByte);
		System.out.println("++++++++++++++++++++++++++++++++++++++++");
		
	}

}
