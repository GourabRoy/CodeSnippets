package sample.example.niochannels.remoteproxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import sample.example.niochannels.ChannelHelper;
import sample.example.niochannels.SerializerDeserializer;
import sample.example.propertycontrol.PropertyLoader;

public class Server {
	private String HOST = System.getProperty("remoteproxy.HOST");
	private int PORT = Integer.getInteger("remoteproxy.PORT");
	private int BUFFER_SIZE = Integer.getInteger("remoteproxy.BUFFER_SIZE");

	public static void main(String[] args) throws IOException {
		new PropertyLoader().init();
		Server server = new Server();
		server.run();
	}
	
	public void run() throws IOException {
		AsynchronousServerSocketChannel channel = ChannelHelper.getServerSocketChannel(HOST, PORT);
		channel.accept(null, getChannelAcceptHandler(channel));
	}
	
	private static ByteBuffer getByteBuffer(int bufferSize) {
		return ByteBuffer.allocate(bufferSize);
	}
	
	private CompletionHandler<AsynchronousSocketChannel, Object> getChannelAcceptHandler(
																			AsynchronousServerSocketChannel channel) {
			return new CompletionHandler<AsynchronousSocketChannel, Object>() {
				public void completed(AsynchronousSocketChannel incoming, Object attachment) {
					channel.accept(attachment, this);
					ByteBuffer buffer = getByteBuffer(BUFFER_SIZE);
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					incoming.read(buffer, outputStream, getReadRequestHandler(incoming, buffer));
				}
	
				public void failed(Throwable exc, Object attachment) {exc.printStackTrace();}
			};
	}
	
	private CompletionHandler<Integer, ByteArrayOutputStream> getReadRequestHandler(AsynchronousSocketChannel incoming, ByteBuffer buffer) {
		return new CompletionHandler<Integer, ByteArrayOutputStream>() {
			public void completed(Integer bytesRead, ByteArrayOutputStream outputStream) {
				try {
					if(bytesRead == -1) { // End of stream. Nothing more to read
						Request request = (Request)SerializerDeserializer.getObject(outputStream.toByteArray());
						sendResponse(process(request), incoming);
					} else {// More bytes to read from channel
						outputStream.write(buffer.array());
						buffer.clear();
						incoming.read(buffer, outputStream, this);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}

			public void failed(Throwable exc, ByteArrayOutputStream attachment) {}
		};
	}
	
	private Response process(Request request) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Class<?> implClass = request.getImplClass();
		Method method = implClass.getMethod(request.getMethod(), request.getParameterTypes());
		Object result = method.invoke(implClass.newInstance(), request.getParameterValues());
		return new Response.Builder().result(result).build();
	}
	
	private void sendResponse(Response response, AsynchronousSocketChannel incoming) throws ClassNotFoundException, IOException {
		ByteBuffer buffer = ByteBuffer.wrap(SerializerDeserializer.getByteArray(response));
		incoming.write(buffer, null, new CompletionHandler<Integer, Object>() {
			public void completed(Integer result, Object message) {
				if (buffer.hasRemaining()) {
					incoming.write(buffer, message, this);
				} else {
					try {
						incoming.close();
					} catch (IOException e1) {}
				}
			}

			public void failed(Throwable exc, Object message) {exc.printStackTrace();}
		});
	}
}
