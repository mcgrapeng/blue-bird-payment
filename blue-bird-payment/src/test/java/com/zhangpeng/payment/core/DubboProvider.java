/*
 * ====================================================================
 * 龙果学院： www.roncoo.com （微信公众号：RonCoo_com）
 * 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */
package com.zhangpeng.payment.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class DubboProvider {
	private static final Log log = LogFactory.getLog(DubboProvider.class);
	
    public static void main(String[] args) {
    	
        try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			context.start();
		} catch (Exception e) {
			log.error("== DubboProvider context start error:",e);
		}
        
        synchronized (DubboProvider.class) {
            while (true) {
                try {
                    System.out.println("启动成功");
                    DubboProvider.class.wait();
                } catch (InterruptedException e) {
                	log.error("== synchronized error:",e);
                }
            }
        }
    }
    
}