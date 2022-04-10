package com.test.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用HttpClient发送请求、接收响应很简单，一般需要如下几步即可：
 *
 * 创建CloseableHttpClient对象。
 * 创建请求方法的实例，并指定请求URL。如果需要发送GET请求，创建HttpGet对象；如果需要发送POST请求，创建HttpPost对象。
 * 如果需要发送请求参数，可可调用setEntity(HttpEntity entity)方法来设置请求参数。setParams方法已过时（4.4.1版本）。
 * 调用HttpGet、HttpPost对象的setHeader(String name, String value)方法设置header信息，或者调用setHeaders(Header[] headers)设置一组header信息。
 * 调用CloseableHttpClient对象的execute(HttpUriRequest request)发送请求，该方法返回一个CloseableHttpResponse。
 * 调用HttpResponse的getEntity()方法可获取HttpEntity对象，该对象包装了服务器的响应内容。程序可通过该对象获取服务器的响应内容；调用CloseableHttpResponse的getAllHeaders()、getHeaders(String name)等方法可获取服务器的响应头。
 * 释放连接。无论执行方法是否成功，都必须释放连接
 *
 * @author: miao
 * @date 2022/3/30
 */

public class HttpSyncClient {

    private static Logger logger = LoggerFactory.getLogger(HttpSyncClient.class);

    public static void main(String[] args) throws NoSuchMethodException, IntrospectionException, InvocationTargetException, IllegalAccessException {

        String url = "http://localhost:8091/ppppecho";
        getHTTPReqeust(url, null);
        url = "http://localhost:8091/echosss";
        Map map = new HashMap();
        map.put("111", "222");
        postHttpRequestByBody(url, map);
//        LcSatisfactionSurvey lcSatisfactionSurvey = new LcSatisfactionSurvey();
//        lcSatisfactionSurvey.setOpenId("2222");
//        getFieldValue(lcSatisfactionSurvey, "openid");
//
//        setFieldValue(lcSatisfactionSurvey, LcSatisfactionSurvey.class, "companyName", String.class, "飞度");
    }





    /**
     * @param url 地址
     * @param map 参数
     */
    public static void getHTTPReqeust(String url, Map<String, String> map) {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        try {
            client = HttpClientBuilder.create().build();

            //创建URLBuilder
            URIBuilder uriBuilder = new URIBuilder(url);
            //设置参数
            if (!CollectionUtils.isEmpty(map)) {
                map.forEach(uriBuilder::addParameter);

            }
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            response = client.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获取响应体。相应数据是一个基于http协议标准字符串封装的对象
                //响应头和响应体都是封装的htto协议数据。 直接使用存在乱码或解析错误 需要解译
                HttpEntity entity = response.getEntity();
                String entityStr = EntityUtils.toString(entity, "UTF-8");
                System.out.println(entityStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String postHttpRequestByBody(String url , Map<String, Object> map) {
        CloseableHttpResponse response = null;
        CloseableHttpClient client = null;
        String result = "";
        // 超时配置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(3000) // 设置连接超时时间 毫秒
                .setConnectionRequestTimeout(3000)
                .setSocketTimeout(5000) // 设置请求获取数据的超时时间 毫秒
                .build();
        try {
            client = HttpClientBuilder.create()
            //        自定义请求失败重试机制
            //        .setRetryHandler(getCustomizeRetryHandler())
                    .setDefaultRequestConfig(requestConfig)
                    .build();
            // post 请求体传递参数， 需要定义请求体格式，默认表单格式
            logger.info("do-Post-Request, url: {}", url);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 超时配置
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000)
//                    .setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
//            httpPost.setConfig(requestConfig);

            // 获取响应体。相应数据是一个基于http协议标准字符串封装的对象
            // 响应头和响应体都是封装的http协议数据。 直接使用存在乱码或解析错误 需要解译

            JSONObject jsonObject = new JSONObject();
            if (!CollectionUtils.isEmpty(map)) {
                map.forEach(jsonObject::put);
            }
            HttpEntity entityStr = new StringEntity(jsonObject.toString(), "UTF-8");
            httpPost.setEntity(entityStr);
            logger.info("do-Post-Request, parameters: {}", jsonObject);

//            StringEntity entityStr = new StringEntity(jsonString, "UTF-8");
//            entityStr.setContentType("application/json"); <==> httpPost.setHeader("Content-Type", "application/json");
//            entityStr.setContentEncoding("UTF-8");
//            httpPost.setEntity(entityStr);

            response = client.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, "UTF-8");
            }
//            if (HttpServletResponse.SC_OK == response.getStatusLine().getStatusCode()) {
//
//            }
            //请注意如果返回的内容没有被完全消费掉，这个连接再次使用时候就会不安全并且会被自动关闭。
            //确保被消费掉
            EntityUtils.consume(entity);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ConnectTimeoutException) {
                throw new RuntimeException("连接超时, url:" + url);
            }
            if (e instanceof SocketTimeoutException) {
                throw new RuntimeException("获取数据超时, url:" + url);
            }

        } finally {
            try {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 可以自定义请求失败重试次数和exception处理 eg.
     * @return HttpRequestRetryHandler
     */
    private static HttpRequestRetryHandler getCustomizeRetryHandler() {
        //    自定义请求失败重试机制
        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= 5) {
                    // Do not retry if over max retry count
                    return false;
                }
                /*
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                 */
                return false;
            }
        };
    }
}



