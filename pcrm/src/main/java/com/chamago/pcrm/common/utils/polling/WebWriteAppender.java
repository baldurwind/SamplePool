package com.chamago.pcrm.common.utils.polling;

import java.io.IOException;
import java.io.Writer;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.AsyncContext;

import org.apache.log4j.WriterAppender;

public class WebWriteAppender extends WriterAppender {
	public static final Queue<AsyncContext> ASYNC_CONTEXT_QUEUE = new ConcurrentLinkedQueue<AsyncContext>();

	private Writer writer = new AsyncContextQueueWriter(ASYNC_CONTEXT_QUEUE);

	public WebWriteAppender() {
		setWriter(writer);
	}

	public  void write(char[] cbuf, int off, int len) throws IOException{
		Writer writer = new AsyncContextQueueWriter(ASYNC_CONTEXT_QUEUE);
		writer.write(cbuf, off, len);
	}

}
