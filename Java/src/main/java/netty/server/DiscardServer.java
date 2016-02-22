package netty.server;


import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import market.jobs.QuartzUtils;
import market.jobs.SimpleJobFactory;
import market.businessLogic.commands.Command;
import market.businessLogic.commands.LoadLastPurchasesCommand;
import market.client.HttpMarketClient;
import market.client.contracts.MarketClient;
import market.dal.hibernate.HibernatePurchaseHistoryRepository;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.quartz.Scheduler;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Discards any incoming data.
 */
public class DiscardServer {

    //поля с настройками для netty
    private int port;

    //поля, необходимые для логики приложения
    private static MarketClient marketClient = null;
    private static SessionFactory sessionFactory = null;
    private static Scheduler loadPurchaseHistoryScheduller = null;

    public DiscardServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)

            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {


        marketClient = new HttpMarketClient();

        sessionFactory = configureHibernateSessionFactory();

        List<Command> jobsLogic = new ArrayList<Command>();
        jobsLogic.add(new LoadLastPurchasesCommand(marketClient, new HibernatePurchaseHistoryRepository(sessionFactory)));
        Properties quartConfig = new Properties();
        quartConfig.load(DiscardServer.class.getClassLoader().getResourceAsStream("quartz.properties"));
        loadPurchaseHistoryScheduller = QuartzUtils.configureScheduler(new SimpleJobFactory(jobsLogic), quartConfig);
        loadPurchaseHistoryScheduller.start();

        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new DiscardServer(port).run();

    }

    //private

     /**
     * создание и настройка hibernate SessionFactory
     *
     * @return сконфигурированная для работы hibernate SessionFactory
     * @throws HibernateException
     */
    private static SessionFactory configureHibernateSessionFactory() throws HibernateException {
        // A SessionFactory is set up once for an application
        sessionFactory = new Configuration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        return sessionFactory;
    }


}