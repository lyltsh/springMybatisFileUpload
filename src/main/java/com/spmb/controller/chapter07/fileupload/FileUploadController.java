package com.spmb.controller.chapter07.fileupload;

import com.spmb.domain.chapter07.fileupload.User;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @Description:
 * @Author: leiyulin
 * @date: 2018/6/9
 */
@Controller
@RequestMapping(value = "/chapter07/fileupload")
public class FileUploadController {

    private static final String PREFIX = "/chapter07/fileupload/";
    private static final Log logger = LogFactory.getLog(FileUploadController.class);

    @RequestMapping(value = "{formName}")
    public String loginForm(@PathVariable String formName) {
        logger.info(formName);
        //动态跳转页面
        return PREFIX + formName;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request,
                         @RequestParam("description") String description,
                         @RequestParam("file") MultipartFile file) {
        System.out.println(description);
        //如果文件不为空，写入上传路径
        if (!file.isEmpty()) {
            //上传文件路径
            String path = request.getServletContext().getRealPath("/images/");
            //上传文件名
            String fileName = file.getName();
            System.out.println("path:" + path + ",fineName:" + fileName);
            File filePath = new File(path, fileName);
            //判断路径是否存在，如果不存在则创建一个
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件之中
            try {
                file.transferTo(new File(path + File.separator + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return PREFIX + "success";
        }
        return PREFIX + "error";
    }

    @RequestMapping(value = "/register")
    public String register(HttpServletRequest request,
                           @ModelAttribute User user,
                           Model model) {
        System.out.println(user.getUsername());
        //如果文件不为空，写入上传路径
        if (!user.getImage().isEmpty()) {
            //上传路径
            String path = request.getServletContext().getRealPath("/images/");
            //上传文件名
            String fileName = user.getImage().getOriginalFilename();
            System.out.println("path:" + path + ",fineName:" + fileName);
            File filePath = new File(path, fileName);
            //判断路径是否存在，如果不存在则创建一个
            if (!filePath.getParentFile().exists()) {
                filePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件之中
            try {
                user.getImage().transferTo(new File(path + File.separator + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("user", user);
            return PREFIX + "userInfo";
        }
        return PREFIX + "error";
    }

    /**
     * 使用ResponseEntity返回，可以方便的定义HttpHeaders和HttpStatus
     *
     * @param request
     * @param fileName
     * @param model
     * @return
     */
    @RequestMapping(value = "/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request,
                                           @RequestParam("filename") String fileName,
                                           Model model) {
        // 下载文件路径
        String path = request.getServletContext().getRealPath(
                "/images/");
        File file = new File(path + File.separator + fileName);
        HttpHeaders headers = new HttpHeaders();
        // 下载显示的文件名，解决中文名称乱码问题
        try {
            String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            // 通知浏览器以attachment（下载方式）打开图片
            headers.setContentDispositionFormData("attachment", downloadFileName);
            // application/octet-stream ： 二进制流数据（最常见的文件下载）。
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 201 HttpStatus.CREATED
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
