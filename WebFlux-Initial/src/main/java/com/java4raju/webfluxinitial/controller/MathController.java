package com.java4raju.webfluxinitial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java4raju.webfluxinitial.dto.Response;
import com.java4raju.webfluxinitial.service.MathService;

@RestController
@RequestMapping("math")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("square/{input}")
    public Response findSquare(@PathVariable int input){
        if(input < 10)
            throw new IllegalArgumentException();
        return this.mathService.findSquare(input);
    }

    @GetMapping("table/{input}")
    public List<Response> multiplicationTable(@PathVariable int input){
        return this.mathService.multiplicationTable(input);
    }

}
