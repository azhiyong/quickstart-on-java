package xyz.mdou.quickstart.aop;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

@Component("aopAspect")
public class AopAspect {

    public void doBefore(JoinPoint jp) {
        String sb = "before {" + jp.toLongString() + "}";
        System.out.println(sb);
    }

    public void doing(JoinPoint jp) {
        String sb = "doing {" + jp.toLongString() + "}";
        System.out.println(sb);
    }

    public void doAfter(JoinPoint jp) {
        String sb = "after {" + jp.toLongString() + "}";
        System.out.println(sb);
    }

    public void doThrow(JoinPoint jp, Throwable e) {
        String sb = "throw {" + jp.toLongString() + "\r\n" + e.getCause() + "}";
        System.out.println(sb);
    }

}
