package com.portfolio.main.view.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TodoController {
    @GetMapping("/todo")
    public String todo() {
        return "todo/todo";
    }
}
