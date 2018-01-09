package eepm.sippr.eepj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import eepm.sippr.eepj.model.Course;
import eepm.sippr.eepj.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {

	private static Logger log = LoggerFactory.getLogger(CourseController.class);
	
	private CourseService courseService;
	@Autowired
	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	/*
	 * course_overview
	 */
	
	//普通方式 
	//request: /idea/course/view?courseId=7
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String viewCourse(@RequestParam("courseId") Integer courseId,Model model) {
		log.debug("in viewCourse,courseId = {}",courseId);
		
		Course course = courseService.getCourseById(courseId);
		model.addAttribute(course);
		return "course_overview";
	}
	//现代方式 Restful
	//request: /idea/course/view2/8
	@RequestMapping(value="/view2/{courseId}",method=RequestMethod.GET)
	public String ViewCourse2(@PathVariable("courseId") Integer courseId,Map<String,Object> model) {
		log.debug("in ViewCourse2,courseId = {}",courseId);
		
		Course course = courseService.getCourseById(courseId);
		model.put("course", course);
		
		return "course_overview";
	}
	//传统方式 HttpServletRequest [or HttpSession]
	//request: /idea/course/view3?courseId=9
	@RequestMapping("view3")
	public String ViewCourse3(HttpServletRequest req) {
		Integer courseId = Integer.valueOf(req.getParameter("courseId")) ;
		log.debug("in ViewCourse3,courseId = {}", courseId);
		Course course = courseService.getCourseById(courseId);
		req.setAttribute("course", course);
		return "course_overview";
	}
	
	/*
	 * course_json
	 */
	
	@RequestMapping(value="/{courseId}",method=RequestMethod.GET)
	public @ResponseBody Course getCourseJsonData(@PathVariable Integer courseId) {
		return courseService.getCourseById(courseId);
	}
	@RequestMapping(value="/jsontype/{courseId}",method=RequestMethod.GET)
	public ResponseEntity<Course> getCourseJsonData2(@PathVariable Integer courseId){
		Course course = courseService.getCourseById(courseId);
		return new ResponseEntity<Course>(course, HttpStatus.OK);
	}
	
	/*
	 * course_admin
	 */
	
	//request: /idea/course/admin?add
	@RequestMapping(value="/admin",method=RequestMethod.GET,params="add")
	public String CreateCourse() {
		return "course_admin/edit";
	}
	//request do: /idea/course/save
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String doSave(@ModelAttribute Course course) {
		course.setCourseId(100);//模拟服务端生成唯一Id
		log.debug("Info of created Course:");
		log.debug(ReflectionToStringBuilder.toString(course));
		
		//持久化...
		
		//redirect: *后接路径不能加/*
		return "redirect:view2/"+course.getCourseId();
	}
	
}
