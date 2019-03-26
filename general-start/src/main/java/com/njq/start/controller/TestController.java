package com.njq.start.controller;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestController {
	public static   CyclicBarrier cyclicBarrier=new CyclicBarrier(5);
	public static void main(String[] args) throws Exception {

//		TestController tt = new TestController();
//		Semaphore semaphore = new Semaphore(3,true);
//		for(int i = 0 ; i <101 ;i++){
//			new Thread(tt.new smr(semaphore,i)).start();
//		}

		
//		String filePlace = "C:/mywork/doc/debug/uploadDoc/yhwiki/20190325/微信商城v212-交互-0510.zip";		
//		String[] str = filePlace.split("\\/");
//		System.out.println(filePlace.substring(0, (filePlace.length()-str[str.length-1].length()-1)));
		
		String url = "https://img-blog.csdn.net/20180428102835632";
		String uriStr = url.split("\\/")[2];
        if (url.startsWith("https")) {
            uriStr = "https://" + uriStr;
        } else {
            uriStr = "http://" + uriStr;
        }
		
		System.out.println(uriStr);
		
		
//		File dir = new File("C:\\mywork\\doc\\debug\\uploadDoc\\yhwiki\\20190325\\微信商城v212-交互-0510.zip");
//		dir.mkdirs();

//		System.out.println(DateTime.now().getDayOfWeek());
//		System.out.println(Period.weeks(1).toString());

//		String[] s=new String[1];
//		s[0]="ftl/*";
//		TestController.simpleMatch(s, "ftl/abc");
//		TestController tt = new TestController();
//		createRunnable run =  tt.new createRunnable();
//
//		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
//		threadPoolTaskExecutor.setCorePoolSize(15);
//		threadPoolTaskExecutor.setKeepAliveSeconds(200);
//		threadPoolTaskExecutor.setMaxPoolSize(180);
//		threadPoolTaskExecutor.setQueueCapacity(100);
//		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
//		threadPoolTaskExecutor.initialize();
//		System.out.println(threadPoolTaskExecutor.getActiveCount());
//		for(int i = 0 ;i<5 ; i++){
//			threadPoolTaskExecutor.submit(run.getRunnable());
//			System.out.println(threadPoolTaskExecutor.getActiveCount());
//
//		}
//		threadPoolTaskExecutor.submit(run.getLeaderRunnable());
//		System.out.println(threadPoolTaskExecutor.getActiveCount());

//		threadPoolTaskExecutor.shutdown();

//		Date timeCur = new Date();
//		SimpleDateFormat fmtYY = new SimpleDateFormat("yyyy");
//		SimpleDateFormat fmtMM = new SimpleDateFormat("MM");
//		SimpleDateFormat fmtDD = new SimpleDateFormat("dd");
//		String strYY = fmtYY.format(timeCur);
//		String strMM = fmtMM.format(timeCur);
//		String strDD = fmtDD.format(timeCur);
//		String url = "/" + "bbbb" + "/" + strYY + strMM + strDD;
//		File dir = new File("D:/worksts/ppcong/target/classes/webapp/static/uploadImage" + url);
//		if (!dir.exists()) {
//			dir.mkdirs();
//		}
//		List<Integer> list = new ArrayList<>();
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		System.out.println(list.subList(0,4 ));
		
//		String aaa = "aaa^bbbb";
//		System.out.println(aaa.split("\\^").length);
//		String str = "<script>var currentBlogId=452926;var currentBlogApp='ibethfy',cb_enable_mathjax=false;var isLogined=false;</script>";
//		Pattern pattern =Pattern.compile("currentBlogId=.*?;");
//		Matcher mt =  pattern.matcher(str);
//		System.out.println(mt.find());
//		System.out.println(mt.group());
	}

	public class  smr implements Runnable{
		private Semaphore semaphore;
		private int i;
		public smr(Semaphore semaphore,int i) {
			this.semaphore = semaphore;
			this.i = i;
		}

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println(i+"开始了");
				TimeUnit.SECONDS.sleep(RandomUtils.nextInt(1,3));
				System.out.println(i+"-----------------"+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			semaphore.release();
		}
	}



	public class  createRunnable{
		public Runnable getRunnable(){
			Runnable r1 = () -> {
				try {
					System.out.println(Thread.currentThread().getId() + "翻越障碍物1");

					TimeUnit.SECONDS.sleep(5);
					System.out.println(Thread.currentThread().getId()+"完成任务!");
				} catch (InterruptedException e) {

				}
				System.out.println(Thread.currentThread().getId()+"执行结束");
			};
			return r1;
		}

		public Runnable getLeaderRunnable(){
			Runnable r1 = () -> {
				try {
					System.out.println(Thread.currentThread().getId() + "翻越障碍物1");
					cyclicBarrier.await();
					cyclicBarrier.reset();
					System.out.println(Thread.currentThread().getId() + "翻越障碍物2");
					cyclicBarrier.await();
					cyclicBarrier.reset();
					System.out.println(Thread.currentThread().getId() + "翻越障碍物3");
					cyclicBarrier.await();
					System.out.println(Thread.currentThread().getId()+"完成任务!");
				} catch (InterruptedException e) {

				} catch (BrokenBarrierException e) {

				}
				System.out.println(Thread.currentThread().getId()+"执行结束");
			};
			return r1;
		}
	}

	public static boolean simpleMatch(@Nullable String[] patterns, String str) {
		if (patterns != null) {
			for (String pattern : patterns) {
				if (simpleMatch(pattern, str)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean simpleMatch(@Nullable String pattern, @Nullable String str) {
		if (pattern == null || str == null) {
			return false;
		}
		int firstIndex = pattern.indexOf('*');
		if (firstIndex == -1) {
			return pattern.equals(str);
		}
		if (firstIndex == 0) {
			if (pattern.length() == 1) {
				return true;
			}
			int nextIndex = pattern.indexOf('*', firstIndex + 1);
			if (nextIndex == -1) {
				return str.endsWith(pattern.substring(1));
			}
			String part = pattern.substring(1, nextIndex);
			if ("".equals(part)) {
				return simpleMatch(pattern.substring(nextIndex), str);
			}
			int partIndex = str.indexOf(part);
			while (partIndex != -1) {
				if (simpleMatch(pattern.substring(nextIndex), str.substring(partIndex + part.length()))) {
					return true;
				}
				partIndex = str.indexOf(part, partIndex + 1);
			}
			return false;
		}
		return (str.length() >= firstIndex &&
				pattern.substring(0, firstIndex).equals(str.substring(0, firstIndex)) &&
				simpleMatch(pattern.substring(firstIndex), str.substring(firstIndex)));
	}
}
