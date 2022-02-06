package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberListServletV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberfromControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV1",urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet { //처음에 생성이될떄

    private Map<String,ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() { //호출되면 각 키에 맞게 저장했음 맵에.
        controllerMap.put("/front-controller/v1/members/new-form",new MemberfromControllerV1());
        controllerMap.put("/front-controller/v1/members/save",new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members",new MemberListServletV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); //주어진 uri 받고

        ControllerV1 controller = controllerMap.get(requestURI); //맵에 할당된 controller가 나옴
        if(controller==null){ //없을 경우
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request,response);
    }
}