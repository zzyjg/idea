package eepm.sippr.eepj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.omg.CORBA.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.alibaba.fastjson.JSON;

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
	 * course_mix
	 */
	
	//普通方式 
	//request: /idea/course/view?courseId=7
	@RequestMapping(value="/view",method=RequestMethod.GET)
	public String viewCourse(@RequestParam("courseId") Integer courseId,Model model) {
		log.debug("in viewCourse,courseId = {}",courseId);
		
		Course course = courseService.getCourseById(courseId);
		model.addAttribute(course);
		return "course_mix";
	}
	//现代方式 Restful
	//request: /idea/course/view2/8
	@RequestMapping(value="/view2/{courseId}",method=RequestMethod.GET)
	public String ViewCourse2(@PathVariable("courseId") Integer courseId,Map<String,Object> model) {
		log.debug("in ViewCourse2,courseId = {}",courseId);
		
		Course course = courseService.getCourseById(courseId);
		model.put("course", course);
		
		return "course_mix";
	}
	//传统方式 HttpServletRequest [or HttpSession]
	//request: /idea/course/view3?courseId=9
	@RequestMapping("view3")
	public String ViewCourse3(HttpServletRequest req) {
		Integer courseId = Integer.valueOf(req.getParameter("courseId")) ;
		log.debug("in ViewCourse3,courseId = {}", courseId);
		Course course = courseService.getCourseById(courseId);
		req.setAttribute("course", course);
		return "course_mix";
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
	
	/*
	 * return json
	 */
	
	//处理JSON的方式一
	//@RequestMapping可增加参数[,produces=MediaType.APPLICATION_JSON_UTF8_VALUE]限定MediaType为JSON,但推荐前端请求时指定。
	@RequestMapping(value="/jsonbody/{courseId}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Course getCourseJsonData(@PathVariable Integer courseId,HttpServletResponse response) {
		return courseService.getCourseById(courseId);
	}
	
	//处理JSON的方式二
	//相比较@ResponseBody，ResponseEntity方式能通过更简单的书写方式，控制响应状态及自由转化等。
	@RequestMapping(value="/jsonentity/{courseId}",method=RequestMethod.GET)
	public ResponseEntity<Course> getCourseJsonData2(@PathVariable Integer courseId){
		Course course = courseService.getCourseById(courseId);
		//方式1:、简洁写法
		return ResponseEntity.ok().body(course);
		//方式2、体现控制响应状态的距离
		//return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(course);
		//方式3、原始写法
		//return new ResponseEntity<Course>(course, HttpStatus.OK);
	}
	
	/*
	 * course_json
	 */
	
	//request: /idea/course/coursejson/7
	@RequestMapping(value="/coursejson/{courseId}",method=RequestMethod.GET)
	public String getCourseWithJsonData(@PathVariable Integer courseId,Model model) {
		model.addAttribute("courseId",courseId);
		return "course_json";
	}
	
}
