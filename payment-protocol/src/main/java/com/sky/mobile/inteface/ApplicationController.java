package com.sky.mobile.inteface;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.util.GameUtil;
import com.sky.mobile.protocol.HandlerException;
import com.sky.mobile.protocol.HandlerManager;
import com.sky.mobile.protocol.IHandler;
import com.sky.mobile.protocol.ProtocolRequestEntry;
import com.sky.mobile.protocol.ProtocolResponseEntry;





public class ApplicationController {

	
	private static final Log logger=LogFactory.getLog(ApplicationController.class);
	
	

	@RequestMapping( method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<String> api(@RequestBody String json) {
		
		
		
		logger.info("\ncurl -i -X POST -H \"Content-Type: application/json\" -H \"Accept: application/json\" -d\'"+json+"\' http://127.0.0.1:8080/payment/api");
		
		ProtocolResponseEntry resp=GameUtil.obtain(ProtocolResponseEntry.class);//new ProtocolRequestEntry();
		long begin=System.currentTimeMillis();
		try {
			
			ProtocolRequestEntry request=GameContextGlobals.getJsonConvertor().convert(json,ProtocolRequestEntry.class);
			
			// UserRequest request=UserRequest.fromJsontoUserRequest(json);
			IHandler handler=HandlerManager.getInstance().getHandler(request.getCode());
			
			resp.setCode(request.getCode());
			
			
			
			// should valid the token first.
			
			
			
			if(handler!=null){
				try {
					resp.setStatus(Integer.valueOf(IHandler.STATUS_OK));
					resp.setTimestamp(request.getTimestamp());
					handler.handle(request,resp);
					
					
				} catch (HandlerException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
					resp.setStatus(Integer.valueOf(e.getStatus()));
					resp.setContent(e.getMessage());
					
				}
			}else{
				resp.setStatus(Integer.valueOf(IHandler.STATUS_PROTOCOL_HANDLE_EMPTY));
				resp.setContent("handler not implment yet!");
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(resp.getCode()==null){
				resp.setCode(Integer.valueOf(IHandler.STATUS_NO_TRANSCODE));
			}
			resp.setStatus(Integer.valueOf(IHandler.STATUS_PROTOCOL_ERROR));
			resp.setContent(e.getMessage());
		}
		
		resp.setElapsed(Integer.valueOf((int)(System.currentTimeMillis()-begin)));
	
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json;charset=UTF-8");
		
		json=GameContextGlobals.getJsonConvertor().format(resp);
		return new ResponseEntity<String>(json, headers,
				HttpStatus.CREATED);
	}

}
