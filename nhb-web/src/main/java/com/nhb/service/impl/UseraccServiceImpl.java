package com.nhb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nhb.domain.user.Session;
import com.nhb.domain.user.Useracc;
import com.nhb.web.controller.LoginController;
import com.nhb.web.controller.RequestWrapper;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.resource.list.ResourceList;
@Component("useraccServiceImpl")
public class UseraccServiceImpl implements ResourceRepositoryV2<Useracc, Long>{
	@Autowired
	private UseraccDaoMapper userMapper;
	
	public UseraccServiceImpl() {
	}

	@Override
	public Useracc findOne(Long id, QuerySpec requestParams) {
		Session session=RequestWrapper.getSession();
		if(session.getUseracc()!=null && session.getUseracc().getId().equals(id)){
			return userMapper.findOne(id);
		}
		return null;
	}

	@Override
	public ResourceList<Useracc> findAll(QuerySpec requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResourceList<Useracc> findAll(Iterable<Long> ids,
			QuerySpec requestParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Useracc> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<Useracc> getResourceClass() {
		// TODO Auto-generated method stub
		return Useracc.class;
	}

	@Override
	public <S extends Useracc> S create(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
