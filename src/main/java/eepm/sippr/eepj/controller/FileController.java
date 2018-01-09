package eepm.sippr.eepj.controller;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {
	
	private static Logger log = LoggerFactory.getLogger(CourseController.class);
	
	/*
	 * file upload
	 */
	
	//request: /idea/upload
	@RequestMapping(value="/upload",method=RequestMethod.GET )
	public String showUpload() {
		return "file/upload";
	}
	
	//request do: /idea/doUpload
	@RequestMapping(value="/doUpload",method=RequestMethod.POST )
	public String doUpload(@RequestParam("file") MultipartFile file) throws IOException {
		log.debug("文件处理中...");
		if(!file.isEmpty()) {
			//linux路径->{对应}->Windows路径[D:\\User\\yjg\wk_ssm\Uploads],目录自动建立。
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File("/Users/yjg/wk_ssm/Uploads",System.currentTimeMillis()+file.getOriginalFilename()));
		}
		return "file/success";
	}
}
