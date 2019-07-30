package sample.example.niochannels.remoteproxy;

import java.io.Serializable;
import java.lang.reflect.Method;

public class Request implements Serializable {
	private static final long serialVersionUID = 1L;
	private Class<?> implClass;
	private String method;
	private Class<?>[] parameterTypes;
	private Object[] parameterValues;
	
	private Request(){}
	
	public Class<?> getImplClass() {
		return implClass;
	}

	public String getMethod() {
		return method;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public Object[] getParameterValues() {
		return parameterValues;
	}

	public static class Builder {
		private Request request = new Request();
		private Method method;
		
		public Builder method(Method method) {
			this.method = method;
			return this;
		}
		
		public Builder parameterValues(Object[] parameterValues) {
			request.parameterValues = new Object[parameterValues.length];
			for(int i=0; i<parameterValues.length; i++) {
				request.parameterValues[i] = parameterValues[i];
			}
			return this;
		}
		
		public Request build() {
			request.implClass = method.getDeclaringClass();
			request.method = method.getName();
			request.parameterTypes = method.getParameterTypes();
			return request;
		}
	}
}
