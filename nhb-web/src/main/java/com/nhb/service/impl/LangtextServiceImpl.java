package com.nhb.service.impl;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.MetaRepository;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.response.MetaInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nhb.domain.Metadata;
import com.nhb.domain.PagingFilter;
import com.nhb.domain.dictionary.Langtext;
import com.nhb.web.controller.RequestWrapper;

@Component("langtextServiceImpl")
public class LangtextServiceImpl extends AbstractService implements ResourceRepository<Langtext, Long> {
	@Autowired
	private LangtextDaoMapper langtextDaoMapper;

	@Override
	public Langtext findOne(Long id, QueryParams queryParams) {
		return langtextDaoMapper.findOne(id);
	}

	@Override
	public Iterable<Langtext> findAll(QueryParams queryParams) {
		return langtextDaoMapper.findAll();
	}

	@Override
	public Iterable<Langtext> findAll(Iterable<Long> ids, QueryParams queryParams) {
		throw new UnsupportedOperationException();
	}

	@Override
	public <S extends Langtext> S save(S entity) {
		if(entity.getId()==null){
			langtextDaoMapper.insert(entity);
		}else{
			langtextDaoMapper.update(entity);
		}
		return entity;
	}

	@Override
	public void delete(Long id) {
		langtextDaoMapper.delete(id);
	}


}
