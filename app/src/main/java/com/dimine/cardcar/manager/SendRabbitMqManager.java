package com.dimine.cardcar.manager;

import android.os.Handler;
import android.os.Message;

import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyLog;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/11 14:30
 * desc   : 发送请求，  一种使用队列，一种使用线程池
 * version: 1.0
 */
public class SendRabbitMqManager {
    public static final String TAG = SendRabbitMqManager.class.getSimpleName();

    public static final String MQ_EXCHANGE_SEND = "dispatcher";
    public static final String MQ_ROUTEKEY_SEND = "truck.";


    public static final String MQ_EXCHANGE = "status";
    public static final String MQ_ROUTEKEY = "truck.";

    public static final String MQ_EXCHANGE_ACK = "Auto";
    public static final String MQ_ROUTEKEY_ACK = "";

    /**
     * 1，默认值；2，失败，3，成功
     */
    public int isConnect = 1;

    private static SendRabbitMqManager mSendManager = null;
    private Thread subscribeThread;

    public static SendRabbitMqManager getInstance() {
        if (mSendManager == null) {
            synchronized (SendRabbitMqManager.class) {
                if (mSendManager == null) {
                    mSendManager = new SendRabbitMqManager();
                }
            }
        }
        return mSendManager;
    }

    public void changeInstance() {
        subscribe(incomingMessageHandler);

    }


    /**
     * 声明 ConnectionFactory
     */
    private ConnectionFactory factory = new ConnectionFactory();
    private Thread publishTh;
    private Thread publishThAck;
    public String deviceId;

    private SendRabbitMqManager() {
        modifyDeviceId();
        setUpConnectionFactory();
        MyLog.d("SendRabbitMqManager", "运行");
        subscribe(incomingMessageHandler);
        publishToAMPQ(MQ_ROUTEKEY_SEND);
        publishToAck(MQ_ROUTEKEY_ACK);
    }

    public void modifyServiceIp() {
        modifyDeviceId();
        setUpConnectionFactory();
        MyLog.d("SendRabbitMqManager#修改ServerIp", "运行#修改ServerIp");
        subscribe(incomingMessageHandler);
        publishToAMPQ(MQ_ROUTEKEY_SEND);
        publishToAck(MQ_ROUTEKEY_ACK);
    }

    public void modifyDeviceId() {
        deviceId = LocalArguments.getInstance().deviceNumber();
    }


    private void setUpConnectionFactory() {
        //主机地址
        factory.setHost(LocalArguments.getInstance().serviceIp());
        factory.setPort(5672);
        factory.setUsername(LocalArguments.getInstance().rabbitMqUserName());
        factory.setPassword(LocalArguments.getInstance().rabbitMqPassword());
        factory.setAutomaticRecoveryEnabled(true);
    }

    private BlockingDeque<String> queue = new LinkedBlockingDeque<>();

    public void publishMessage(String message) {
        //Adds a message to internal blocking queue
        try {
            MyLog.d("SendRabbitMqManager", queue.size()+"[add queue] " + message);
            queue.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
            WriteLogManager.getInstance().writeLog("【rabbitMq#publishMessage】" + e.toString());
        }
    }

