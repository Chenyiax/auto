package com.example.back.service;

import com.example.back.message.Message;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service
public class BeginService {
    @Autowired
    AsyncTaskService asyncTaskService;

    private WebDriver driver;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private boolean isFirst = true;

    public boolean isBrowserClosed(WebDriver driver){
        boolean isClosed = false;
        try{
            driver.getTitle();
        }catch(Exception e) {
            e.printStackTrace();
            isClosed=true;
        }
        return isClosed;
    }

    public Message begin() {
        Date date = new Date();
        if (driver == null || isBrowserClosed(driver)) {
            try{
                System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver.exe");//谷歌浏览器驱动的地址
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get("http://passport2.chaoxing.com/login?newversion=true");
            } catch (Exception e) {
                e.printStackTrace();
                return new Message(2,formatter.format(date),"启动失败");
            }
            return new Message(1,formatter.format(date),"启动成功");
        } else {
            return new Message(2,formatter.format(date),"已存在浏览器，请勿重复启动");
        }
    }

    public Message run() {
        Message message = new Message();
        Date date = new Date();
        if (isFirst) {
            isFirst = false;
            Future<Message> future = asyncTaskService.start(driver);
            try {
                message = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(2,formatter.format(date),"出现了奇妙的错误");
            }

            return message;
        } else {
            Future<Message> future = asyncTaskService.check(driver);
            try {
                message = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                return new Message(2,formatter.format(date),"出现了奇妙的错误");
            }
            return message;
        }
    }

    public Message stop() {
        Date date = new Date();
        Message message = new Message();
        Future<Message> future = asyncTaskService.stop();
        try {
            message = future.get();
            isFirst = true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return new Message(2,formatter.format(date),"出现了奇妙的错误");
        }
        return message;
    }

    public Message over() {
        Date date = new Date();
        try {
            driver.close();
            driver.quit();
            driver = null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(2,formatter.format(date),"出现了奇妙的错误");
        }
        return new Message(1,formatter.format(date),"关闭成功");
    }

    public Message next() {
        Date date = new Date();
        try {
            Future<Message> future = asyncTaskService.next(driver);
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return new Message(2,formatter.format(date),"出现了奇妙的错误");
        }
    }
}
