package sample.example.niochannels.remoteproxy;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	private Class<?> returnType;
	private Object result;
	
	private Response(){}
	
	public Class<?> getReturnType() {
		return returnType;
	}

	public Object getResult() {
		return result;
	}

	public static class Builder {
		private Response response = new Response();
		
		public Builder result(Object result) {
			response.result = result;
			return this;
		}
		
		public Response build() {
			response.returnType = response.result.getClass();
			return response;
		}
	}
}
