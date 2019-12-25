package xyz.mdou.quickstart.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@interface Task {
    String desc() default "";
}

@Task(desc = "annotation task on type")
class AnnotationType {

}

class AnnotationMethod {

    @Task(desc = "annotation task on method")
    private String method(String name) {
        return name;
    }
}

public class AnnotationMain {

    public static void main(String[] args) {
        AnnotationMain at = new AnnotationMain();
        at.parseClass(AnnotationType.class);
        at.parseMethod(AnnotationMethod.class);
    }

    private void parseMethod(Class<?> clazz) {
        try {
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);
                Task demoMethod = method.getAnnotation(Task.class);
                if (demoMethod != null) {
                    System.out.println(demoMethod.desc());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseClass(Class<?> clazz) {
        try {
            Task task = clazz.getAnnotation(Task.class);
            if (task != null) {
                System.out.println(task.desc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


