package com.epam.courses.web;

import com.epam.courses.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xalf on 11/10/14.
 */
@Controller
@RequestMapping("/mvc")
public class UserController {

  @RequestMapping(value= {"/"})
  public ModelAndView launchInputForm() {
    ModelAndView model = new ModelAndView( "inputForm", "user", new User());
    return model;
  }

  @RequestMapping(value= {"/submitData"})
  public ModelAndView getInputForm(@RequestParam("name")String userName) {
    User user = new User();
    user.setName(userName);
    ModelAndView model = new ModelAndView( "displayResults", "user", user);
    return model;

  }

  @RequestMapping("/showuser.do")
  public String show(@RequestParam("id")String id) {
    return "jsp/user.jsp";
  }

}
