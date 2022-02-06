package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import org.springframework.web.method.annotation.ModelFactory;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String,String> paramMap); //서블릿에 종속적이지않ㅇ므
}
