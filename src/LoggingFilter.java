import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
public class LoggingFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        System.out.println("LoggingFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Servlet avant le filtre");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("Servlet apres le filtre");
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
        System.out.println("LoggingFilter destroy");
    }
}
