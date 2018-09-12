package main;

public class ResultVo {
    private int wordNum = 0;
    private int charNum = 0;
    private int lineNum = 0;
    private int spaceLineNum = 0;
    private int annotationNum = 0;
    private String fileName = "";

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public int getCharNum() {
        return charNum;
    }

    public void setCharNum(int charNum) {
        this.charNum = charNum;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int getSpaceLineNum() {
        return spaceLineNum;
    }

    public void setSpaceLineNum(int spaceLineNum) {
        this.spaceLineNum = spaceLineNum;
    }

    public int getAnnotationNum() {
        return annotationNum;
    }

    public void setAnnotationNum(int annotationNum) {
        this.annotationNum = annotationNum;
    }

    @Override
    public String toString() {
        return "ResultVo{" +
                "wordNum=" + wordNum +
                ", charNum=" + charNum +
                ", lineNum=" + lineNum +
                ", spaceLineNum=" + spaceLineNum +
                ", annotationNum=" + annotationNum +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
