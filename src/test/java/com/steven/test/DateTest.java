package com.steven.test;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;

public class DateTest {
	@Test
	public void Test(){
		List<String> aList = Arrays.asList("zhangyu.chen.o","dongshun");
	}
	/**
	 * 测试不同时区的时间比较
	 * @throws ParseException
	 */
	@Test
	public void testDifferTimeZoneCompare() throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		//初始化正好相差8小时的两个时区，实际处在同一时间点
		String date00 = "2017-04-10T04:58:06.000+0000";
		String date08 = "2017-04-10T12:58:06.000+0800";
		Date date00Compare = simpleDateFormat.parse(date00);
		//测试经过Long转换后是否保留时区信息
		Long date08Long = simpleDateFormat.parse(date08).getTime();
		Date date08Compare = new Date(date08Long);
		//断言转换后依旧保留时区信息且与0时区的时间相等;并打印
		Assert.assertEquals(date08, simpleDateFormat.format(date08Compare));
		System.out.println(date08 + "  equals  " + simpleDateFormat.format(date08Compare));
		//断言调整后不同时区相同时间点的两个Date对象相等
		Assert.assertEquals(simpleDateFormat.parse(date08), date00Compare);
		//断言时间Compare结果为相等的0
		Assert.assertEquals(0,date00Compare.compareTo(date08Compare));
		System.out.println(date00Compare.compareTo(date08Compare));
		System.out.println("date00:  " + date00);
		System.out.println("date08:  " + simpleDateFormat.format(date08Compare));
	}

}
