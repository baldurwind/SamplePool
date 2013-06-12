package com.chamago.lucene.api;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

public class LucenePojo {
	
	private String name;
	private IndexWriter writer;
	private Directory directory;
	private IndexSearcher searcher;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public IndexWriter getWriter() {
		return writer;
	}
	public void setWriter(IndexWriter writer) {
		this.writer = writer;
	}
	public Directory getDirectory() {
		return directory;
	}
	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	public IndexSearcher getSearcher() {
		return searcher;
	}
	public void setSearcher(IndexSearcher searcher) {
		this.searcher = searcher;
	}
	
}
