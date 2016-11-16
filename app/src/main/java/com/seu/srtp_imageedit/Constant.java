package com.seu.srtp_imageedit;

/**
 * 单例，保存一些常量
 * Created by 周彬 on 2016/10/6.
 */

public class Constant {
    private static Constant constant;
    private Constant(){}
    public static Constant getInstance() {
        if (null == constant) {
            constant = new Constant();
        }
        return constant;
    }

    private int windowWidth;//屏幕宽
    private int windowHeight;//屏幕高
    private int rotateCounter;//旋转计数器，左旋-1，右旋+1
    //旋转处理是原图片的尺寸和新图片的尺寸
    private int rotateSrcW;
    private int rotateSrcH;
    private int rotateNewW;
    private int rotateNewH;

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getRotateCounter() {
        return rotateCounter;
    }

    public void setRotateCounter(int rotateCounter) {
        this.rotateCounter = rotateCounter;
    }

    public int getRotateSrcW() {
        return rotateSrcW;
    }

    public void setRotateSrcW(int rotateSrcW) {
        this.rotateSrcW = rotateSrcW;
    }

    public int getRotateSrcH() {
        return rotateSrcH;
    }

    public void setRotateSrcH(int rotateSrcH) {
        this.rotateSrcH = rotateSrcH;
    }

    public int getRotateNewW() {
        return rotateNewW;
    }

    public void setRotateNewW(int rotateNewW) {
        this.rotateNewW = rotateNewW;
    }

    public int getRotateNewH() {
        return rotateNewH;
    }

    public void setRotateNewH(int rotateNewH) {
        this.rotateNewH = rotateNewH;
    }
}
