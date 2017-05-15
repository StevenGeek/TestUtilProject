package com.steven.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * zhangyu.chen.o
 */
public class WebRequestUtil {
    private static HttpURLConnection c_HttpURLConnection;
    private final static int READ_TIME_OUT = 30000;
    private final static int CONNECTIONG_TIME_OUT = 10000;
    /**
     * 发送webGet请求
     * 
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static String get(String m_Url, Map<String, String> p_Params, Map<String, String> p_headers) throws IOException {
        return readResponse(getAsInputStream(m_Url, p_Params,  p_headers));
    }

    /**
     *发送get请求，返回Inputstream
     * @param m_Url 请求地址
     * @param p_Params 请求参数Map（？&形式加入URL）
     * @param p_headers 请求头Map
     * @return Inputstream
     * @throws IOException
     */
    public static InputStream getAsInputStream(String m_Url, Map<String, String> p_Params, Map<String, String> p_headers) throws IOException {
        m_Url = setParams(m_Url, p_Params);
        URL m_UrlUtil = new URL(m_Url);
        c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
        setConnectionProperty();
        c_HttpURLConnection.setRequestMethod("GET");
        c_HttpURLConnection.setRequestProperty("Content-Type", "application/json");
        c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
        c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
        for (Map.Entry<String, String> entry : p_headers.entrySet()) {
			c_HttpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
		}
        return c_HttpURLConnection.getInputStream();
    }
    
    /**
     * 发送webGet请求
     * 
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static String get(String m_Url, Map<String, String> p_Params) throws IOException {
        return readResponse(getAsInputStream(m_Url,  p_Params));
    }
    
    public static InputStream getAsInputStream(String m_Url, Map<String, String> p_Params) throws IOException {
        m_Url = setParams(m_Url, p_Params);
        URL m_UrlUtil = new URL(m_Url);
        c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
        setConnectionProperty();
        c_HttpURLConnection.setRequestMethod("GET");
        c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
        c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
        c_HttpURLConnection.setRequestProperty("Content-Type", "application/json");
        return c_HttpURLConnection.getInputStream();
    }
    
    /**
     * 发送webGet请求
     * 
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static <T> String get(String m_Url, T p_Params)
            throws IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        InputStream m_InputStream = null;
        m_Url = setParams(m_Url, p_Params);
        URL m_UrlUtil = new URL(m_Url);
        c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
        setConnectionProperty();
        c_HttpURLConnection.setRequestMethod("GET");
        c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
        c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
        c_HttpURLConnection.setRequestProperty("Content-Type", "application/json");
        m_InputStream = c_HttpURLConnection.getInputStream();
        return readResponse(m_InputStream);
    }

    /**
     * 发送webPOST请求
     * 
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String m_Url, Map<String, String> p_Params, String p_Request) throws IOException {

        OutputStream m_RequestStream = null;
        InputStream m_ResponseStream = null;
        try {
            m_Url = setParams(m_Url, p_Params);
            URL m_UrlUtil = new URL(m_Url);
            c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
            setConnectionProperty();
            c_HttpURLConnection.setRequestMethod("POST");
            c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
            c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
            c_HttpURLConnection.setRequestProperty("Content-Type", "application/json");
            m_RequestStream = c_HttpURLConnection.getOutputStream();
            m_ResponseStream = c_HttpURLConnection.getInputStream();
            m_RequestStream.write(p_Request.getBytes("UTF-8"));
            return readResponse(m_ResponseStream);
        }
        finally {
            if(m_RequestStream != null) {
                m_RequestStream.flush();
                m_RequestStream.close();
            }
        }
    }

    /**
     * 发送webPOST请求
     *
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String m_Url, Map<String, String> p_Params, String p_Request, String contentType) throws IOException {
        OutputStream m_RequestStream = null;
        try {
            m_Url = setParams(m_Url, p_Params);
            URL m_UrlUtil = new URL(m_Url);
            c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
            setConnectionProperty();
            c_HttpURLConnection.setRequestMethod("POST");
            c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
            c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
            c_HttpURLConnection.setRequestProperty("Content-Type", contentType);
            m_RequestStream = c_HttpURLConnection.getOutputStream();
            m_RequestStream.write(p_Request.getBytes());
            m_RequestStream.flush();
            m_RequestStream.close();
            InputStream m_ResponseStream = c_HttpURLConnection.getInputStream();
            return readResponse(m_ResponseStream);
        }
        finally {
            if(m_RequestStream!=null){
                m_RequestStream.close();
            }
        }
    }

    /**
     * 发送webPOST请求
     *
     * @param m_Url 请求Url
     * @param p_Params 请求参数map，（?key=value形式）
     * @return 响应字符串
     * @throws IOException
     */
    public static String post(String m_Url, Map<String, String> p_Params, Map p_Request, String contentType) throws IOException {
        OutputStream m_RequestStream = null;
        try {
            m_Url = setParams(m_Url, p_Params);
            URL m_UrlUtil = new URL(m_Url);
            c_HttpURLConnection = (HttpURLConnection) m_UrlUtil.openConnection();
            setConnectionProperty();
            c_HttpURLConnection.setRequestMethod("POST");
            c_HttpURLConnection.setReadTimeout(READ_TIME_OUT);
            c_HttpURLConnection.setConnectTimeout(CONNECTIONG_TIME_OUT);
            c_HttpURLConnection.setRequestProperty("Content-Type", contentType);
            m_RequestStream = c_HttpURLConnection.getOutputStream();
            String bodyParam = formatBodyMapParams(p_Request);
            m_RequestStream.write(bodyParam.getBytes("UTF-8"));
            m_RequestStream.flush();
            m_RequestStream.close();
            InputStream m_ResponseStream = c_HttpURLConnection.getInputStream();
            return readResponse(m_ResponseStream);
        }
        finally {
            if(m_RequestStream!=null){
                m_RequestStream.close();
            }
        }
    }

