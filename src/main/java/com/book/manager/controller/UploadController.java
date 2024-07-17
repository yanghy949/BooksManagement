package com.book.manager.controller;

import com.book.manager.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 公用的上传类
 * @author Administrator
 *
 */
@RequestMapping("/upload")
@Controller
public class UploadController {

	@Value("${yuanmu.sufix}")
	private String uploadPhotoSufix;

	@Value("${yuanmu.maxsize}")
	private long uploadPhotoMaxSize;

	@Value("${yuanmu.uploadPath}")
	private String uploadPhotoPath;//文件保存位置

	private Logger log = LoggerFactory.getLogger(UploadController.class);

	/**
	 * 图片统一上传类
	 * @param photo
	 * @return
	 */
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> uploadPhoto(@RequestParam(name="photo",required=true)MultipartFile photo){

		//构造返回值
		Map<String, Object> ret = new HashMap<>();
		ret.put("type","上传失败");
		//1、获取图片 --判断图片名称是否为指定类型
		String originalFilename = photo.getOriginalFilename();
		//1.1 解析出上传图片的类型.jpg
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
		//1.2 获取到指定的图片类型有哪些   uploadPhotoSufix获取到了配置文件数据
		//1.3 判断1.1是否在1.2
		if(!uploadPhotoSufix.contains(suffix)){
			//如果上传文件的类型不包含在指定图片类型
			ret.put("message","上传的图片类型错误");
			return ret;
		}
		//如果包含在内  数据文件大小校验
		// 上传文件的大小不能超过 uploadPhotoMaxSize
		long size = photo.getSize();
		if(size/1024 > uploadPhotoMaxSize){
			ret.put("message","上传的图片大小超过限制");
			return ret;
		}
		//图片存储 指定存储的路径
		File file = new File(uploadPhotoPath+StringUtil.getFormatterDate(new Date(), "yyyyMMdd")+"/");
		if(!file.exists()){
			// 不存在则创建路径
			file.mkdirs();
		}
		//图片名称进行重命名
		String filename = StringUtil.getFormatterDate(new Date(),"yyyyMMdd")+"/"+System.currentTimeMillis()+suffix;
		//保存图片

		try{
			photo.transferTo(new File(uploadPhotoPath + "/" + filename));
		}catch (IOException e){
			e.printStackTrace();
		}
		ret.put("message","上传成功");
		ret.put("data",filename);
		ret.put("type","success");
		return ret;
	}


}











