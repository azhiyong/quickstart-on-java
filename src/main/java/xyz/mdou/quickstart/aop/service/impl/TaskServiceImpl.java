package xyz.mdou.quickstart.aop.service.impl;

import xyz.mdou.quickstart.aop.service.TaskService;
import org.springframework.stereotype.Service;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    @Override
    public void doTask() {
        System.out.println("TaskServiceImpl doTask()");
    }
}
