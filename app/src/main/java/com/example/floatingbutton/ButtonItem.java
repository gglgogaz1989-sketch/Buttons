package com.example.floatingbutton;

public class ButtonItem {
    public String name;       // название кнопки
    public String keyCode;    // клавиша (например "SPACE")
    public boolean holdEnabled; // зажатие
    public int repeatInterval; // интервал повторения в мс

    public ButtonItem(String name, String keyCode, boolean holdEnabled, int repeatInterval) {
        this.name = name;
        this.keyCode = keyCode;
        this.holdEnabled = holdEnabled;
        this.repeatInterval = repeatInterval;
    }
}
