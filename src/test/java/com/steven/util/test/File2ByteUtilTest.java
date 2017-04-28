package com.steven.util.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.steven.util.File2ByteUtil;

public class File2ByteUtilTest {
	@Test
	public void testImageToHex() throws Exception{
		byte[] data = File2ByteUtil.image2byte("C:\\Users\\zhangyu.chen.o\\Pictures\\验证码6.jpg");
		String hex = File2ByteUtil.byte2String(data);
		byte[] returnData = File2ByteUtil.hex2byte(hex);
		BufferedImage image = (BufferedImage) File2ByteUtil.byte2image(data);
		OutputStream os = new FileOutputStream(new File("D:\\qqqq.png"));
		ImageIO.write(image, "png", os);
	}
}
