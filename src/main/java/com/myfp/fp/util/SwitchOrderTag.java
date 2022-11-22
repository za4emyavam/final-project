package com.myfp.fp.util;

import jakarta.servlet.ServletContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class SwitchOrderTag extends SimpleTagSupport {
    final ServletContext servletContext = ((PageContext) getJspContext()).getServletContext();
    @Override
    public void doTag() throws JspException, IOException {
        servletContext.getAttribute("order");
    }
}
