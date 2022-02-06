package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberfromControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV2",urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet { //처음에 생성이될떄

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() { //호출되면 각 키에 맞게 저장했음 맵에.
        controllerMap.put("/front-controller/v2/members/new-form",new MemberfromControllerV2());
        controllerMap.put("/front-controller/v2/members/save",new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members",new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); //주어진 uri 받고

        ControllerV2 controller = controllerMap.get(requestURI); //맵에 할당된 controller가 나옴
        if(controller==null){ //없을 경우
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request,response);
    }
}