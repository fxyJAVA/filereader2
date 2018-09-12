package main;

import org.junit.Assert;

import java.io.File;
import java.util.List;

public class Test {

    //测试读取单文件docx,正常及异常处理
    @org.junit.Test
    public void readWord() {
        File file = new File("C:\\Users\\hp\\Desktop\\新建文件夹\\1.1软件工程-个人.docx");
        if (file.exists()) {
            ResultVo vo = ReadUtils.readDoc(file);
            System.out.println("charNum:" + vo.getCharNum());
            System.out.println("wordNum:" + vo.getWordNum());
            System.out.println("lineNum:" + vo.getLineNum());
            System.out.println("spaceNum:" + vo.getSpaceLineNum());
            System.out.println("annotationNum:" + vo.getAnnotationNum());
        } else {
            System.out.println("未找到指定文件");
        }
    }

    //测试读取单文件txt,.java,正常及异常处理
    @org.junit.Test
    public void readTxt() {
        File file = new File("C:\\Users\\hp\\Desktop\\Test.java");
        if (file.exists()) {
            ResultVo vo = ReadUtils.readTxt(file);
            System.out.println("charNum:" + vo.getCharNum());
            System.out.println("wordNum:" + vo.getWordNum());
            System.out.println("lineNum:" + vo.getLineNum());
            System.out.println("spaceNum:" + vo.getSpaceLineNum());
            System.out.println("annotationNum:" + vo.getAnnotationNum());
        } else {
            System.out.println("未找到指定文件");
        }
    }

    //测试读取多文件docx,正常及异常处理
    @org.junit.Test
    public void readMoreWord() {
        String[] path = {"C:\\Users\\hp\\Desktop\\新建文件夹\\1.1软件工程-个人.docx", "C:\\Users\\hp\\Desktop\\新建文件夹\\《软件需求规格说明书》.docx", "C:\\Users\\hp\\Desktop\\新建文件夹\\新建文件夹\\1.2个人项目题目.docx", "C:\\Users\\hp\\Desktop\\新建文件夹\\新建文件夹\\1.3评分标准"};
        for (String p : path) {
            File file = new File(p);
            if (file.exists()) {
                ResultVo vo = ReadUtils.readDoc(file);
                System.out.println("charNum:" + vo.getCharNum());
                System.out.println("wordNum:" + vo.getWordNum());
                System.out.println("lineNum:" + vo.getLineNum());
                System.out.println("spaceNum:" + vo.getSpaceLineNum());
                System.out.println("annotationNum:" + vo.getAnnotationNum());
            } else {
                System.out.println("\n -----未找到指定文件:"+p+"------");
            }
        }
    }

    //测试读取多文件txt,java,正常及异常处理
    @org.junit.Test
    public void readMoreTxt() {
        String[] path = {"C:\\Users\\hp\\Desktop\\新建文件夹\\tt.txt","C:\\Users\\hp\\Desktop\\新建文件夹\\新建文件夹\\1.txt","C:\\Users\\hp\\Desktop\\新建文件夹\\新建文件夹\\2.txt","C:\\Users\\hp\\Desktop\\新建文件夹\\tt"};
        for (String p : path) {
            File file = new File(p);
            if (file.exists()) {
                ResultVo vo = ReadUtils.readTxt(file);
                System.out.println("charNum:" + vo.getCharNum());
                System.out.println("wordNum:" + vo.getWordNum());
                System.out.println("lineNum:" + vo.getLineNum());
                System.out.println("spaceNum:" + vo.getSpaceLineNum());
                System.out.println("annotationNum:" + vo.getAnnotationNum());
            } else {
                System.out.println("\n -----未找到指定文件:"+p+"------");
            }
        }
    }

    //测试递归读取文件夹下所有符合条件的文件
    @org.junit.Test
    public  void recursionRead() {
        //目录存在时
        String path1 = "C:\\Users\\hp\\Desktop\\新建文件夹";
        List<String> list =  wc.findFilePath(path1);
        for (String path : list) {
            System.out.println(path);
        }

        //不存在符合的文件时
        String path2 = "C:\\Users\\hp\\Desktop\\download";
        list = wc.findFilePath(path2);
        try {
           Assert.assertNotNull(list);
        }catch (AssertionError e) {
            System.out.println("文件不存在1");
        }


        //文件目录不存在时
        String path3 = "C:\\Users\\hp\\Desktop\\新建文件夹33";
        list = wc.findFilePath(path3);
        try {
            Assert.assertNotNull(list);
        }catch (AssertionError e) {
            System.out.println("文件不存在2");
        }
    }
}
