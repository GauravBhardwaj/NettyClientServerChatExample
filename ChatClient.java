

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.serialization.ObjectEncoder;

public class ChatClient {

	
	
	public static void main(String args[]) throws IOException{
		
				
		//create a Boss thread from thread pool
		Executor BossPool = Executors.newCachedThreadPool();
		Executor WorkerPool = Executors.newCachedThreadPool();
		
		//create NIO client channel factories
		ChannelFactory channelFactory = new NioClientSocketChannelFactory(BossPool,WorkerPool);
		
		
		
		//Client Setup the channel using bootstrap
		ClientBootstrap clientBootstrap = new ClientBootstrap(channelFactory);
		
		
		//setup the Channel PipeLine Factory
		ChannelPipelineFactory pipelineFactory = new ChannelPipelineFactory() {
			  public ChannelPipeline getPipeline() throws Exception {
			    return Channels.pipeline(
			      new ObjectEncoder()
			    );
			  }
			};	
			
		//set the pipeline
			clientBootstrap.setPipelineFactory(pipelineFactory);
		
		
		//connect to host port
		InetSocketAddress addressToConnectTo = new InetSocketAddress("localhost", 8090);
		
		//connect asynchronously :)
		ChannelFuture cf = clientBootstrap.connect(addressToConnectTo);
		
		//get the channel to send message
		org.jboss.netty.channel.Channel channel = cf.getChannel(); //could be a error here
		
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		while(true)
		{
			//write data in it.
			channel.write(input.readLine().getBytes());
			
		}
		
		 
	}
}

