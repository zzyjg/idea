package eepm.sippr.eepj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by yjg on 2017/12/30.
 */

@Controller
@RequestMapping("/mvc")
public class HomeController {
	@RequestMapping("/home")
	public String getHome() {
		return "home";
	}
}
