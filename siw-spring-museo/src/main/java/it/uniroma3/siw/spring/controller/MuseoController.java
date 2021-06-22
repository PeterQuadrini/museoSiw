package it.uniroma3.siw.spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;





@Controller
public class MuseoController {

	
  @RequestMapping(value={"/home","/"}, method=RequestMethod.GET)
  public String goHome(Model model) {

	  return "index.html";
  }
  @RequestMapping(value="/areaP", method=RequestMethod.GET)
  public String goAreaP() {
	  return "AreaPersonale.html";
  }
  
  


  

}
