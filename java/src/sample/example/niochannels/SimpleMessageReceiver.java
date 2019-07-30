package sample.example.niochannels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

import sample.example.propertycontrol.PropertyLoader;

public class SimpleMessageReceiver {
	
	public static void main(String[] args) throws IOException {
		new PropertyLoader().init();
		String host = "localhost"; //System.getProperty("sample.example.niochannels.host");
		int receiverPort = 47331; //Integer.getInteger("sample.example.niochannels.port");
		AsynchronousServerSocketChannel channel = ChannelHelper.getServerSocketChannel(host, receiverPort);
		channel.accept(null, getConnectionRequestCompletionHandler(channel));
	}
	
	private static CompletionHandler<AsynchronousSocketChannel, Void> getConnectionRequestCompletionHandler(AsynchronousServerSocketChannel channel) {
		return new CompletionHandler<AsynchronousSocketChannel, Void>() {

			private void handle(AsynchronousSocketChannel incoming) {
				try {	
					int bufferSize = Integer.getInteger("sample.example.niochannels.buffer_size");
					ByteBuffer buffer = ByteBuffer.wrap(new byte[bufferSize]);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					incoming.read(buffer, baos, getReceiveMessageCompletionHandler(incoming, buffer, baos));
				} catch (IOException | InterruptedException | ExecutionException e) {
				}
			}
			
			@Override
			public void completed(AsynchronousSocketChannel result, Void attachment) {
				channel.accept(attachment, this);
				handle(result);
			}

			@Override
			public void failed(Throwable exc, Void attachment) {
				System.out.println("Failed to accept incoming channel connection.");
			}
		};
	}
	private static CompletionHandler<Integer, ByteArrayOutputStream> getReceiveMessageCompletionHandler(AsynchronousSocketChannel incoming,
				ByteBuffer buffer, ByteArrayOutputStream baos) throws IOException, InterruptedException, ExecutionException {
		
		return new CompletionHandler<Integer, ByteArrayOutputStream>() {

			@Override
			public void completed(Integer result, ByteArrayOutputStream baos) {
				if(result < 0) {
					try {
						System.out.println("Incoming message : " + incoming.getRemoteAddress() + " : " + SerializerDeserializer.getObject(baos.toByteArray()).toString());
						byte[] response = SerializerDeserializer.getByteArray("Thanks for the message");

						incoming.write(ByteBuffer.wrap(response), null, getSendResponseCompletionHandler(incoming));
					} catch (ClassNotFoundException | IOException e) {
						System.out.println("Failed to de-serialize incoming byte array.");
					}
				} else {
					System.out.println("Writing byte array into stream: byteArraySize=" + result);
					baos.write(buffer.array(), 0, result);
					buffer.clear();
					incoming.read(buffer, baos, this);
				}
			}

			@Override
			public void failed(Throwable exc, ByteArrayOutputStream attachment) {
				System.out.println("Failed to read incomeing message.");
			}
			
		};
	}

	
	private static CompletionHandler<Integer, Void> getSendResponseCompletionHandler(AsynchronousSocketChannel incoming) {
		return new CompletionHandler<Integer, Void>(){

			@Override
			public void completed(Integer result, Void attachment) {
				try {
					incoming.close();
				} catch (IOException e) {
					System.out.println("Falied to close connection after sending response.");
				}
			}

			@Override
			public void failed(Throwable exc, Void attachment) {
				System.out.println("Falied to Send response.");
			}
			
		};
	}
}
