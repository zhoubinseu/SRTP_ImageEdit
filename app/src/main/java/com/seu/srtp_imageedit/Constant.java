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
}
