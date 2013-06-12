/**
 * 
 */
package com.chamago.pcrm.search.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

import com.chamago.pcrm.worktable.connect.service.SysMgtService;

/**
 * 扩展IK词库
 * @author gavin.peng
 * 目前每5分钟执行一次,先读数据库，再读配置文件
 */
@Component("iKKeywordsScanService")
public class IKWordsManager {
	
	private static Logger logger = LoggerFactory.getLogger(IKWordsManager.class);
	//TODO 分钟级别读配置文件
	private static long HOURES = 4*60;
	private static long MINUTS = 1;
	private static long POLLING_INTERVAL_SENCONDS = 60*1000;
	
	private static final String FILE_NAME = "/IKAnalyzer.cfg.xml";
	
	@Autowired
	private SysMgtService sysMgtService;
	/**
	 * IK词库扩展实例
	 */
	private static IKWordsManager singleton = new IKWordsManager();
	
	
	private IKWordsManager(){
		
	}
	

	
	public static IKWordsManager getSingleton()
	{
	    if (singleton == null) {
	      throw new IllegalStateException("词典扩展实例尚未初始化，请先调用initial方法");
	    }
	    return singleton;
	}
	
	@PostConstruct
	public void initial(){
		LoadWordsThread loadWordsThread = new LoadWordsThread();
		loadWordsThread.start();
	}
	
	/**
	 * 加载Acookie的关键词
	 */
	public void loadDBWords(){
		try{
			logger.info("开始刷新Acookie关键词");
			Collection<String> wds = sysMgtService.getKeywordsFromAccokie();
			logger.info("此次Acookie有["+wds.size()+"]个关键词要刷新到分词库");
			Dictionary.loadExtendWords(wds);
			logger.info("此次共刷新Acookie["+wds.size()+"]个关键词要到分词库");
		}catch(Exception e){
			logger.error("加载Acookie关键词失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 加载配置文件的关键词
	 * @throws Exception
	 */
	private void loadConfigWords() throws Exception{
		List<String> extDicList = Configuration.getExtDictionarys();
		Collection<String> extWordCollection = new ArrayList<String>();
		if(extDicList != null&&extDicList.size()>0){
			for(String extDictName : extDicList){
				//读取扩展词典文件
				InputStream is = Dictionary.class.getResourceAsStream(extDictName);
				//如果找不到扩展的字典，则忽略
				if(is == null){
					continue;
				}
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
					String theWord = null;
					do {
						theWord = br.readLine();
						if (theWord != null && !"".equals(theWord.trim())) {
							extWordCollection.add(theWord);
						}
					} while (theWord != null);
					
				}catch (IOException ioe) {
					logger.error("加载扩展词典失败");
					ioe.printStackTrace();
				}finally{
					try {
						if(is != null){
		                    is.close();
		                    is = null;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			logger.info("此次配置文件共有["+extWordCollection.size()+"]个词需要刷新到分词库");
			Dictionary.loadExtendWords(extWordCollection);
			logger.info("此次配置文件词典成功刷新词库");
		}
	}
	
	
	private class LoadWordsThread extends Thread{
		
		
		public LoadWordsThread(){
			
		}
		
		public void run(){
			do{
				try{
					Thread.sleep(POLLING_INTERVAL_SENCONDS*MINUTS);
					//加载Acookie
					loadDBWords();
					//加载配置文件
					loadConfigWords();
					Thread.sleep(POLLING_INTERVAL_SENCONDS*MINUTS*HOURES);
					
				}catch(Exception e){
					logger.error("刷新词库失败,1分钟后继续刷新");
					e.printStackTrace();
				}
			}while(true);
		}
		
	}

}
