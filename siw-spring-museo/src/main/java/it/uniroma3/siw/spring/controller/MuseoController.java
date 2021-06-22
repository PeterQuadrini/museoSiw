package it.uniroma3.siw.spring.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.spring.service.OperaService;




@Controller
public class MuseoController {

  @Autowired
  private OperaService operaService;
	
  @RequestMapping(value={"/home","/"}, method=RequestMethod.GET)
  public String goHome(Model model) {

	  return "index.html";
  }
  @RequestMapping(value="/areaP", method=RequestMethod.GET)
  public String goAreaP() {
	  return "AreaPersonale.html";
  }
  
  


  

}
