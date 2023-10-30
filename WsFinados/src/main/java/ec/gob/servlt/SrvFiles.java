package ec.gob.servlt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;

import ec.gob.dao.TmpFileDAO;
import ec.gob.object.ServerResponse;
import ec.gob.object.TmpFile;
import ec.gob.object.TmpFilePK;
import ec.gob.persist.JPAUtil;

/**
 * Servlet implementation class SrvFiles
 */
@WebServlet("/SrvFiles")
@MultipartConfig(
		fileSizeThreshold = 1024*1024*10, // 10 MB	podemos especificar el umbral de tamaño después del cual el archivo se escribirá en el disco
		maxFileSize = 1024 * 1024 * 10,      // 10 MB	Tamaño máximo permitido para cargar un archivo
		maxRequestSize = 1024 * 1024 * 100   // 100 MB valor para formulario en total
		)
public class SrvFiles extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SrvFiles() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	//	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		/* Receive file uploaded to the Servlet from the HTML5 form */
		String ID_SESSION = request.getSession().getId();
		
		ServerResponse tmpResponse = new ServerResponse();
		tmpResponse.setSuccess(false);
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
		em.getTransaction().begin();
		if(isMultipart){
			try {
				TmpFileDAO objImgDao = new TmpFileDAO();				
//				int regElimTmp = objImgDao.delete(ID_SESSION);				
				Query query = em.createNativeQuery("Delete from gadmapps.TMP_FILE where ID_SESSION=?");
			    query.setParameter(1, ID_SESSION);
			    query.executeUpdate();
			    
				
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
						String fieldName = item.getFieldName();
						String fieldValue = item.getString();
						System.out.println("********* ITEM FORMULARIO : "+fieldName);
						// ... (do your job here)
					} else {
						// Process form file field (input type="file").
						String fieldName = item.getFieldName();	//debe llegar req_1
						System.out.println("********* NAME: "+fieldName);
						String fileName = FilenameUtils.getName(item.getName());
						System.out.println("********* NAME file: "+fileName);
						String mimeType = item.getContentType().split(";")[0];
						System.out.println("TIPO: "+mimeType);
						InputStream fileContent = item.getInputStream();
						byte[] bytes = IOUtils.toByteArray(fileContent);


						//		        String mimeType = item.getContentType().split(";")[0];
						//		        System.out.println(mimeType);
						//INSERT EN LA TABLA SP_REQUISINSCRIP_TEMP
						TmpFile objImg = new TmpFile();
						TmpFilePK objPk = new TmpFilePK();
						objPk.setIdSession(ID_SESSION);		        
						objPk.setIdRequisito(Long.valueOf(fieldName.split("_")[1]));


						objImg.setId(objPk);
						objImg.setArchivo(bytes);
						objImg.setMymetype(mimeType);
						objImg.setNombre(fileName);
						objImg.setFecha(new Date());

						
//						if(regElimTmp>=0) {
//						objImgDao.save(objImg);						
						em.persist(objImg);
						
//						}else {
//							
//						}
						/*//para poder enviar al file server
		        LocalDateTime lv_now   = LocalDateTime.now();  
		        Par_directorioDAO directorioDAO =  new Par_directorioDAO();
		        String lv_ruta = directorioDAO.find("DIR_FINADOS").getParUrl() + lv_now.getYear() + "/";
		        String path = "smb:"+lv_ruta+"ejemplo.pdf";


		        String v_userSMB = directorioDAO.find("USR_DIR_FINA").getParUrl();
		        String v_passSMB = directorioDAO.find("PAS_DIR_FINA").getParUrl();
		        v_userSMB = new String(Base64.getDecoder().decode(v_userSMB));
				v_passSMB = new String(Base64.getDecoder().decode(v_passSMB));
		        Configuration config = new PropertyConfiguration(new Properties());
		        CIFSContext context = new BaseContext(config);
				context = context.withCredentials(new NtlmPasswordAuthentication(null, 
						directorioDAO.find("DIR_FINADOS").getParUrl(), 
						v_userSMB, 
						v_passSMB));


				SmbFile sFile = new SmbFile(path, context);
				SmbFileOutputStream sfos = new SmbFileOutputStream(sFile);
				sfos.write(bytes);
				sfos.close();
				fileContent.close();*/
						// ... (do your job here)
					}
				}
				em.getTransaction().commit();
				em.close();
				tmpResponse.setSuccess(true);
				tmpResponse.setMsg("Registro Correcto");
				HashMap<String, Object> hash = new HashMap<String, Object>();
				hash.put("SESSION_ID", ID_SESSION);
				//		    String jSonSession = "{\"SESSION_ID\":\""+ID_SESSION+"\"}";
				tmpResponse.setObjData(new Gson().toJson(hash));
			} catch (FileUploadException e) {
				em.getTransaction().rollback();
				em.close();
				e.printStackTrace();					
				tmpResponse.setMsg(e.getMessage());
				tmpResponse.setSuccess(false);
			}catch (Exception e) {
				em.getTransaction().rollback();
				em.close();
				e.printStackTrace();					
				tmpResponse.setMsg(e.getMessage());
				tmpResponse.setSuccess(false);
			}
		}
		//		response.setContentType("text/html");
		response.setContentType("application/json");       
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new Gson().toJson(tmpResponse));




		//		response.getWriter().append("EJEMPLO: Served at: ").append(request.getContextPath());		
		//        response.getWriter().println("The file uploaded sucessfully.");
	}
}
