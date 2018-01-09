package eepm.sippr.eepj.service.Impl;

import eepm.sippr.eepj.model.Course;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import eepm.sippr.eepj.model.Chapter;
import eepm.sippr.eepj.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService {

	public Course getCourseById(Integer courseId) {
		// TODO Auto-generated method stub
		//return null;
		
		Course course = new Course();
		course.setCourseId(courseId);
		course.setTitle("python parent and son.");
		course.setImgPath("assets/imgs/course-img.jpg");
		course.setLearningNum(619800);
		course.setLevel(2);
		course.setLevelDesc("进阶级");
		course.setDuration(2600L);
		course.setDescr("父子一块进阶Python，为未来智能世界做好准备。");
		
		List<Chapter> chapterList = new ArrayList<Chapter>();
		//提取章节数据
		warpChapterList(courseId,chapterList);
		course.setChapterList(chapterList);
		
		return course;
	}

	private void warpChapterList(Integer courseId, List<Chapter> chapterList) {
		// TODO Auto-generated method stub
		Chapter chapter = new Chapter();
		chapter.setId(1);
		chapter.setCourseId(courseId);
		chapter.setOrder(1);
		chapter.setTitle("第1章 多线程背景知识介绍");
		chapter.setDescr("本章将介绍与多线程编程相关的背景概念");	
		chapterList.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(2);
		chapter.setCourseId(courseId);
		chapter.setOrder(2);
		chapter.setTitle("第2章 Java 线程初体验");
		chapter.setDescr("Java语言层面对线程的支持，如何创建，启动和停止线程。如何使用常用的线程方法。用隋唐演义理解线程的代码");
		chapterList.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(3);
		chapter.setCourseId(courseId);
		chapter.setOrder(3);
		chapter.setTitle("第3章 Java 线程的正确停止");
		chapter.setDescr("本章讨论如何正确的停止一个线程，既要线程停得了，还得线程停得好。");		
		chapterList.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(4);
		chapter.setCourseId(courseId);
		chapter.setOrder(4);
		chapter.setTitle("第4章 线程交互");
		chapter.setDescr("争用条件，线程的交互，及死锁的成因及预防。");		
		chapterList.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(5);
		chapter.setCourseId(courseId);
		chapter.setOrder(5);
		chapter.setTitle("第5章 进阶展望");
		chapter.setDescr("简单介绍 Java 并发相关的类，及常用的多线程编程模型。");		
		chapterList.add(chapter);
	}

}
