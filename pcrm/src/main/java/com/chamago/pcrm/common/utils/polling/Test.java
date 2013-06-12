package com.chamago.pcrm.common.utils.polling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class Test
 * @author ben
 */
@WebServlet("/Test")
public class Test extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8095181906918852254L;

	Logger log = Logger.getLogger(Test.class);

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File logFile = new File("/adminserver.log");
		if(!logFile.exists())
			logFile.createNewFile();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(logFile));
			while (reader.ready()) {
			//	log.info(reader.readLine());
				String line = reader.readLine();
				new WebWriteAppender().write(line.toCharArray(),0,line.length());
				try {
					Thread.sleep((long) (Math.random() * 100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			reader.close();
		}

	}
	/*public static final Queue<AsyncContext> ASYNC_CONTEXT_QUEUE = new ConcurrentLinkedQueue<AsyncContext>();
	private void write(char[] cbuf, int off, int len) throws IOException{
		Writer writer = new AsyncContextQueueWriter(ASYNC_CONTEXT_QUEUE);
		writer.write(cbuf, off, len);
		
	}*/
}
