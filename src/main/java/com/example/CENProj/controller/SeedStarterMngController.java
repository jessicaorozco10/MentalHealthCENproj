package com.example.CENProj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Controller
public class SeedStarterMngController {
    @ModelAttribute("allTypes")
    public List<String> populateTypes() {
        List<String> test = new LinkedList<>();
        test.add("Hello");
        test.add("yes");
        return test;
    }

    @RequestMapping({"/","/seedstartermng"})
    public String showSeedstarters() {
        return "seedstartermng";
    }

    @RequestMapping(value="/seedstartermng", params={"save"})
    public String saveSeedstarter(final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "seedstartermng";
        }
        model.clear();
        return "redirect:/seedstartermng";
    }
}
