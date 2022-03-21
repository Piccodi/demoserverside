package com.example.democlient.controller;

import com.example.democlient.model.User;
import com.example.democlient.service.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MainController {

    @RequestMapping("/")
    public ModelAndView getPage(){
        var mav = new ModelAndView();
        mav.setViewName("index.html");
        return mav;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user){
        if(Validator.checkForValidity(user.getUsername(), user.getPassword())){

            return ResponseEntity.ok().body("success: true");
        }
        else{
            return new ResponseEntity("{ \"error\" : true, \"errorMessage\" : \"paswd mismatch\" }",HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }
}
