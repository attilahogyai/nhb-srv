package com.nhb.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nhb.domain.company.Level;
import com.nhb.domain.user.Session;
import com.nhb.web.controller.RequestWrapper;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.ResourceRepository;
@Component(value="levelServiceImpl")
public class LevelServiceImpl extends AbstractService implements ResourceRepository<Level, Long>{
	@Autowired
	private PlaccDaoMapper placcDaoMapper;
	
	@Override
	public void delete(Long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<Level> findAll(QueryParams query) {
		List<Level> l=new ArrayList<Level>();
		Session session=RequestWrapper.getSession();
		String id=getParam(query,"id");
		if(id!=null){
			String date=getParam(query,"date");

			if(id==null || date==null) return null;
			
			Long userid=0L;
			if(session.getUseracc()!=null){
				userid=(Long)session.getUseracc().getId();
			}
			Level level=placcDaoMapper.findOneLevel(Long.parseLong(id), userid,  parseTimestamp(date));
			if(level!=null){
				l.add(level);
			}
				
			return l;
		}
		return l;
	}

	@Override
	public Iterable<Level> findAll(Iterable<Long> arg0, QueryParams query) {
		return null;
	}

	@Override
	public Level findOne(Long id, QueryParams query) {
		Session session=RequestWrapper.getSession();
		String date=getParam(query,"date");
		Long userid=0L;
		if(session.getUseracc()!=null){
			userid=(Long)session.getUseracc().getId();
		}
		
		return placcDaoMapper.findOneLevel(id, userid,  parseTimestamp(date));
	}

	@Override
	public <S extends Level> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
