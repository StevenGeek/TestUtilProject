package com.steven.util;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
/**
 * 文件、二进制、十六进制 互转工具类
 * @author zhangyu.chen.o
 *
 */
public class File2ByteUtil {
	/**
	 * 图片转换二进制
	 * @param path 图片文件路径
	 * @return 二进制数据
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static byte[] image2byte(String path) throws FileNotFoundException, IOException{
		byte[] data = null;
		FileImageInputStream fileImageInputStream = null;
		fileImageInputStream = new FileImageInputStream(new File(path));
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int numBytesRead = 0;
		while((numBytesRead = fileImageInputStream.read(buf))!=-1){
			byteArrayOutputStream.write(buf,0,numBytesRead);
		}
		data = byteArrayOutputStream.toByteArray();
		byteArrayOutputStream.close();
		fileImageInputStream.close();
		return data;
	}
	/**
	 * 二进制转换图片
	 * @param data 二进制数据
	 * @return 图片
	 * @throws IOException
	 */
	public static BufferedImage byte2image(byte[] data) throws IOException{
		if (data.length<3){
			return null;
		}
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
		BufferedImage image = ImageIO.read(byteArrayInputStream);
		return image;
	}
	/**
	 * 二进制转换为16进制数据
	 * @param data 二进制数据
	 * @return 16进制数据
	 */
	public static String byte2String (byte[] data){
		if(null==data||data.length<1){
			return "0x";
		}else if (data.length>200000){
			return "0x";
		}else{
			StringBuffer sBuffer = new StringBuffer();
			int buf[] = new int[data.length];
			//byte数组转换为十进制
			for (int i =0;i<data.length;i++) {
				buf[i]=data[i]<0?(data[i]+256):(data[i]);
			}
			//转换16进制
			for(int k=0;k<buf.length;k++){
				if(buf[k]<16){
					sBuffer.append("0" + Integer.toHexString(buf[k]));
				}else{
					sBuffer.append(Integer.toHexString(buf[k]));
				}
			}
			return "0x" + sBuffer.toString().toUpperCase();
		}
	}
	public static byte[] hex2byte(String hex) throws Exception{
		if(null == hex || "".equals(hex)){
			return null;
		}else if (hex.length()%2!=0){
			throw new Exception("hex%2  !=0 Error");
		}else{
			char[] hexChar = hex.toCharArray();
			if ("0X".equals((hex.substring(0, 2).toUpperCase()))){
				hexChar = hex.substring(2).toCharArray();
			}
			int length = hexChar.length/2;
			byte[] data = new byte[length];
			for(int i =0;i<length;i++){
				int pos = i*2;
				data[i] = (byte) (char2byte(hexChar[pos])<<4|char2byte(hexChar[pos+1]));
			}
			return data;
		}
	}
	/**
	 * char转换对应byte
	 * @param c
	 * @return
	 */
	public static byte char2byte(char c){
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
