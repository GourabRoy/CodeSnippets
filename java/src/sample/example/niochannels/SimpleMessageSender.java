package sample.example.niochannels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import sample.example.propertycontrol.PropertyLoader;

public class SimpleMessageSender {

	static String host = "localhost"; // System.getProperty("sample.example.niochannels.host");
	static int receiverPort = 47331; // Integer.getInteger("sample.example.niochannels.port");

	public static void main(String[] args) throws IOException, InterruptedException {
		new PropertyLoader().init();
		trigger();
	}

	static public void trigger() throws IOException, InterruptedException {
		SocketAddress remoteSocket = new InetSocketAddress(host, receiverPort);
		while (true) {
			int size = Integer.getInteger("sample.example.niochannels.object_size");
			AsynchronousSocketChannel channel = ChannelHelper.getSocketChannel();
			channel.connect(remoteSocket, new Result(size), getConnectionReceiverCompletionHandler(channel));
			Thread.sleep(10 * 1000);
		}

	}

	private static CompletionHandler<Void, Object> getConnectionReceiverCompletionHandler(AsynchronousSocketChannel channel) {
		return new CompletionHandler<Void, Object>() {
			@Override
			public void completed(Void result, Object message) {
				try {
					byte[] byteArray = SerializerDeserializer.getByteArray(message);
					ByteBuffer buffer = ByteBuffer.wrap(byteArray);
					channel.write(buffer, message, getWriteMessageCompletionHandler(channel, buffer));
				} catch (IOException | ClassNotFoundException e) {
					System.out.println("Failed to serialize object.");
				}
			}

			@Override
			public void failed(Throwable exc, Object attachment) {
				System.out.println("Could not connect to " + host + "/" + receiverPort);
			}

		};
	}
	
	private static CompletionHandler<Integer, Object> getWriteMessageCompletionHandler(AsynchronousSocketChannel channel, ByteBuffer buffer) {
		return new CompletionHandler<Integer, Object>() {
			@Override
			public void completed(Integer result, Object message) {
				if (buffer.hasRemaining()) {
					channel.write(buffer, message, this);
				} else {
					try {
						channel.shutdownOutput();
						readResponse(channel);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void failed(Throwable exc, Object message) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		};
	}
	
	
	private static void readResponse(AsynchronousSocketChannel channel)  {
		int bufferSize = Integer.getInteger("sample.example.niochannels.buffer_size");
		ByteBuffer responseBuffer = ByteBuffer.wrap(new byte[bufferSize]);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		channel.read(responseBuffer, baos,
				new CompletionHandler<Integer, ByteArrayOutputStream>() {

					@Override
					public void completed(Integer result, ByteArrayOutputStream baos) {
						if (result < 0) { // End Of Stream reached
							try {
								System.out.println("Response message : "
										+ channel.getRemoteAddress() + " : "
										+ SerializerDeserializer.getObject(baos.toByteArray()));
							} catch (ClassNotFoundException | IOException e) {
								System.out.println("Failed to read response message." + e);
							}
						} else {
							baos.write(responseBuffer.array(), 0, result);
							responseBuffer.clear();
							channel.read(responseBuffer, baos, this);
						}
					}

					@Override
					public void failed(Throwable exc, ByteArrayOutputStream attachment) {
						System.out.println("Failed to read response message." + exc);
					}

				});
	}
}
