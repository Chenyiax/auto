package com.example.back.service;

import com.example.back.message.Message;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.concurrent.Future;

@Async
@Service
public class AsyncTaskService {
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private boolean isRun = false;
    private WebElement video;

    public Future<Message> start(WebDriver driver){
        isRun = true; //check函数通过检测isrun判断是否在运行
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
        for (String handles : driver.getWindowHandles()) {
            driver.switchTo().window(handles);
        }//将driver选择到当前页面
        try {
            WebElement frame1 = driver.findElement(By.xpath("//*[@id=\"iframe\"]"));
            driver.switchTo().frame(frame1);
            try {
                WebElement frame2 = driver.findElement(By.xpath("/html/body/div/div/div/iframe"));
                driver.switchTo().frame(frame2);//学习通的页面有点问题，有的页面会多一个<p>也不知道为啥，所以用try判断一下
            } catch (Exception e) {
                e.printStackTrace();
                WebElement frame2 = driver.findElement(By.xpath("/html/body/div/div/p/div/iframe"));
                driver.switchTo().frame(frame2);
            }
            video = driver.findElement(By.xpath("//*[@id=\"video_html5_api\"]"));
            javascriptExecutor.executeScript("return arguments[0].play();", video);//找到视频后通过JavaScript播放
        } catch (Exception e) {
            e.printStackTrace();
            Date date = new Date();
            isRun = false;
            return new AsyncResult<Message>(new Message(2,formatter.format(date),"未能找到目标视频"));
        }
        while(isRun) {
            try {
                Thread.sleep(13000);
                javascriptExecutor.executeScript("return arguments[0].play();", video);
                Object a = javascriptExecutor.executeScript("return arguments[0].duration;", video);
                Object b = javascriptExecutor.executeScript("return arguments[0].currentTime;", video);//这两行是获取视频时长与当前时长，判断是否要跳转到下一个视频
                double a1 = Double.parseDouble(a.toString());
                double b1 = Double.parseDouble(b.toString());
                System.out.println(a1);
                System.out.println(b1);
                if(b1 < 10) {
                    driver.switchTo().defaultContent();//返回主页面
                    try{
                        WebElement next = driver.findElement(By.xpath("//h4[@class=\"currents\"]/../following-sibling::div[1]/h4/a"));//不出意外是这这么找到下一个视频
                        next.click();
                    } catch (NoSuchElementException e) {
                        e.printStackTrace();
                        WebElement next = driver.findElement(By.xpath("//h4[@class=\"currents\"]/../../following-sibling::div[1]/div[1]/h4/a"));//出意外，比如是下一章的视频这么找
                        next.click();
                    }
                    Thread.sleep(2000);
                    try {
                        WebElement frame1 = driver.findElement(By.xpath("//*[@id=\"iframe\"]"));//跳转到新页面后需要重新寻找视频
                        driver.switchTo().frame(frame1);
                    } catch (NoSuchElementException e) {//如果没找到视频，可能是跳转到了没有视频的章节，找下一个视频
                        e.printStackTrace();
                        try{
                            WebElement next = driver.findElement(By.xpath("//h4[@class=\"currents\"]/../following-sibling::div[1]/h4/a"));
                            next.click();
                        } catch (NoSuchElementException e1) {
                            e1.printStackTrace();
                            WebElement next = driver.findElement(By.xpath("//h4[@class=\"currents\"]/../../following-sibling::div[1]/div[1]/h4/a"));
                            next.click();
                        }
                        WebElement frame1 = driver.findElement(By.xpath("//*[@id=\"iframe\"]"));
                        driver.switchTo().frame(frame1);
                    }
                    try {
                        WebElement frame2 = driver.findElement(By.xpath("/html/body/div/div/div/iframe"));
                        driver.switchTo().frame(frame2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        WebElement frame2 = driver.findElement(By.xpath("/html/body/div/div/p/div/iframe"));
                        driver.switchTo().frame(frame2);
                    }
                    video = driver.findElement(By.xpath("//*[@id=\"video_html5_api\"]"));
                    javascriptExecutor.executeScript("return arguments[0].play();", video);
                } else {
                    javascriptExecutor.executeScript("return arguments[0].play();", video);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Date date = new Date();
        return new AsyncResult<Message>(new Message(1,formatter.format(date),"完成运行"));
    }

    public Future<Message> check(WebDriver driver){
        Date date = new Date();
        if (isRun) {
            return new AsyncResult<Message>(new Message(1,formatter.format(date),"正在操作浏览器"));
        } else {
            return new AsyncResult<Message>(new Message(1,formatter.format(date),"出现了奇妙的问题"));
        }
    }

    public Future<Message> stop() {
        Date date = new Date();
        isRun = false;
        return new AsyncResult<Message>(new Message(1,formatter.format(date),"操作结束"));
    }

    public Future<Message> next(WebDriver driver) {
        Date date = new Date();
        try {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor)driver;
            javascriptExecutor.executeScript("return arguments[0].currentTime=duration;",video);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<Message>(new Message(1,formatter.format(date),"快进失败"));
        }
        return new AsyncResult<Message>(new Message(1,formatter.format(date),"快进成功"));
    }
}
