

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ClassModel;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;


import task.dbTask;

@WebServlet("/UploadFile")
public class UploadServlet extends HttpServlet {

//    private static final long serialVersionUID = 1L;
    private File fileUploadPath;

    @Override
    public void init(ServletConfig config) {
    	try {
			super.init(config);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        fileUploadPath = new File(this.getServletContext().getRealPath("/upload"));
    }
        
    /**
        * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
        if (request.getParameter("getfile") != null 
                && !request.getParameter("getfile").isEmpty()) {
            File file = new File(fileUploadPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty() && request.getParameter("clid") != null) {
        	String clid = request.getParameter("clid");
    		ClassModel model = dbTask.getInstance().GetClassByClassid(clid);
    		String filejson = model.getFile();
		   
    		try {
		    	JSONArray array = new JSONArray(filejson);
		    	
		    	int index =-1;
		    	for(int i =0; i<array.length();i++){
		    		JSONObject object = (JSONObject)array.get(i);
		    		String name= object.get("name").toString();
		    		index = i;
		    	}
		    	if(index!=-1){
		    		array.remove(index);
		    	}
    		    dbTask.getInstance().UpdateFileToClass(array.toString(), clid);
    		    
            	File file = new File(fileUploadPath, request.getParameter("delfile"));
                if (file.exists()) {
                    file.delete(); // TODO:check and report success
                } 
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	

        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty() ) {
            File file = new File(fileUploadPath, request.getParameter("getthumb"));
                if (file.exists()) {
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } // TODO: check and report success
        } else if(request.getParameter("clid") != null && !request.getParameter("clid").isEmpty()){
        		String clid = request.getParameter("clid");
        		ClassModel model = dbTask.getInstance().GetClassByClassid(clid);
        		String filejson = model.getFile();
        	    PrintWriter out = response.getWriter();
        	    out.println(filejson);
        	    out.close();
        }	else {
        
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }
    }
    
    /**
        * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        JSONArray json = new JSONArray();
        
        String clid=null;
        JSONObject jsono =null;
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                        File file = new File(fileUploadPath, item.getName());
                        item.write(file);
                        jsono = new JSONObject();
                        jsono.put("name", item.getName());
                        jsono.put("size", item.getSize());
                        jsono.put("url", "UploadFile?getfile=" + item.getName());
                        jsono.put("thumbnail_url", "UploadFile?getthumb=" + item.getName());
                        jsono.put("delete_url", "UploadFile?clid="+clid+"&delfile=" + item.getName());
                        jsono.put("delete_type", "GET");
                        json.put(jsono);
                }else{
                	//一般的參數
                	String name = item.getFieldName();
                	String value = item.getString("UTF-8");
                	if(name.equals("clid")){
                		clid = value;
                	}
                }
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
                throw new RuntimeException(e);
        } finally {
        	
        	if(clid!=null&&json.length()!=0){
        		ClassModel model = dbTask.getInstance().GetClassByClassid(clid);
        		String filejson = model.getFile();
    		    Gson gson  =  new Gson();
    		    List list = gson.fromJson(filejson, List.class);
    		    try {
    		    	JSONArray array = new JSONArray(filejson);
    		    	array.put(jsono);
        		    dbTask.getInstance().UpdateFileToClass(array.toString(), clid);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		    //String newFileJson = gson.toJson(list);
        	}
        	
        	
            writer.write(json.toString());
            writer.close();
        }

    }

    private String getMimeType(File file) {
        String mimetype = "";
        if (file.exists()) {
//            URLConnection uc = new URL("file://" + file.getAbsolutePath()).openConnection();
//            String mimetype = uc.getContentType();
//            MimetypesFIleTypeMap gives PNG as application/octet-stream, but it seems so does URLConnection
//            have to make dirty workaround
            if (getSuffix(file.getName()).equalsIgnoreCase("png")) {
                mimetype = "image/png";
            } else {
                javax.activation.MimetypesFileTypeMap mtMap = new javax.activation.MimetypesFileTypeMap();
                mimetype  = mtMap.getContentType(file);
            }
        }
        System.out.println("mimetype: " + mimetype);
        return mimetype;
    }



    private String getSuffix(String filename) {
        String suffix = "";
        int pos = filename.lastIndexOf('.');
        if (pos > 0 && pos < filename.length() - 1) {
            suffix = filename.substring(pos + 1);
        }
        System.out.println("suffix: " + suffix);
        return suffix;
    }
}