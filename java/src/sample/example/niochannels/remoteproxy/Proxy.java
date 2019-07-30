package sample.example.niochannels.remoteproxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import sample.example.niochannels.ChannelHelper;
import sample.example.niochannels.SerializerDeserializer;

public class Proxy {
	private static String HOST = System.getProperty("remoteproxy.HOST");
	private static int PORT = Integer.getInteger("remoteproxy.PORT");
	private static int BUFFER_SIZE = Integer.getInteger("remoteproxy.BUFFER_SIZE");

	public Proxy() {}
	
	public Response remoteCall(Request request) {
		// One channel to post the request and receive response
		try {
			AsynchronousSocketChannel channel = ChannelHelper.getSocketChannel();
			ResponseWrapper responseWrapper = new ResponseWrapper();
			channel.connect(new InetSocketAddress(HOST, PORT), request, getConnectionReceiverCompletionHandler(channel, responseWrapper));
			while(!responseWrapper.isResponseSet) {
				Thread.sleep(1000);
			}
			return responseWrapper.response;
		} catch (IOException | InterruptedException e) {e.printStackTrace();}
		return null;
	}

	private CompletionHandler<Void, Request> getConnectionReceiverCompletionHandler(AsynchronousSocketChannel channel, ResponseWrapper responseWrapper) {
		return new CompletionHandler<Void, Request>() {
			public void completed(Void result, Request request) {
				try {
					ByteBuffer buffer = ByteBuffer.wrap(SerializerDeserializer.getByteArray(request));
					channel.write(buffer, request, getSendRequestHandler(channel, buffer, responseWrapper));
				} catch (IOException | ClassNotFoundException e) {e.printStackTrace();}
			}

			public void failed(Throwable exc, Request attachment) {System.out.println("Could not connect server.");}
		};
	}

	private CompletionHandler<Integer, Object> getSendRequestHandler(AsynchronousSocketChannel outgoing, ByteBuffer buffer, ResponseWrapper responseWrapper) {
		return new CompletionHandler<Integer, Object>() {
			public void completed(Integer result, Object message) {
				if (buffer.hasRemaining()) {
					outgoing.write(buffer, message, this);
				} else {
					try {
						outgoing.shutdownOutput();
						readResponse(outgoing, responseWrapper);
					} catch (IOException e) {e.printStackTrace();}
				}
			}

			public void failed(Throwable exc, Object message) {exc.printStackTrace();}
		};
	}
	
	private void readResponse(AsynchronousSocketChannel outgoing, ResponseWrapper responseWrapper) {
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		outgoing.read(buffer, baos,
				new CompletionHandler<Integer, ByteArrayOutputStream>() {
					public void completed(Integer bytesRead, ByteArrayOutputStream baos) {
						if (bytesRead < 0) { // End Of Stream reached
							try {
								responseWrapper.response = (Response)SerializerDeserializer.getObject(baos.toByteArray());
								responseWrapper.isResponseSet = true;
							} catch (ClassNotFoundException | IOException e) {e.printStackTrace();}
						} else {
							baos.write(buffer.array(), 0, bytesRead);
							buffer.clear();
							outgoing.read(buffer, baos, this);
						}
					}

					public void failed(Throwable e, ByteArrayOutputStream attachment) {e.printStackTrace();}
				});
	}
	
	class ResponseWrapper {
		volatile Response response;
		volatile boolean isResponseSet = false;
	}
}
