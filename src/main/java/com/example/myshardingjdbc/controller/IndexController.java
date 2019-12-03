package com.example.myshardingjdbc.controller;


import com.example.myshardingjdbc.entity.TImage;
import com.example.myshardingjdbc.entity.TImageExample;
import com.example.myshardingjdbc.mapper.TImageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author zp
 * @create 2019/10/16 11:38
 */
@RestController
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    private int iii=0;

    @Autowired
    TImageMapper tImageMapper;

    @RequestMapping("/list")
    public Object index() {
        PageHelper.startPage(1,10);
        long start = System.currentTimeMillis();
        Object o = new PageInfo<>(tImageMapper.selectAll());
        long costTime = System.currentTimeMillis() - start;
        System.out.println("用时:" + costTime + "ms");
        return o;
    }

    @RequestMapping("/title")
    public Object title(@RequestParam String title) {
        long start = System.currentTimeMillis();
        Object o = tImageMapper.selectByTitle(title);
        long costTime = System.currentTimeMillis() - start;
        System.out.println("用时:" + costTime + "ms");
        return o;
    }

    @RequestMapping("/delete")
    public Object delete() {
        long start = System.currentTimeMillis();
        tImageMapper.delete();
        long costTime = System.currentTimeMillis() - start;
        System.out.println("用时:" + costTime + "ms");
        return costTime;
    }

    @RequestMapping("/insert")
    public Object insert(@RequestParam Integer cnt) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < cnt; i++) {
            TImage tImage = new TImage();
            tImage.setTitle("第" + (i + 1) + "个标题");
            tImage.setSmallImage("test.jpg");
            tImage.setPath("test.jpg");
            tImage.setIsDelete("0");
            tImage.setIsEnable("1");
            tImage.setCreateTime(new Date());
            tImageMapper.insert(tImage);
        }
        long costTime = System.currentTimeMillis() - start;
        System.out.println("用时:" + costTime + "ms");
        return "OK";
    }

    @RequestMapping("/insertWithThread")
    public Object insertWithThread(@RequestParam Integer cnt) {
        log.info("==============insertWithThread start==============");
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < cnt/10; i++) {
                        iii = i + 1;
                        TImage tImage = new TImage();
                        tImage.setTitle("第" + iii + "个标题");
                        tImage.setSmallImage("test222.jpg");
                        tImage.setPath("test222.jpg");
                        tImage.setIsDelete("0");
                        tImage.setIsEnable("1");
                        tImage.setCreateTime(new Date());
                        tImageMapper.insert(tImage);
                    }
                }
            };
            thread.start();
        }
        long costTime = System.currentTimeMillis() - start;
        log.info("用时:" + costTime + "ms");
        return start;
    }

    @RequestMapping("/insertWithThreadPool")
    public Object insertWithThreadPool(@RequestParam Integer cnt) {
        log.info("==============insertWithThreadPool start==============");
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < cnt; i++) {
            iii = i + 1;
            executorService.execute(()->{
                TImage tImage = new TImage();
                tImage.setTitle("第" + iii + "个标题");
                tImage.setSmallImage("test222.jpg");
                tImage.setPath("test222.jpg");
                tImage.setIsDelete("0");
                tImage.setIsEnable("1");
                tImage.setCreateTime(new Date());
                tImageMapper.insert(tImage);
            });

        }
        long costTime = System.currentTimeMillis() - start;
        log.info("用时:" + costTime + "ms");
        return start;
    }

    @RequestMapping("/insertWithThreadPoolFixed")
    public Object insertWithThreadPoolFixed(@RequestParam Integer cnt) {
        log.info("==============insertWithThreadPoolFixed start==============");
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < cnt; i++) {
            iii = i + 1;
            executorService.execute(()->{
                TImage tImage = new TImage();
                tImage.setTitle("第" + iii + "个标题");
                tImage.setSmallImage("test222.jpg");
                tImage.setPath("test222.jpg");
                tImage.setIsDelete("0");
                tImage.setIsEnable("1");
                tImage.setCreateTime(new Date());
                tImageMapper.insert(tImage);
            });

        }
        long costTime = System.currentTimeMillis() - start;
        log.info("用时:" + costTime + "ms");
        return start;
    }

    @RequestMapping("/test")
    public Object test(){
        return tImageMapper.selectByPrimaryKey(1472251);
    }

    @RequestMapping("/test2")
    public Object test2(){
        TImageExample tImageExample = new TImageExample();
        TImageExample.Criteria criteria = tImageExample.createCriteria();
        criteria.andTitleLike("第8%");
//        tImageExample.or().andTitleLike("第8%");
//        tImageExample.or().andImageIdLessThan(1472252);
        return tImageMapper.selectByExample(tImageExample);
    }

    @RequestMapping("/test3")
    public Object test3(){
        // selectAll()
//        TImageExample.Criteria criteria = tImageExample.createCriteria();
        PageHelper.startPage(1,10);
        TImageExample tImageExample = new TImageExample();
        return new PageInfo(tImageMapper.selectByExample(tImageExample));
    }

    @RequestMapping("/insertOne")
    public Object insertOne(){
        TImage tImage = new TImage();
//        tImage.setImageId(1);
        tImage.setTitle("t1_test"+System.currentTimeMillis());
        tImage.setPath("p1_test" + System.currentTimeMillis());
        tImage.setSmallImage("s1_test" + System.currentTimeMillis());
        tImage.setIsDelete("0");
        tImage.setIsEnable("1");
        tImage.setCreateTime(new Date());
        return tImageMapper.insert(tImage);
    }

}
