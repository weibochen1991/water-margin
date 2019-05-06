package http.chap01;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * 使用httpClient发送请求和响应的一般步骤
 * 1.创建CloseableHttpClient
 * 2.创建请求方法实例，并制定url，如HttpGet,HttpPost
 * 3.如果需要请求参数，则使用setEntity设置参数
 * 4.设置header 设置消息头信息
 * 5.调用CloseableHttpClient的execute方法发送请求，并获得一个CloseableHttpResponse
 * 6.可以调用CloseableHttpResponse#getEntity()方法获取entity对象
 * 7.关闭连接
 * @Author: weibo
 * @Date: 2019/5/6 20:34
 * @Version 1.0
 */
public class HttpClientDemo01 {

    public static void main(String[] args) throws Exception {
        /**
         * Entity对象可以理解为除header之外的其他数据；
         * 主要有几种类型：1.Stream .来自于Stream ,不能repeatable
         * 2.self-content:内容来自于内存 或者其他entity,可以repeatable
         * 3.wrapping:
         **/
        test1();




    }

    /**
     *一个简单的get
     */
    private static void test1() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        System.out.println("状态： "+entity.getContentType());

        InputStream inputStream = entity.getContent();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        while((br.readLine())!=null){
            System.out.println(br.readLine());
        }

        br.close();
        httpResponse.close();
        httpClient.close();
    }




}
