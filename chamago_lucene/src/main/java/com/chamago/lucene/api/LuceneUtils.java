package com.chamago.lucene.api;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;




public class LuceneUtils {
	
	 private final static Logger logger=Logger.getLogger(LuceneUtils.class);
	 private final static  int RESULT_SIZE=20;
	 private final static Analyzer analyzer=new IKAnalyzer( );
	 public  static String ROOT_PATH;
	 private static	IndexWriterConfig config ;
	 private static Map<String,LucenePojo> lucenePojoMap=new HashMap<String,LucenePojo>();
	
	 
	 public LuceneUtils(String root) {
			try {
				ROOT_PATH=root;
				File f=new File(root);
				if(!f.exists())
				    f.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
			}
	 }
	 
	 /*** 批量创建索引*/		
	public   void createBatch(String directory,List<Document> docs)throws CorruptIndexException,LockObtainFailedException ,IOException{
		IndexWriter writer=	this.initWriter(directory);
		try{
			for(Document doc:docs){
				writer.addDocument(doc);
			}
		}
		finally{
			if(null!=writer){
				writer.close();
			}
		}
	}
	public void create(String directory,Document doc)throws CorruptIndexException,LockObtainFailedException ,IOException{
		IndexWriter writer=	this.initWriter(directory);
		try{
			writer.addDocument(doc);
		}
		finally{
			if(null!=writer){
				writer.close();
			}
		}
	}
	
/* 	
		public  List<Document>   search(String name,String keyword,String fields[]) throws ParseException,  CorruptIndexException ,IOException{
		        QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_35, fields, analyzer);
		        IndexSearcher searcher = this.findIndexSearcher(name);
		        
		        Query query = queryParser.parse(keyword);
		        
		        
		        TopScoreDocCollector collector =TopScoreDocCollector.create(RESULT_SIZE,false);
		        searcher.search(query, collector);
		        TopDocs topDocs =collector.topDocs();
		        List<Document> docList =new ArrayList<Document>();
		        for(ScoreDoc scoreDoc : topDocs.scoreDocs){
		            docList.add(searcher.doc(scoreDoc.doc));
		        }
		        return docList;
		}*/
		public List<Document> search(String directory,Query query)throws ParseException,  CorruptIndexException ,IOException{
			IndexSearcher searcher = this.findIndexSearcher(directory);
			TopDocs topDocs =searcher.search(query, RESULT_SIZE);
			 List<Document> result =new ArrayList<Document>();
			 for(ScoreDoc scoreDoc : topDocs.scoreDocs){
				 result.add(searcher.doc(scoreDoc.doc));
			 }
			return result;
		}
	
	
		/**删除所有：*/		
		public void deleteAll(String directory) throws IOException{
			IndexWriter writer=this.initWriter(directory);
			try{
				writer.deleteAll();			
			}
			finally{
				if(null!=writer)
					writer.close();
			}
		}
		/**删除单个Term：**/			
		public void delete(String directory,Term term) throws IOException{
			IndexWriter writer=this.initWriter(directory);
			try{
				writer.deleteDocuments(term);
			}
			finally{
				if(null!=writer)
					writer.close();
			}
		}
	 
	//根据name返回LucenePojo
	 private   LucenePojo findLucenePojo(String directory){
		 return lucenePojoMap.get(directory);
	 }
	//根据seller返回Directory
	private Directory  findDirectory(String directory){
		return lucenePojoMap.get(directory).getDirectory();
	}
/*		//根据seller返回IndexWriter
	private IndexWriter  findIndexWriter(String name)throws IOException{
		IndexWriter writer = lucenePojoMap.get(name).getWriter();
		if(null==writer)
			writer =initWriter(name);
		return writer;
	}*/
	//根据seller返回IndexSearcher
	private IndexSearcher findIndexSearcher(String directory)throws IOException{
		IndexSearcher searcher =lucenePojoMap.get(directory).getSearcher();
		if(null==searcher){
			searcher= new IndexSearcher(IndexReader.open(findDirectory(directory)));
			lucenePojoMap.get(directory).setSearcher(searcher);
		}
			
		return searcher;
	}

	 
	//初始化Directory
	 public    void initDirectory(String  directory){
			 Directory tmp=null;
				try {
					 String dir=ROOT_PATH+"/"+directory;
					 File file=new  File(dir);
					 if(!file.exists())
						 file.mkdir();
					 tmp = new SimpleFSDirectory(file);
					  LucenePojo pojo=new LucenePojo();
					     pojo.setDirectory(tmp);
					     lucenePojoMap.put(directory, pojo);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("LuceneUtils.Constructor IOException", e);
				}
		}
	 
	//初始化IndexWriter
	private IndexWriter initWriter(String directory)throws CorruptIndexException,LockObtainFailedException ,IOException{
		LucenePojo pojo=this.findLucenePojo(directory);
		
		
		config= new IndexWriterConfig(Version.LUCENE_35,analyzer);
		config.setOpenMode(OpenMode.CREATE);
		IndexWriter writer=new IndexWriter(pojo.getDirectory(),config);
			pojo.setWriter(writer);
		return   pojo.getWriter();
	}
		
	
public void closeWirter(){
	
}
	 
	 
	 
}
