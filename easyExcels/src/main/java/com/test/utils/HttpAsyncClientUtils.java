package com.test.utils;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

public class HttpAsyncClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpAsyncClientUtils.class);

    /**
     * 向指定的url发送一次异步post请求,参数是字符串
     * @param baseUrl 请求地址
     * @param postString 请求参数,格式是json.toString()
     * @param urlParams 请求参数,格式是String
     * @param callback 回调方法,格式是FutureCallback
     * @return 返回结果,请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static void httpAsyncPost(String baseUrl, String postString,
                                     String urlParams, FutureCallback callback) throws Exception {
        if (baseUrl == null || "".equals(baseUrl)) {
            logger.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }
        CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                .getAsyncHttpClient();
        try {
            hc.start();
            HttpPost httpPost = new HttpPost(baseUrl);

            httpPost.setHeader("Connection","close");

            if (null != postString && !"".equals(postString)) {
                logger.debug("exeAsyncReq post postBody={}", postString);
                StringEntity entity = new StringEntity(postString.toString(), Consts.UTF_8);
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
            }

            if (null != urlParams && !"".equals(urlParams)) {

                httpPost.setURI(new URI(httpPost.getURI().toString()
                        + "?" + urlParams));
            }

            logger.warn("exeAsyncReq getparams:" + httpPost.getURI());

            hc.execute(httpPost, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定的url发送一次异步post请求,参数是字符串
     * @param baseUrl 请求地址
     * @param urlParams 请求参数,格式是List<BasicNameValuePair>
     * @param callback 回调方法,格式是FutureCallback
     * @return 返回结果,请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static void httpAsyncPost(String baseUrl, List<BasicNameValuePair> postBody,
                                     List<BasicNameValuePair> urlParams, FutureCallback callback ) throws Exception {
        if (baseUrl == null || "".equals(baseUrl)) {
            logger.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }

        try {
            CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                    .getAsyncHttpClient();

            hc.start();

            HttpPost httpPost = new HttpPost(baseUrl);

            httpPost.setHeader("Connection","close");

            if (null != postBody) {
                logger.debug("exeAsyncReq post postBody={}", postBody);
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                        postBody, "UTF-8");
                httpPost.setEntity(entity);
            }

            if (null != urlParams) {

                String getUrl = EntityUtils
                        .toString(new UrlEncodedFormEntity(urlParams));

                httpPost.setURI(new URI(httpPost.getURI().toString()
                        + "?" + getUrl));
            }

            logger.warn("exeAsyncReq getparams:" + httpPost.getURI());

            hc.execute(httpPost, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定的url发送一次异步get请求,参数是String
     * @param baseUrl 请求地址
     * @param urlParams 请求参数,格式是String
     * @param callback 回调方法,格式是FutureCallback
     * @return 返回结果,请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static void httpAsyncGet(String baseUrl,String urlParams,FutureCallback callback) throws Exception {

        if (baseUrl == null || "".equals(baseUrl)) {
            logger.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }
        CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                .getAsyncHttpClient();
        try {


            hc.start();

            HttpGet httpGet = new HttpGet(baseUrl);

            httpGet.setHeader("Connection","close");

            if (null != urlParams && !"".equals(urlParams)) {

                httpGet.setURI(new URI(httpGet.getURI().toString()
                        + "?" + urlParams));
            }
            else{
                httpGet.setURI(new URI(httpGet.getURI().toString()));
            }

            logger.warn("exeAsyncReq getparams:" + httpGet.getURI());


            hc.execute(httpGet,  callback);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 向指定的url发送一次异步get请求,参数是List<BasicNameValuePair>
     * @param baseUrl 请求地址
     * @param urlParams 请求参数,格式是List<BasicNameValuePair>
     * @param callback 回调方法,格式是FutureCallback
     * @return 返回结果,请求失败时返回null
     * @apiNote http接口处用 @RequestParam接收参数
     */
    public static void httpAsyncGet(String baseUrl, List<BasicNameValuePair> urlParams, FutureCallback callback) throws Exception {
        if (baseUrl == null || "".equals(baseUrl)) {
            logger.warn("we don't have base url, check config");
            throw new Exception("missing base url");
        }

        try {
            CloseableHttpAsyncClient hc = HttpClientFactory.getInstance().getHttpAsyncClientPool()
                    .getAsyncHttpClient();

            hc.start();

            HttpGet httpGet = new HttpGet(baseUrl);

            httpGet.setHeader("Connection","close");

            if (null != urlParams) {

                String getUrl = EntityUtils
                        .toString(new UrlEncodedFormEntity(urlParams));

                httpGet.setURI(new URI(httpGet.getURI().toString()
                        + "?" + getUrl));
            }

            logger.warn("exeAsyncReq getparams:" + httpGet.getURI());


            hc.execute(httpGet, callback);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
