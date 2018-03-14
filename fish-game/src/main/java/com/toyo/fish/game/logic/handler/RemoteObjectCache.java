/**
 * 
 */
package com.toyo.fish.game.logic.handler;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sky.game.context.annotation.introspector.IIdentifiedObject;
import com.sky.game.context.event.LocalServiceException;
import com.sky.game.context.event.WebsocketEvent;
import com.sky.game.context.route.RouterHeader;
import com.sky.game.context.util.G;
import com.toyo.fish.data.wrapper.domain.Login;
//import com.toyo.fish.game.logic.user.remote.service.UserAccountRemote;


/**
 * @author sparrow
 *
 */
public class RemoteObjectCache implements IIdentifiedObject {
	
	
	protected Long id;
	private  static final ConcurrentHashMap<Long,Map<String, RemoteObjectCache>> caches=new ConcurrentHashMap<Long,Map<String, RemoteObjectCache>>();
	
	
	public RemoteObjectCache(Long id) {
		super();
		
		
		if(!caches.containsKey(id)){
			//caches.put(id, this);
			caches.put(id, new LinkedHashMap<String,RemoteObjectCache>());
		}
		Map<String,RemoteObjectCache> map=caches.get(id);
		if(!map.containsKey(this.getClass().getSimpleName())){
			map.put(this.getClass().getSimpleName(), this);
		}
		
		this.id = id;
	}

	
	public static synchronized void unload(Long id){
		if(caches.containsKey(id)){
			Map<String,RemoteObjectCache>  obj=caches.get(id);
			if(obj!=null){
			for(RemoteObjectCache o:obj.values()){
				G.clearAllHandlers(o);
			}
			obj.clear();
			
			caches.remove(id);
			}
			
		}
	}
	
	
	
	
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getObject(Long id,Class<?> clz) throws LocalServiceException{
		Map<String,RemoteObjectCache>  obj=caches.get(id);
		T t =null;
		if(obj!=null){
			t=(T)obj.get(clz.getSimpleName());
		}
		
		if(t==null)
			throw new LocalServiceException(-1, "Object is empty!");
		return t;
	}

	
//	public Login getLogin() throws LocalServiceException{
//		Login login=GlobalUserManager.localLoginService().findLoginByUserId(id);
//		return login;
//	}
	
	
//	public RouterHeader getHeader() throws LocalServiceException{
//		UserAccountRemote remote=getObject(id, UserAccountRemote.class);
//		
//		return remote.getHeader();
//		
//	}
//	
	
	 
}
