package com.thoughtworks.wechat.http.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.Map;

public abstract class PostJsonRequest<T> extends BaseJsonRequest<T> {

	/**
	 * 接口访问所需要的参数，以Map的形式组织
	 */
	protected Map<String, String> mParams;

	/***
	 * 构造以POST方式访问的接口的JsonRequest
	 * 
	 * @param @param url 接口的名称和地址
	 * @param @param params 接口需要的参数
	 * @param @param cls 需要映射的Java实体类对象
	 * @param @param errorListener 访问错误回调的接口
	 * @param @param listener 访问正确回调的接口
	 */
	public PostJsonRequest(String url, Map<String, String> params, Class<T> cls, Response.ErrorListener errorListener, Response.Listener<T> listener) {
		super(Method.GET, url, cls, errorListener, listener);
		mParams = params;
	}

	/***
	 * 返回服务端接口需要的参数
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return mParams;
	}

}
