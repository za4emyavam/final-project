package com.myfp.fp.controller;

import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class NumberFormatTag extends SimpleTagSupport {

    private String lang;
    private String number;

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void doTag() throws IOException {
        try {
            if (!lang.equals("en")) {
                number = number.replace(".", ",");
            }
            getJspContext().getOut().write(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
