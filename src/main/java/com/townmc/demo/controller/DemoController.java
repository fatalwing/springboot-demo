package com.townmc.demo.controller;

import com.townmc.demo.domain.po.Demo;
import com.townmc.demo.service.DemoService;
import com.townmc.demo.service.impl.DemoServiceImpl;
import com.townmc.demo.utils.ApiResponse;
import com.townmc.demo.utils.annotations.RetryOnOptimisticLockingFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
@RequestMapping("/_demo_")
public class DemoController {

    @Autowired private DemoService demoService;

    /**
     * 访问页面
     * @return
     */
    @RequestMapping("/index.htm")
    public String indexPage() {
        return "/_demo_/index";
    }

    @RequestMapping("/socket.htm")
    public String socketPage() {
        return "/_demo_/socket";
    }

    @RequestMapping("/add.json")
    @ResponseBody
    public ApiResponse add(@RequestParam(name="id", required=true) String id,
                           @RequestParam(name="name", required=false) String name) {

        Demo re = demoService.add(id, name, Demo.DemoStatus.normal);
        return ApiResponse.success(re);
    }

    @RequestMapping("/update.json")
    @ResponseBody
    @RetryOnOptimisticLockingFailure
    public ApiResponse update(String id, String name) {
        demoService.update(id, name, Demo.DemoStatus.normal);
        return ApiResponse.success("");
    }

    /**
     * 通过post的请求体传参
     * @param requestBody
     * @return
     */
    @PostMapping("/body_update.json")
    @ResponseBody
    public ApiResponse bbUpdate(@RequestBody String requestBody) {
        demoService.update("001", "abc", Demo.DemoStatus.normal);
        return ApiResponse.success("");
    }

    /**
     * 通过rest风格path传参
     * @param id
     * @return
     */
    @RequestMapping("/{id}/get.json")
    @ResponseBody
    public ApiResponse get(@PathVariable("id") String id) {
        Demo re = demoService.findOne(id);
        return ApiResponse.success(re);
    }

    /**
     * 输出流
     * @param request
     * @param response
     */
    @RequestMapping("/pic.do")
    public void juniuoPic2(HttpServletRequest request, HttpServletResponse response) {
        InputStream inStream = null;
        ByteArrayOutputStream outStream = null;
        OutputStream o = null;
        try {
            inStream = new FileInputStream("");

            outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len = 0;
            while( (len=inStream.read(buffer)) != -1 ){
                outStream.write(buffer, 0, len);
            }
            byte data[] = outStream.toByteArray();

            response.setContentType("image/jpg"); //设置返回的文件类型
            o = response.getOutputStream();
            o.write(data);
            o.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != inStream) inStream.close();
                if(null != outStream) outStream.close();
                if(null != o) o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
