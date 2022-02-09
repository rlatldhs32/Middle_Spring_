package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3); //controller V3 구현안 애면 참
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3)handler;

        Map<String,String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);
        return null;
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator() //모든 파라미터 이름 다 가져옴 , 돌리면서
                .forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));
        //paramName이 키 이름은 getParameter ()
        return paramMap;
    }

}
