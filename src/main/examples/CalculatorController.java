package ru.artur.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {

    @GetMapping("/calc")
    public String calculating(@RequestParam(value = "numb1", required = false) Double numb1,
                              @RequestParam(value = "numb2", required = false) Double numb2,
                              @RequestParam(value = "action", required = false) String action, Model model) {
        Double answer = 0D;
        switch (action) {
            case "multiplication":
            case "*":
                answer = numb1 * numb2;
                break;
            case "addition":
            case "+":
                answer = numb1 + numb2;
                break;
            case "subtraction":
            case "-":
                answer = numb1 - numb2;
                break;
            case "division":
            case "/":
                if (numb2 != 0) {
                    answer = numb1 / numb2;
                } else {
                    throw new ArithmeticException();
                }
                break;
            default:
                model.addAttribute("answer", numb1 + action + numb2 + " = " + answer);
                break;

        }
        model.addAttribute("answer", numb1 + action + numb2 + " = " + answer);


        return "/calculator/calc";
    }
}
