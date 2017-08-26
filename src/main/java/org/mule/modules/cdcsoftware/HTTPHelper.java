/**
 * Copyright (C) 2017 CDC Software LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mule.modules.cdcsoftware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;



public class HTTPHelper {
	
	public static final String GZIP = "gzip";
	public static final String UTF_8 = "UTF-8";	
	public static final String TEXT_XML_UTF_8 ="text/xml;charset=UTF-8";
	public static final String GZIP_DEFLATE ="gzip,deflate";	

	public static String buildPostURL(String domain,String serverId,String clientId) {
		StringBuilder sb = new StringBuilder();
		sb.append(domain);
		sb.append("/api/message?destination=topic://");
		sb.append(serverId);
		sb.append(".output");
		sb.append("&clientId=mule_");
		sb.append(clientId);
		return sb.toString();
	}

	public static String buildGetURL(String domain,String serverId,String clientId,int timeout) {
	
		StringBuilder sb = new StringBuilder();
		sb.append(domain);
		sb.append("/api/message?destination=topic://");
		sb.append(serverId);
		sb.append(".global.input");
		sb.append("&readTimeout=");
		sb.append(Integer.toString(timeout));
		sb.append("&clientId=mule_");
		sb.append(clientId);

		return sb.toString();
	}
	
	public static String doHTTP(String method,String urlString, int timeout,String body) throws IOException {

		String result = null;

		URL url = new URL(urlString);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setConnectTimeout(20000);
		conn.setReadTimeout(timeout * 1000);

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setRequestProperty("Content-Type", TEXT_XML_UTF_8);
		conn.setRequestProperty("Accept-Encoding",GZIP_DEFLATE);

		String userpass = "username" + ":" + "password";
		String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
		conn.setRequestProperty ("Authorization", basicAuth);		
		
		conn.setRequestMethod(method);
		
		if("POST".equals(method)){
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", Integer.toString(body.getBytes(UTF_8).length));  			
			OutputStream post = conn.getOutputStream();
			post.write(body.getBytes("UTF-8"));
			post.flush();
			post.close();
		}else{
			conn.setDoOutput(false);
		}
		

		int responseCode = conn.getResponseCode();

		if (responseCode == 200) {

			String encoding = conn.getContentEncoding();

			InputStream resultingInputStream;

			if (encoding != null && encoding.equalsIgnoreCase(GZIP)) {

				resultingInputStream = new GZIPInputStream(conn.getInputStream());

			} else {
				resultingInputStream = conn.getInputStream();
			}

			result = getResponseAsString(resultingInputStream);

		}

		return result;
	}
	
	private static String getResponseAsString(InputStream is) throws IOException {
		StringBuilder inputStringBuilder = new StringBuilder();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,UTF_8));
		String line = bufferedReader.readLine();
		while (line != null) {
			inputStringBuilder.append(line);
			line = bufferedReader.readLine();
		}
		return inputStringBuilder.toString();
	}
	
	

}
