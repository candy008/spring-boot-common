package com.imfbp.boot.common.utils.fastdfs.controller;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.DefaultFastFileStorageClient;
import com.imfbp.boot.common.web.result.Result;

@RequestMapping("/fastdfs")
@RestController
public class FastdfsController {

	@Resource
	private DefaultFastFileStorageClient storageClient;

	private static final Logger LOG = LoggerFactory.getLogger(FastdfsController.class);
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Result upload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception {
		LOG.debug("===================上传文件开始=====================");
		Result result = new Result();
		if(file==null){
			result.setSuccess(false);
			result.setSuccessMessage("没有接受到上传文件！");;
			return result;
		}
		String filename = file.getOriginalFilename();
		LOG.debug("===================fileName is"+ filename +"=====================");
		long size = file.getSize();
		String prefix=filename.substring(filename.lastIndexOf(".")+1);
		StorePath store = storageClient.uploadFile(file.getInputStream(), size, prefix, null);
		
		if(store==null){
			result.setSuccess(false);
			result.setSuccessMessage("上传到文件服务器失败失败");;
			return result;
		}
		
		LOG.debug("===================result is"+ store +"=====================");
		result.setSuccess(true);
		result.setSuccessMessage("上传成功");
		result.addDefaultModel("store", store);
		return result;
	}
	
	@RequestMapping(value = "/richTextPicUpload", method = RequestMethod.POST)
	public Map<String, Object> richTextPicUpload(@RequestParam("upfile") MultipartFile file, HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String,Object>();
		try {
			
			LOG.debug("===================上传文件开始=====================");
			if(file==null){
				result.put("state", "ERROR");
				result.put("message", "没有接受到上传文件！");
				return result;
			}
			
			String filename = file.getOriginalFilename();
			LOG.debug("===================fileName is"+ filename +"=====================");
			long size = file.getSize();
			String prefix=filename.substring(filename.lastIndexOf(".")+1);
			StorePath store = storageClient.uploadFile(file.getInputStream(), size, prefix, null);
			
			if(store==null){
				result.put("state", "ERROR");
				result.put("message", "上传到文件服务器失败失败!");
				return result;
			}
			
			LOG.debug("===================result is"+ store +"=====================");
			result.put("originalName", filename);
			result.put("name", filename);
			result.put("url",  "fastdfs/img/" + store.getFullPath());
			result.put("size", size);
			result.put("type", prefix);
			result.put("state", "SUCCESS");
			result.put("original", filename);
		} catch (Exception e) {
			result.put("state", "ERROR");
			LOG.error("上传失败",e);
		}
		return result;
	}

	@RequestMapping(value = "/img/{groupName}/**", method = RequestMethod.GET)
	public Object download(@PathVariable String groupName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = extractPathFromPattern(request);
		DownloadByteArray callback = new DownloadByteArray();
		byte[] content = storageClient.downloadFile(groupName, path, callback);
		// 支持文本编辑器 附件 下载 
//		response.setContentType("image/png");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(content);
		return content;
	}
	@RequestMapping(value = "/deleteFile", method = {RequestMethod.POST, RequestMethod.GET })
	public Object deleteFile(String fullpath, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("state", "SUCCESS");
		result.put("message", "删除成功");
		try {
			String groupName = fullpath.substring(0, fullpath.indexOf("/"));
			String path = fullpath.substring(fullpath.indexOf("/") + 1);
			storageClient.deleteFile(groupName, path);
			;
		} catch (Exception e) {
			result.put("state", "fail");
			result.put("message", "删除失败");
			LOG.error("删除失败",e);
		}
		return result;
	}

	private String extractPathFromPattern(final HttpServletRequest request) {
		String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
		return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
	}

}
