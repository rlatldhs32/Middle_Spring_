package hello.springmvc.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String LogTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        log.trace("trace log={}", name);
        log.debug("debug log={}", name); //위에 두개는 안남음
        log.warn("warn log={}", name);
        log.error("error log={}", name);
        log.info("info log = {}", name);
        return "ok";

    }
}
