package com.epam.final_task.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class YandexMapTag extends TagSupport {
    private final static String YANDEX_MAP = "<div class=\"container\">\n" +
            "  <iframe src=\"https://yandex.ru/map-widget/v1/?um=constructor%3Ac440b013ca68320ee2939c257e68def290604b6ec9f430e6f885affb70171811&amp;source=constructor\" width=\"100%\" height=\"400\" frameborder=\"0\"></iframe>\n" +
            "</div>";

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(YANDEX_MAP);
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return  SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}
