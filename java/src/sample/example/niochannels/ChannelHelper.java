package sample.example.niochannels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.spi.AsynchronousChannelProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChannelHelper {

	private static final int nThreads = 2;
	private static final int initialSize = 1;
	private static final ExecutorService executor = Executors.newFixedThreadPool(nThreads);
	private static final AsynchronousChannelProvider channelProvider = AsynchronousChannelProvider.provider();
	private static AsynchronousChannelGroup channelGroup;
	static {
		try {
			channelGroup = channelProvider.openAsynchronousChannelGroup(executor, initialSize);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static AsynchronousServerSocketChannel getServerSocketChannel(String host, int listeningPort) throws IOException {
		AsynchronousServerSocketChannel channel = channelProvider.openAsynchronousServerSocketChannel(channelGroup);
		channel.bind(new InetSocketAddress(host, listeningPort));
		return channel;
	}

	public static AsynchronousSocketChannel getSocketChannel() throws IOException {
		return channelProvider.openAsynchronousSocketChannel(channelGroup);
	}
}
