package hu.com.controlled;

import hu.com.utils.FileOperateUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileOperateController {

    /**
     * 到上传文件的位置
     *
     * @return
     * @author geloin
     * @date 2012-3-29 下午4:01:31
     */
    @RequestMapping(value = "/to_upload")
    public String toUpload() {
        return "uploadSuccess";
    }

    private static Logger logger = Logger.getLogger("FileOperateController.class");

    /**
     * 上传文件
     *
     * @param request
     * @return
     * @throws Exception
     * @author
     */
    @RequestMapping(value = "/upload")
    public ModelAndView upload(HttpServletRequest request) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        logger.trace("我的信息啊" + request.getHeaderNames());
        System.out.println("我的信息啊" + request.getHeaderNames());

        // 别名
        String[] alaises = ServletRequestUtils.getStringParameters(request, "alais");
        System.out.println("数组长度  " + alaises.length);
        String[] params = new String[]{"alais"};
        System.out.println("参数长度  " + params.length);
        Map<String, Object[]> values = new HashMap<String, Object[]>();
        values.put("alais", alaises);

        List<Map<String, Object>> result = FileOperateUtil.upload(request, params, values);

        map.put("result", result);

        return new ModelAndView("uploadSuccess", map);
    }

    /**
     * 下载
     *
     * @param
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author zhang_cq
     */
    @RequestMapping(value = "download")
    public ModelAndView download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 下载源
        String storeName = "2017011821392011420519411922.zip";
        // 下载之后的文件名
        String realName = "myPhoto.zip";
        String contentType = "application/octet-stream";

        FileOperateUtil.download(request, response, storeName, contentType, realName);

        return null;
    }
}
