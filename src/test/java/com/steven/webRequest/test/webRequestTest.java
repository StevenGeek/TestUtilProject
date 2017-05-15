package com.steven.webRequest.test;

import com.steven.util.WebRequestUtil;

import java.io.IOException;

import org.junit.Test;

/**
 * Created by zhangyu.chen.o on 2017/5/15.
 */
public class webRequestTest {
    @Test
    public void testWebRequest() throws IOException {
        WebRequestUtil.get("http://localhost:8001/health", null);
        System.out.print("done");
    }
}
