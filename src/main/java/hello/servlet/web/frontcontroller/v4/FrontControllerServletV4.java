package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV4",urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet { //처음에 생성이될떄

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() { //호출되면 각 키에 맞게 저장했음 맵에.
        controllerMap.put("/front-controller/v4/members/new-form",new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); //주어진 uri 받고

        ControllerV4 controller = controllerMap.get(requestURI); //맵에 할당된 controller가 나옴
        if(controller==null){ //없을 경우
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> paramMap = createParamMap(request); // 파라미터 가져옴
        Map<String,Object> model = new HashMap<>(); //추가
        String viewName = controller.process(paramMap, model);// 그 파라미터 맵으로 모델뷰 만듬

        MyView view = viewResolver(viewName); //뷰를 제대로 띄워줌
        view.render(model,request,response);
    }

    private MyView viewResolver (String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String,String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator() //모든 파라미터 이름 다 가져옴 , 돌리면서
                .forEachRemaining(paramName->paramMap.put(paramName, request.getParameter(paramName)));
        //paramName이 키 이름은 getParameter ()
        return paramMap;
    }
}