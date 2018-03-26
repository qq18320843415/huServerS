package hu.com.utils;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileOperateUtil {
    private static final String REALNAME = "realName";
    private static final String STORENAME = "storeName";
    private static final String SIZE = "size";
    private static final String SUFFIX = "suffix";
    private static final String CONTENTTYPE = "contentType";
    private static final String CREATETIME = "createTime";
    private static final String UPLOADDIR = "uploadDir/";

    /**
     * 将上传的文件进行重命名
     *
     * @param name
     * @return
     * @author zhang_cq
     */


    private static String rename(String name) {

        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        Long random = (long) (Math.random() * now);
        String fileName = now + "" + random;
        if (name.indexOf(".") != -1) {
            fileName += name.substring(name.lastIndexOf("."));
        }
        return fileName;
    }

    /**
     * 压缩后的文件名
     *
     * @param name
     * @return
     * @author zhang_cq
     */


    private static String zipName(String name) {
        String prefix = "";
        if (name.indexOf(".") != -1) {
            prefix = name.substring(0, name.lastIndexOf("."));
        } else {
            prefix = name;
        }
        return prefix + ".zip";
    }

    /**
     * 上传文件
     *
     * @param request
     * @param params
     * @param values
     * @return
     * @throws Exception
     * @author zhang_cq
     */

    public static List<Map<String, Object>> upload(HttpServletRequest request, String[] params, Map<String, Object[]> values) throws Exception {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
        // 获得上传的文件
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();
        // 文件上传后存放的地址
        String uploadDir = request.getSession().getServletContext().getRealPath("/") + FileOperateUtil.UPLOADDIR;
        System.out.println("文件路径  " + uploadDir);
        File file = new File(uploadDir);

        if (!file.exists()) {
            file.mkdir();
        }
        // 文件名称
        String fileName = null;
        int i = 0;
        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

            Map.Entry<String, MultipartFile> entry = it.next();
            MultipartFile mFile = entry.getValue();
            // 上传的文件名
            fileName = mFile.getOriginalFilename();
            // 将文件名重新命名
            String storeName = rename(fileName);
            // 上传文件的路径+名称
            String noZipName = uploadDir + storeName;
            // 压缩后的文件名
            String zipName = zipName(noZipName);

            // 上传成为压缩文件
            ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
            outputStream.putNextEntry(new ZipEntry(fileName));
            // outputStream.setEncoding("GBK");

            FileCopyUtils.copy(mFile.getInputStream(), outputStream);

            Map<String, Object> map = new HashMap<String, Object>();
            // 固定参数值对
            map.put(FileOperateUtil.REALNAME, zipName(fileName)); // 图片元名
            map.put(FileOperateUtil.STORENAME, zipName(storeName)); // 压缩后的名称
            map.put(FileOperateUtil.SIZE, new File(zipName).length()); // 图片大小
            map.put(FileOperateUtil.SUFFIX, "zip"); //后缀
            map.put(FileOperateUtil.CONTENTTYPE, "application/octet-stream"); // 格式
            map.put(FileOperateUtil.CREATETIME, new Date()); // 创建日期
            map.put("path", uploadDir);
            // 自定义参数值对
            for (String param : params) {
                map.put(param, values.get(param)[i]);
            }

            result.add(map);
        }
        return result;
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     * @param storeName
     * @param contentType
     * @param realName
     * @throws Exception
     * @author zhang_cq
     */


    public static void download(HttpServletRequest request, HttpServletResponse response, String storeName,
                                String contentType, String realName) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        // 下载源路径
        String ctxPath = request.getSession().getServletContext().getRealPath("/") + FileOperateUtil.UPLOADDIR;
        String downLoadPath = ctxPath + storeName;

        long fileLength = new File(downLoadPath).length();

        response.setContentType(contentType);
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String(realName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
    }
}
