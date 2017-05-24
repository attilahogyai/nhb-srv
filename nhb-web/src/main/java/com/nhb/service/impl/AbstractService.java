package com.nhb.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nhb.domain.AbstractEntity;
import com.nhb.domain.PagingFilter;
import com.nhb.domain.user.Session;
import com.nhb.util.Constants;
import com.nhb.web.controller.RequestWrapper;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryspec.QuerySpec;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class AbstractService {
	
	final Logger logger = LoggerFactory.getLogger(AbstractService.class);
	
	BeanUtilsBean beanUtils=BeanUtilsBean.getInstance();
	protected void initEntity(AbstractEntity entity){
		if(entity instanceof AbstractEntity){ // set session object for entity
			Session session=RequestWrapper.getSession();
			if(session!=null){
					try {
						beanUtils.setProperty(entity, "session", session);
					} catch (IllegalAccessException | InvocationTargetException e) {
						logger.error("error initEntity", e);
					}
			}
		}
	}
	public String getParam(QueryParams query, String name) {
		if(query.getFilters().getParams().get(name)==null) return null;
		Set<String> r=query.getFilters().getParams().get(name).getParams().get("");
		if(r==null || r.size()==0) return null;
		String [] list = r.toArray(new String[r.size()]);
		return list[0];
	}
	public Timestamp parseTimestamp(String dateS){
		if(dateS==null) return null;
		final DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date dateO=null;
		try {
			dateO = format.parse(dateS);
		} catch (ParseException e) {
			logger.error("parse date error",e);
		}
		if(dateO==null) return null;
		return new Timestamp(dateO.getTime());
	}
	

	/*
	protected static <T extends PagingFilter> T buildDefaultFilter(QuerySpec requestParams, T filter) {
		if(requestParams!=null){
			Map<String,FilterParams> params=requestParams.getFilters().getParams();
			filter.setOffset(0);
			filter.setLimit(1);
			if(!requestParams.getPagination().isEmpty()){
				filter.setOffset(requestParams.getPagination().get(RestrictedPaginationKeys.offset)-1);
				filter.setLimit(requestParams.getPagination().get(RestrictedPaginationKeys.limit));
				filter.setOffset(filter.getOffset()*filter.getLimit());
				filter.setPerPage(filter.getLimit());
			}
			for (String filterS : params.keySet()) {
				if(filterS.equals("popular")){
					filter.setPopular(true);
				}
				if(filterS.equals("query")){
					Set query=params.get("query").getParams().get("");
					String [] queryArray=(String [])query.toArray(new String[query.size()]);
					filter.setQuery(queryArray[0]);
				}
				if(filterS.equals("language")){
					Set language=params.get("language").getParams().get("");
					String [] languageArray=(String [])language.toArray(new String[language.size()]);
					filter.setLanguage(languageArray[0]);
				}
				
			}
			if(filter.getLanguage()==null){
				filter.setLanguage(AbstractController.searchLanguageByCode(RequestWrapper.getSession().getLanguage()));
			}
		}
		return filter;
	}	*/
	
	protected boolean checkCaptcha(HttpServletRequest request){
		if(Constants.UNITTESTMODE) return true;
		
		HttpPost post=new HttpPost("https://www.google.com/recaptcha/api/siteverify");
		List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(new BasicNameValuePair("secret", "6LeQfQ0TAAAAAO6Rn_5qhTPfq3hqHD2ougeF0eYN"));
		String solution=request.getParameter("s");
		if(solution==null) return false;
		nvps.add(new BasicNameValuePair("response", solution));
		nvps.add(new BasicNameValuePair("remoteip", RequestWrapper.getRemoteIP(request)));
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps));
			CloseableHttpResponse response=HttpClientBuilder.create().build().execute(post);
			JSONParser jp=new JSONParser(); 
			JSONObject object=(JSONObject)jp.parse(response.getEntity().getContent());
			logger.debug("captcha check result:"+object);
			Boolean result=(Boolean)object.get("success");
			return result;
		} catch (Exception e) {
			logger.error("parser error",e);
		}
		return false;
	}

}
