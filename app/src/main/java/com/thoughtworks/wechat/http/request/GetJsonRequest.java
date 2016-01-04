package com.thoughtworks.wechat.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public abstract class GetJsonRequest<T> extends BaseJsonRequest<T> {

	private final String mRequestBody;

	private Map<String, String> headers = new HashMap<String, String>();

	/***
	 * 构造以Get方式访问的接口的JsonRequest
	 * 
	 * @param @param url 接口名称、地址
	 * @param @param cls 映射类
	 * @param @param listener 服务器成功返回后回调接口
	 * @param @param errorListener 服务器访问出错回调接口
	 */
	public GetJsonRequest(String url, Class<T> cls, Response.Listener<T> listener,Response.ErrorListener errorListener) {
		super(Request.Method.GET, url, cls, errorListener, listener);
		mRequestBody = null;
	}

	/***
	 * 描述HTTP访问的Header部分的参数
	 */
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		return headers;
	}

	/***
	 * 要求返回的数据类型格式（JSON、String等）
	 */
	@Override
	public String getBodyContentType() {
		return PROTOCOL_CONTENT_TYPE;
	}

	@Override
	public byte[] getBody() {
		try {
			return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
		} catch (UnsupportedEncodingException uee) {
			VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, PROTOCOL_CHARSET);
			return null;
		}
	}

}
