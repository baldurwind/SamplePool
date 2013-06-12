package com.chamago.pcrm.common.utils.polling;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.AsyncContext;

public class AsyncContextQueueWriter extends Writer {
	private Queue<AsyncContext> queue;

	public static final BlockingQueue<String> MESSAGE_QUEUE = new LinkedBlockingQueue<String>();
	private void sendMessage(char[] cbuf, int off, int len) throws IOException {
		try {
			MESSAGE_QUEUE.put(new String(cbuf, off, len));
		} catch (Exception ex) {
			IOException t = new IOException();
			t.initCause(ex);
			throw t;
		}
	}

	private Runnable notifierRunnable = new Runnable() {
		public void run() {
			boolean done = false;
			while (!done) {
				String message = null;
				try {
					message = MESSAGE_QUEUE.take();
					for (AsyncContext ac : queue) {   
						try {
							PrintWriter acWriter = ac.getResponse().getWriter();
							acWriter.println(htmlEscape(message));
							acWriter.flush();
						} catch (IOException ex) {
							System.out.println(ex);
							queue.remove(ac);
						}
					}
				} catch (InterruptedException iex) {
					done = true;
					System.out.println(iex);
				}
			}
		}
	};

	/**
	 * @param message
	 * @return
	 */
	private String htmlEscape(String message) {
		return "<script type='text/javascript'>\nwindow.parent.update(\""
				+ message.replaceAll("\n", "").replaceAll("\r", "") + "\");</script>\n";
	}

	private static final Writer DEFAULT_WRITER = new OutputStreamWriter(System.out);

	public AsyncContextQueueWriter(Queue<AsyncContext> queue) {
		this.queue = queue;
		Thread notifierThread = new Thread(notifierRunnable);
		notifierThread.start();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		//DEFAULT_WRITER.write(cbuf, off, len);
		sendMessage(cbuf, off, len);
	}

	@Override
	public void flush() throws IOException {
		DEFAULT_WRITER.flush();
	}

	@Override
	public void close() throws IOException {
		DEFAULT_WRITER.close();
		for (AsyncContext ac : queue) {
			ac.getResponse().getWriter().close();
		}
	}

}
