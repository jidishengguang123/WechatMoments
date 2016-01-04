package com.thoughtworks.wechat.http.request;

import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.thoughtworks.wechat.WeChatApp;

import java.io.UnsupportedEncodingException;

/***
 * JsonRequest基类，提供访问成功和访问出错的回调逻辑，并异步解析JSON成Java实体对象
 *
 * @ClassName: BaseJsonRequest
 * @author: 李阳
 * @date 2015-12-19 上午10:15:23
 */
public abstract class BaseJsonRequest<T> extends Request<T> {

    /**
     * Charset for request.
     */
    protected static final String PROTOCOL_CHARSET = "utf-8";

    /**
     * Content type for request.
     */
    protected static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    /**
     * 服务器成功响应后（状态码200）回调此接口
     */
    protected final Response.Listener<T> mListener;

    /**
     * 用于反射生产Java实体对象的类
     */
    protected Class<T> mJavaClass;

    /***
     * 构造BaseJsonRequest子类对象的构造函数
     *
     * @param @param method Get 或者 POST方式访问。
     * @param @param url 访问的接口地址。
     * @param @param cls 映射Java实体类
     * @param @param errorListener 访问错误回调的接口对象
     * @param @param listener 访问成功后回调
     */
    public BaseJsonRequest(int method, String url, Class<T> cls, Response.ErrorListener errorListener, Response.Listener<T> listener) {
        super(method, url, errorListener);

        // 设置访问超时时间和超时后的重试
        setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mListener = listener;
        mJavaClass = cls;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // 获取服务器返回的字符串
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            // 解析JSON串映射成Java实体对象
            T parsedGSON = parse(jsonString, mJavaClass);
            return Response.success(parsedGSON, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (Exception je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        Toast.makeText(WeChatApp.sContext,error.getMessage(),Toast.LENGTH_LONG).show();
        //当网络未连接时，读取缓存数据
        if (error instanceof NoConnectionError) {
            Cache.Entry entry = this.getCacheEntry();
            if (entry != null) {
                Response<T> response = parseNetworkResponse(new NetworkResponse(entry.data, entry.responseHeaders));
                deliverResponse(response.result);
                return;
            }
        }
        super.deliverError(error);
    }

    /***
     * 解析JSON字符串成Java实体对象
     *
     * @param json JSON字符串
     * @param t    需要映射的类
     * @return
     * @Title: BaseJsonRequest
     * @author:李阳
     */
    public abstract T parse(String json, Class<T> t);
}
