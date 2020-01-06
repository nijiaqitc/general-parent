package com.njq.junit;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RocketProducer {


    public static void main(String[] args) throws InterruptedException {
        // 需要一个producer group名字作为构造方法的参数，这里为producer1
        DefaultMQProducer producer = new DefaultMQProducer("SELF_TEST_P_GROUP");
        // 设置NameServer地址,此处应改为实际NameServer地址，多个地址之间用；分隔
        // NameServer的地址必须有，但是也可以通过环境变量的方式设置，不一定非得写死在代码里
        producer.setNamesrvAddr("118.25.12.143:9876");
        producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        // 解决rocketmq生产者发送消息超时
        // 毫秒单位，重要的点！！！！！！！！！！！
        producer.setSendMsgTimeout(15000);
        // 为避免程序启动的时候报错，添加此代码，可以让rocketMq自动创建topickey
        try {
            producer.start();
        } catch (MQClientException e1) {
            e1.printStackTrace();
            System.out.println("start失败：" + e1.toString());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:MM:sss");
        try {
            Message message = new Message("threezto-test", "tag", "key",
                    ("美丽中国-2").getBytes(RemotingHelper.DEFAULT_CHARSET));
            System.out.println("开始发送：" + sdf.format(new Date()));
            SendResult sendResult = producer.send(message);
            System.out.println("发送完成：" + sdf.format(new Date()));
            System.out.println("发送的消息ID:" + sendResult.getMsgId() + "--- 发送消息的状态：" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
