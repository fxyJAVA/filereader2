package main;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Map;
import java.util.regex.Pattern;

public class ReadUtils {
    static Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5 \\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b]");


    //docx文件
    public static ResultVo readDoc(File file) {
        int wordNum = 0;
        int charNum = 0;
        int lineNum = 0;
        int spaceLineNum = 0;
        int annotationNum = 0;

        try (InputStream is = new FileInputStream(file)) {
            StringBuffer sb = new StringBuffer();
            XWPFDocument xp = new XWPFDocument(is);
            XWPFWordExtractor wordReader = new XWPFWordExtractor(xp);
            String[] line = wordReader.getText().split("\n");

            if (line.length < 1 || line == null) {
                return null;
            }

            for (int j = 0; j < line.length; j++) {
                System.out.println(line[j]);
                lineNum += 1;
                if (line[j].trim().toString().length() <= 1) {
                    spaceLineNum += 1;
                    continue;
                }
                String[] result = calcuChar(line[j]).split("==");
                wordNum += Integer.parseInt(result[0]);
                charNum += Integer.parseInt(result[1]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultVo vo = new ResultVo();
        vo.setWordNum(wordNum);
        vo.setCharNum(charNum);
        vo.setLineNum(lineNum);
        vo.setSpaceLineNum(spaceLineNum);
        vo.setAnnotationNum(annotationNum);
        vo.setFileName(file.getAbsolutePath());
        return vo;
    }

    //txt文件 能读未加密的文件？
    public static ResultVo readTxt(File file) {
        int wordNum = 0;
        int charNum = 0;
        int lineNum = 0;
        int spaceLineNum = 0;
        int annotationNum = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"))) {
            //用正则匹配中文
            String line;
            boolean flag = false;
            int temp = 0;
            while ((line = br.readLine()) != null) {
                System.out.println("该行内容为：" + line);
                lineNum += 1;
                System.out.println();
                if (line.trim().length() <= 1) {
                    spaceLineNum += 1;
                    continue;
                }


                if (line.trim().contains("//")) {
                    annotationNum += 1;
                    continue;
                }
                if (line.trim().contains("/*")) {
                    temp += 1;
                    flag = true;
                }

                if (!line.trim().contains("/*") && !line.trim().contains("*/") && line.trim().contains("*")) {
                    if (flag) {
                        temp += 1;
                    }
                }

                if (line.trim().contains("*/")) {
                    if (flag) {
                        flag = false;
                        annotationNum = temp + 1;
                        temp = 0;
                        if (lineNum > annotationNum) {
                            lineNum = lineNum - annotationNum;
                        }
                        continue;
                    }
                }

                String[] result = calcuChar(line).split("==");
                wordNum += Integer.parseInt(result[0]);
                charNum += Integer.parseInt(result[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultVo vo = new ResultVo();
        vo.setWordNum(wordNum);
        vo.setCharNum(charNum);
        vo.setLineNum(lineNum);
        vo.setSpaceLineNum(spaceLineNum);
        vo.setAnnotationNum(annotationNum);
        vo.setFileName(file.getName());
        return vo;
    }

    //计算方法
    public static String calcuChar(String line) {
        int wordNum = 0;
        int charNum = 0;
        for (int i = 0; i < line.split("\\s+").length; i++) {
            String tempWord = line.split("\\s+")[i].trim();
            if ("".equals(tempWord) || tempWord == null) {
                continue;
            }

            if (pattern.matcher(tempWord).find()) {
                char c[] = tempWord.toCharArray();
                //汉字前一个字母是否已经被算入
                boolean flag = true;
                if (pattern.matcher(String.valueOf(c[0])).matches()) {
                    wordNum += 1;
                    charNum += 2;
                    flag = false;
                } else {
                    charNum += 1;
                }
                for (int k = 1; k < c.length; k++) {
                    if (pattern.matcher(String.valueOf(c[k])).matches()) {
                        if (flag) {
                            wordNum += 1;
                            flag = false;
                        }
                        wordNum += 1;
                        charNum += 2;
                    } else {
                        flag = true;
                        charNum += 1;
                    }
                }
                if (flag) {
                    wordNum += 1;
                }
            } else {
                wordNum += 1;
                charNum += line.split("\\s+")[i].trim().length();
            }
        }
        return wordNum + "==" + charNum;
    }
}