    /**
     * 转换Json到实体类
     * 
     * @param p_Json Json字串
     * @param p_Clazz 实体类类型
     * @return 实体类
     */
    public static <T> T convertJson2Obj(String p_Json, Class<T> p_Clazz) throws IOException {
        ObjectMapper m_Mapper = new ObjectMapper();
        return m_Mapper.readValue(p_Json, p_Clazz);
    }

    /**
     * 设置HTTPHeadFields
     */
    private static void setConnectionProperty() {
        c_HttpURLConnection.setDoOutput(true);
        c_HttpURLConnection.setDoInput(true);
        c_HttpURLConnection.setDefaultUseCaches(false);
    }

    /**
     * 读取响应流转成String 默认UTF-8
     * 
     * @param p_InputStream 响应流
     * @return 响应String
     * @throws IOException
     */
    private static String readResponse(InputStream p_InputStream) throws IOException {
        try {
            if(null == p_InputStream){
                return null;
            }
            BufferedReader m_BufferedReader = new BufferedReader(new InputStreamReader(p_InputStream, "UTF-8"));
            String m_ReadLine = null;
            StringBuilder m_StringBuilder = new StringBuilder();
            while ((m_ReadLine = m_BufferedReader.readLine()) != null) {
                m_StringBuilder.append(m_ReadLine);
            }
            return m_StringBuilder.toString();
        }
        finally {
            if(p_InputStream != null){
                p_InputStream.close();
            }
        }
    }

    /**
     * 设置URL请求参数
     * 
     * @param m_Url URL
     * @param p_Params 参数Map
     * @return
     */
    private static String setParams(String m_Url, Map<String, String> p_Params) {
        if (p_Params != null && !p_Params.isEmpty()) {
            Iterator<String> m_ParamkeyKeyIterator = p_Params.keySet().iterator();
            String m_FirstParamKey = m_ParamkeyKeyIterator.next();
            m_Url += "?" + m_FirstParamKey + "=" + p_Params.get(m_FirstParamKey);
            while (m_ParamkeyKeyIterator.hasNext()) {
                String m_IteratorKey = m_ParamkeyKeyIterator.next();
                m_Url += "&" + m_IteratorKey + "=" + p_Params.get(m_IteratorKey);
            }
        }
        return m_Url;
    }

    /**
     * 设置body键值对请求参数
     *
     * @param p_Params 参数Map
     * @return
     */
    private static String formatBodyMapParams(Map<String, String> p_Params) {
        String result ="";
        if (p_Params != null && !p_Params.isEmpty()) {
            Iterator<String> m_ParamkeyKeyIterator = p_Params.keySet().iterator();
            while (m_ParamkeyKeyIterator.hasNext()) {
                String m_IteratorKey = m_ParamkeyKeyIterator.next();
                result += "&" + m_IteratorKey + "=" + p_Params.get(m_IteratorKey);
            }
            result = result.substring(1,result.length());
        }
        return result;
    }

    /**
     * 设置URL请求参数
     * 
     * @param m_Url URL
     * @param p_Params 参数Map
     * @return
     */
    private static <T> String setParams(String m_Url, T p_Params)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = getNameAndValue(p_Params, new HashMap<>(), p_Params.getClass());
        if (map != null && !map.isEmpty()) {
            Iterator<String> m_ParamkeyKeyIterator = map.keySet().iterator();
            String m_FirstParamKey = m_ParamkeyKeyIterator.next();
            m_Url += "?" + m_FirstParamKey + "=" + map.get(m_FirstParamKey);
            while (m_ParamkeyKeyIterator.hasNext()) {
                String m_IteratorKey = m_ParamkeyKeyIterator.next();
                m_Url += "&" + m_IteratorKey + "=" + map.get(m_IteratorKey);
            }
        }
        return m_Url;
    }

    private static <T> Map<String, Object> getNameAndValue(T obj, Map<String, Object> resultMap, Class<?> clazz)
            throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (clazz.getSuperclass() != null) {
            getNameAndValue(obj, resultMap, clazz.getSuperclass());
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isTransient(field.getModifiers())) {// 浣跨敤cobertura鐢熸垚娴嬭瘯鎶ュ憡鐢ㄥ埌,璺宠繃cobertura娣诲姞鐨勮窡韪睘鎬?
                Method method = null;
                Object value = null;
                String name = field.getName();
                StringBuilder getFieldMethodName = new StringBuilder("get").append(name.substring(0, 1).toUpperCase()).append(name.substring(1));
                method = clazz.getMethod(getFieldMethodName.toString());
                value = method.invoke(obj);
                if (null==value) {
                    continue;
                }
                resultMap.put(name, value);
            }
        }
        return resultMap;
    }
}
