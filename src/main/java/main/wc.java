package main;

import oracle.jrockit.jfr.JFR;
import sun.security.krb5.internal.PAData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class wc {
    public static void main(String[] args) {
        JFrame frame = new JFrame("主页面");

        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel);

        // 设置界面可见
        frame.setVisible(true);
    }

    private static void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("请选择文件:");
        userLabel.setBounds(10, 20, 80, 25);
        JLabel findFiles = new JLabel("递归回显:");
        findFiles.setBounds(10, 60, 80, 25);
        panel.add(userLabel);
        panel.add(findFiles);

//        递归读文件
        JButton jButton2 = new JButton("请选择文件");
        jButton2.setBounds(100, 60, 100, 30);
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("C:\\Users\\hp\\Desktop");
                chooser.setMultiSelectionEnabled(false);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnVal;
                returnVal = chooser.showOpenDialog(jButton2);
                if (returnVal == JFileChooser.OPEN_DIALOG) {
                    System.out.println(chooser.getSelectedFile());
                    List<String> pathList = findFilePath(chooser.getSelectedFile().getAbsolutePath());
                    if (pathList != null) {
                        JFrame frame = new JFrame("文件列表");
                        frame.setSize(350, 200);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        JPanel panel = new JPanel();
                        frame.add(panel);
                        frame.setVisible(true);
                        int step = 10;
                        for (String path : pathList) {
                            System.out.println(path);
                            JLabel fileName = new JLabel("文件:"+path);
                            fileName.setBounds(10, 10+step, 80, 25);
                            panel.add(fileName);
                            step += 10;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "该文件夹下不存在符合条件的文件", "扫描失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        panel.add(jButton2);
        JButton jButton = new JButton("请选择文件");
        jButton.setBounds(100, 20, 100, 30);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("C:\\Users\\hp\\Desktop");
                chooser.setMultiSelectionEnabled(true);

                int returnVal;
                returnVal = chooser.showOpenDialog(jButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = chooser.getSelectedFiles();
                    for (File f : files) {
                        if (f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".") + 1).equals("docx")) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ResultVo vo = ReadUtils.readDoc(f);
                                    if (vo == null) {
                                        JOptionPane.showMessageDialog(null, "文件路径错误", "扫描失败", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        JFrame frame = new JFrame("文件信息");
                                        frame.setSize(350, 200);
                                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        JPanel panel = new JPanel();
                                        frame.add(panel);
                                        frame.setVisible(true);
                                        placeComponents(panel, vo);
                                    }
                                }
                            }).start();
                        } else if (f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".") + 1).equals("txt") ||
                                f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf(".") + 1).equals("java")) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    ResultVo vo = ReadUtils.readTxt(f);
                                    if (vo == null) {
                                        JOptionPane.showMessageDialog(null, "文件路径错误", "扫描失败", JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        System.out.println(1);
                                        JFrame frame = new JFrame("文件信息");
                                        frame.setSize(350, 200);
                                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                        JPanel panel = new JPanel();
                                        frame.add(panel);
                                        frame.setVisible(true);
                                        placeComponents(panel, vo);
                                    }
                                }
                            }).start();
                        } else {
                            JOptionPane.showMessageDialog(null, "暂不支持对该类型文件的扫描 ", "错误 ", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        panel.add(jButton);
    }

    private static void placeComponents(JPanel panel, ResultVo vo) {
        JLabel fileName = new JLabel("文件名:" + vo.getFileName());
        fileName.setBounds(10, 30, 80, 25);
        panel.add(fileName);

        JLabel charNum = new JLabel("字符数:" + vo.getCharNum());
        charNum.setBounds(10, 40, 80, 25);
        panel.add(charNum);

        JLabel wordNum = new JLabel("词数：" + vo.getWordNum());
        wordNum.setBounds(10, 55, 80, 25);
        panel.add(wordNum);

        JLabel lineNum = new JLabel("行数：" + vo.getLineNum());
        lineNum.setBounds(10, 70, 80, 25);
        panel.add(lineNum);

        JLabel spaceLineNum = new JLabel("空行数：" + vo.getSpaceLineNum());
        spaceLineNum.setBounds(10, 85, 80, 25);
        panel.add(spaceLineNum);

        JLabel annotationNum = new JLabel("注释行数：" + vo.getAnnotationNum());
        annotationNum.setBounds(10, 100, 80, 25);
        panel.add(annotationNum);
    }

    public static List<String> findFilePath(String fileSaveRootPath) {
        List<String> pathList = new ArrayList<>();
        researchfile(new File(fileSaveRootPath), pathList);
        if (pathList.size() >= 1)
            return pathList;
        else
            return null;
    }

    public static void researchfile(File file, List<String> pathList) {
        if (file.isDirectory()) {
            File[] filearry = file.listFiles();
            for (File f : filearry) {
                researchfile(f, pathList);
            }
        } else {
            String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (fileType.equals("java") || fileType.equals("docx") || fileType.equals("txt")) {
                pathList.add(file.getAbsolutePath());
            }
        }
    }
}