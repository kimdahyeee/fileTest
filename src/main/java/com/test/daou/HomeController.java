package com.test.daou;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/main")
	public String SmartEditor2Skin(Locale locale, Model model) {
		return "SmartEditor2Skin";
	}
	
	@RequestMapping(value = "/SmartEditor2")
	public String SmartEditor2(Locale locale, Model model) {
		return "SmartEditor2";
	}
	
	@RequestMapping(value = "/photoUploader")
	public String photoUploader(Locale locale, Model model) {
		return "photo_uploader";
	}
	
	
	@RequestMapping(value = "/file_uploader_html5", method=RequestMethod.POST)
	public void fileUploaderHtml5(HttpServletRequest request, HttpServletResponse response) {
		try {
	         String sFileInfo = "";
	         String filename = request.getHeader("file-name");
	         System.out.println("fdfdfdf : " + request.getHeader("file-name"));
	         String filename_ext = filename.substring(filename.lastIndexOf(".")+1);
	         filename_ext = filename_ext.toLowerCase();
	         //파일 기본경로
	         String dftFilePath = request.getSession().getServletContext().getRealPath("/");
	         //파일 기본경로 _ 상세경로
	         String filePath = dftFilePath + "resources" + File.separator + "photo_uploader" + File.separator + "img" + File.separator;

	         File file = new File(filePath);
	         if(!file.exists()) {
	            file.mkdirs();
	         }
	         
	         String realFileNm = "";
	         SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	         String today= formatter.format(new java.util.Date());
	         realFileNm = today+UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
	         String rlFileNm = filePath + realFileNm;
	         
	         ///////////////// 서버에 파일쓰기 /////////////////
	         InputStream is = request.getInputStream();
	         OutputStream os=new FileOutputStream(rlFileNm);
	         int numRead;
	         byte b[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
	         
	         while((numRead = is.read(b,0,b.length)) != -1){
	            os.write(b,0,numRead);
	         }
	         if(is != null) {
	            is.close();
	         }
	         os.flush();
	         os.close();
	         
	         ///////////////// 서버에 파일쓰기 /////////////////
	         sFileInfo += "&bNewLine=true";
	         // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
	         sFileInfo += "&sFileName="+ filename;
	         sFileInfo += "&sFileURL="+"/resources/photo_uploader/img/"+realFileNm;

	         System.out.println("sfileInfo ::: " + sFileInfo);
	         PrintWriter print = response.getWriter();
	         print.print(sFileInfo);
	         print.flush();
	         print.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}
}
