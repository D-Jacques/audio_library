package com.myaudiolibrary.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(method = RequestMethod.GET)
    public String accueil(final ModelMap modelMain){
        return "accueil";
    }
}
