package shop.mtcoding.hiberpc.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyBlackListFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 버퍼를 비우면 컨트롤러에서 버퍼를 읽지 못함 - 다시 채울 순 있지만 이렇게 사용하지 말기
        // x-www-form-tulencoded

        String value = request.getParameter("value");

        if (value == null) {
            response.setContentType("text/plain; charset=utf-8;");
            response.getWriter().println("value 파라미터를 전송해주세요");
            return;
        }

        if (value.equals("babo")) {
            response.setContentType("text/plain; charset=utf-8;");
            response.getWriter().println("당신은 블랙리스트 등록됨");
            return;
        }

        chain.doFilter(request, response);
    }
}
