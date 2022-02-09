package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV3",urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet { //처음에 생성이될떄

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() { //호출되면 각 키에 맞게 저장했음 맵에.
        controllerMap.put("/front-controller/v3/members/new-form",new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save",new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI(); //주어진 uri 받고

        ControllerV3 controller = controllerMap.get(requestURI); //맵에 할당된 controller가 나옴
        if(controller==null){ //없을 경우
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        Map<String, String> paramMap = createParamMap(request); // 파라미터 가져옴
        ModelView mv = controller.process(paramMap); // 그 파라미터 맵으로 모델뷰 만듬
        String viewName = mv.getViewName(); // 논리이름 : new-form ( 그논리이름으로 경로 반환해줌)
        MyView view = viewResolver(viewName); //뷰를 제대로 띄워줌

        view.render(mv.getModel(),request,response);
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