    private void publishToAMPQ(final String routingKey) {
        publishTh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        MyLog.d("SendRabbitMqManager", "new Thread" + Thread.currentThread().getName());
                        while (true) {
                            String message = queue.takeFirst();
                            try {
                                //在此渠道上启用发布商确认。
//                                channel.confirmSelect();
                                //声明交换
                                channel.exchangeDeclare(MQ_EXCHANGE_SEND, "topic");
                                //（交换器名，路由键）
                                channel.basicPublish(MQ_EXCHANGE_SEND, routingKey + deviceId, null, message.getBytes());
                                MyLog.d("SendRabbitMqManager", Thread.currentThread().getName()+"while " + message);
//                                channel.waitForConfirmsOrDie();
                            } catch (Exception e) {
                                MyLog.d("SendRabbitMqManager", "Exception#publishTh [queue] " + message);
                                queue.putFirst(message);
                                WriteLogManager.getInstance().writeLog("【rabbitMq#publishTh queue】" + e.toString());
                                throw e;
                            }
                        }
                    } catch (Exception e) {
                        MyLog.d("SendRabbitMqManager", "Exception#publishTh" + e.toString());
                        WriteLogManager.getInstance().writeLog("【rabbitMq#publishTh exception】" + e.toString());
                        try {
                            //sleep and then try again
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            break;
                        }
                    }
                }
            }
        });
        publishTh.start();
    }


    private BlockingDeque<String> queueAck = new LinkedBlockingDeque<>();

    public void publishMessageAck(String message) {
        //Adds a message to internal blocking queue
        try {
            MyLog.d("SendRabbitMqManager", queueAck.size()+"[add queueAck] " + message);
            queueAck.putLast(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
            WriteLogManager.getInstance().writeLog("【rabbitMq#publishMessageAck】" + e.toString());
        }
    }

    private void publishToAck(final String routingKey) {
        publishThAck = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Connection connection = factory.newConnection();
                        Channel channel = connection.createChannel();
                        MyLog.d("SendRabbitMqManager", "new Thread ack" + Thread.currentThread().getName());
                        while (true) {
                            String message = queueAck.takeFirst();
                            try {
                                //在此渠道上启用发布商确认。
//                                channel.confirmSelect();
                                //声明交换
                                channel.exchangeDeclare(MQ_EXCHANGE_ACK, "topic");
                                //（交换器名，路由键）
                                channel.basicPublish(MQ_EXCHANGE_ACK, routingKey, null, message.getBytes());
                                MyLog.d("SendRabbitMqManager", "publish Ack " + message);
//                                channel.waitForConfirmsOrDie();
                            } catch (Exception e) {
                                MyLog.d("SendRabbitMqManager ack", "rabbitMq#publishToAck [queueAck] " + message);
                                queueAck.putFirst(message);
                                WriteLogManager.getInstance().writeLog("【rabbitMq#publishToAck queueAck】" + e.toString());
                                throw e;
                            }
                        }
                    } catch (Exception e) {
                        MyLog.d("SendRabbitMqManager ack", "Exception#publishThAck" + e.toString());
                        WriteLogManager.getInstance().writeLog("【rabbitMq#publishThAck】" + e.toString());
                        try {
                            //sleep and then try again
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            break;
                        }
                    }
                }
            }
        });
        publishThAck.start();
    }

    public interface ReceiveMessageListener {
        void onMessage(String msg);
    }

    private ReceiveMessageListener receiveMessageListener;

    public void setReceiveMessageListener(ReceiveMessageListener receiveMessageListener) {
        this.receiveMessageListener = receiveMessageListener;
    }

    /**
     * 处理handler发送的消息，然后进行操作（在主线程）
     */
    private Handler incomingMessageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 获取RabbitMQ的消息数据
            String messageData = (String) msg.obj;
            MyLog.d(TAG, "handleMessage" + messageData);
            if (receiveMessageListener != null) {
                receiveMessageListener.onMessage(messageData);
            }
        }

    };

    public void subscribe(final Handler handler) {
        subscribeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
                        // 创建新的连接
                        Connection connection = factory.newConnection();
                        // 创建通道
                        Channel channel = connection.createChannel();
                        // 声明交换机类型
                        channel.exchangeDeclare(MQ_EXCHANGE, "topic");
                        // 声明队列
                        String queueName = channel.queueDeclare().getQueue();
                        // 处理完一个消息，再接收下一个消息
                        channel.basicQos(1);
                        MyLog.d("ReceiveRabbitMqManager", "handleMessage" + "[Exchange] " + MQ_EXCHANGE);
                        isConnect = 3;
                        // 根据路由键将队列绑定到交换机上（需要知道交换机名称和路由键名称），可以绑定多个路由到一个队列
                        // （队列名，交换名，路由键）
                        channel.queueBind(queueName, MQ_EXCHANGE, MQ_ROUTEKEY + deviceId);
                        // 创建消费者获取rabbitMQ上的消息。每当获取到一条消息后，就会回调handleDelivery（）方法，该方法可以获取到消息数据并进行相应处理
                        Consumer consumer = new DefaultConsumer(channel) {
                            @Override
                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                                super.handleDelivery(consumerTag, envelope, properties, body);
                                MyLog.d("ReceiveRabbitMqManager", "handleMessage" + "[Exchange] " + envelope.getExchange() +
                                        "  [RoutingKey] " + envelope.getRoutingKey());
                                // 通过geiBody方法获取消息中的数据
                                String msg = new String(body);
                                // 发消息通知UI更新
                                Message message = handler.obtainMessage();
                                message.obj = msg;
                                handler.sendMessage(message);
                                channel.basicAck(envelope.getDeliveryTag(), false);
                            }
                        };
                        channel.basicConsume(queueName, false, consumer);

                    } catch (Exception e) {
                        e.printStackTrace();
                        WriteLogManager.getInstance().writeLog("【rabbitMq exception#subscribeThread】" + e.toString());
                        MyLog.d("ReceiveRabbitMqManager", "[Exception#subscribeThread]  " + e.getMessage());
                        isConnect = 2;
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

        });
        subscribeThread.start();// 开启线程获取RabbitMQ推送消息
        MyLog.d("ReceiveRabbitMqManager", "接收启动");
    }

}
