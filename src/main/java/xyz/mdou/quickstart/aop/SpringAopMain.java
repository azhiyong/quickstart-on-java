package xyz.mdou.quickstart.aop;

import xyz.mdou.quickstart.aop.service.TaskService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAopMain {
    private static final String[] CONFIG_FILES = new String[]{
            "classpath:spring-config.xml"
    };

    private static final SpringAopMain INSTANCE = new SpringAopMain();

    private ClassPathXmlApplicationContext application;

    private SpringAopMain() {
        application = new ClassPathXmlApplicationContext(CONFIG_FILES);
    }

    private static SpringAopMain getInstance() {
        return INSTANCE;
    }

    private TaskService getAopService() {
        return application.getBean(TaskService.class);
    }

    public static void main(String[] args) {
        SpringAopMain.getInstance().getAopService().doTask();
    }
}
