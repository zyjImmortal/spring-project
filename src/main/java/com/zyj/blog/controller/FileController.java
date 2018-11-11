package com.zyj.blog.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import com.zyj.blog.domain.File;
import com.zyj.blog.service.FileService;
import com.zyj.blog.util.MD5Util;

import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * FileController
 */

@CrossOrigin(origins = "*", maxAge = 3600) // 允许所有域名访问
@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("files", fileService.listFilesByPage(0, 20));
        return "index";
    }

    @GetMapping("files/{pageIndex}/{pageSize}")
    @ResponseBody // 表示该方法的返回的结果直接写入 HTTP 响应正文（ResponseBody）中，一般在异步获取数据
                  // 使用
    public List<File> listFilesByPage(@PathVariable int pageIndex, @PathVariable int pageSize) {
        return fileService.listFilesByPage(pageIndex, pageSize);
    }

    @GetMapping("files/{id}")
    @ResponseBody
    public ResponseEntity<Object> serverFile(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id); // Optional这是一个可以为null的容器对象。如果值存在则isPresent()方法会返回true，调用get()方法会返回该对象
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("connection", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file was no found");
        }
    }

    /**
     * 
     * @param id
     * @return
     */
    @GetMapping("view/{id}")
    @ResponseBody
    public ResponseEntity<Object> serverFileOnline(@PathVariable String id) {
        Optional<File> file = fileService.getFileById(id);
        if (file.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + file.get().getName() + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, file.get().getContentType())
                    .header(HttpHeaders.CONTENT_LENGTH, file.get().getSize() + "").header("Connetion", "close")
                    .body(file.get().getContent().getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file was no found");
        }
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            fileService.saveFile(f);
        } catch (IOException | NoSuchAlgorithmException e) {
            //TODO: handle exception
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("meessage", "Your" + file.getOriginalFilename() + "is wrong!");
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("meessage", "Your successfully upload" + file.getOriginalFilename() + "!");
        return "redirect:/";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        File returnFile = null;
        try {
            File f = new File(file.getOriginalFilename(), file.getContentType(), file.getSize(), new Binary(file.getBytes()));
            f.setMd5(MD5Util.getMD5(file.getInputStream()));
            returnFile = fileService.saveFile(f);
            String path = "//" + serverAddress + ":" + serverPort + "/view/" + returnFile.getId();
            return ResponseEntity.status(HttpStatus.OK).body(path);
        } catch (IOException | NoSuchAlgorithmException e) {
            //TODO: handle exception
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String id){
        try {
            Optional<File>  file = fileService.getFileById(id);
            if(file.isPresent()){
                fileService.removeFile(file.get());
                return ResponseEntity.status(HttpStatus.OK).body("Delete Success!");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File was not found! id=" + id);
            }
        } catch (Exception e) {
            //TODO: handle exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}