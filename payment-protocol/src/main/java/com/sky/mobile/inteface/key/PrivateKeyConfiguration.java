/**
 * 
 * @Date:Nov 11, 2014 - 2:30:05 PM
 * @Author: sparrow
 * @Project :moible-protocol 
 * 
 *
 */
package com.sky.mobile.inteface.key;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.hibernate.validator.cfg.defs.PatternDef;

import com.sky.game.context.GameContextGlobals;
import com.sky.game.context.configuration.GameContxtConfigurationLoader;
import com.sky.mobile.protocol.util.DES;

/**
 * @author sparrow
 * 
 */
public class PrivateKeyConfiguration {

	Map<String, PrivateKey> keys;

	/**
	 * 
	 */
	public PrivateKeyConfiguration() {
		// TODO Auto-generated constructor stub
		transcodeMap = new ConcurrentHashMap<String, PrivateKey>();
	
	}

	public Map<String, PrivateKey> getKeys() {
		return keys;
	}

	public void setKeys(Map<String, PrivateKey> keys) {
		this.keys = keys;
	}

	private ConcurrentHashMap<String, PrivateKey> transcodeMap;

	public synchronized String findDesKey(long transcode) {
		String desKey = null;

		if (!transcodeMap.contains(String.valueOf(transcode))) {
			// loading the transcode and private key in the databases.

			Iterator<PrivateKey> patterns = this.keys.values().iterator();
			while (patterns.hasNext()) {
				PrivateKey k = patterns.next();
				if (k.valid(String.valueOf(transcode))) {
					this.transcodeMap.put(String.valueOf(transcode), k);
					break;
				}

			}
		}

		desKey = transcodeMap.get(String.valueOf(transcode)).getDesKey();

		return desKey;
	}

	private static void generate() {
		PrivateKeyConfiguration configuration = new PrivateKeyConfiguration();
		Map<String, PrivateKey> keys = new HashMap<String, PrivateKey>();
		DES des = new DES();
		configuration.setKeys(keys);
		PrivateKey privateKey = new PrivateKey();
		privateKey.setDesKey(des.genrateKey("012345670123456701234567"));
		privateKey.setPattern("1000[0-9]");
		keys.put(privateKey.getPattern(), privateKey);

		String json = GameContextGlobals.getJsonConvertor().format(
				configuration);

		System.out.println(json);
	}

	private static final String URI = "/META-INF/privatekeys.conf";
	
	
	static {
		URL url = PrivateKeyConfiguration.class.getResource(URI);
		configuration= GameContxtConfigurationLoader
				.loadConfiguration(url, PrivateKeyConfiguration.class);
		
	}

	static PrivateKeyConfiguration configuration ;
	
	
	public static PrivateKeyConfiguration getInstance() {
		return configuration;
	}

	@Override
	public String toString() {
		return "PrivateKeyConfiguration [keys=" + keys + "]";
	}

	public static void main(String args[]) {

		PrivateKeyConfiguration configuration = PrivateKeyConfiguration
				.getInstance();

		String desKey = configuration.findDesKey(2000);
		System.out.println(desKey);
		System.out.println(configuration.toString());
	}

}
