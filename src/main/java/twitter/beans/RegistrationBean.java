package twitter.beans;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.faces.webapp.FacesServlet;

@Service
public class RegistrationBean {
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new FacesServlet(), "*.jsf");
    }
}
