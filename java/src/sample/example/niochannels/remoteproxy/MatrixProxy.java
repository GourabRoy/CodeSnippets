package sample.example.niochannels.remoteproxy;

public class MatrixProxy implements MatrixOperations {
	private Proxy proxyService = new Proxy();
	
	@Override
	public Matrix add(Matrix m1, Matrix m2) {
		Request request;
		try {
			request = new Request.Builder()
								.method(MatrixOperationsImpl.class.getMethod("add", Matrix.class, Matrix.class))
								.parameterValues(new Object[]{m1, m2})
								.build();
			Response response = proxyService.remoteCall(request);
			return (Matrix)response.getResult();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
