package com.nhb.service.impl;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nhb.domain.reservation.Reservation;
import com.nhb.domain.user.Session;
import com.nhb.web.controller.RequestWrapper;
import com.nhb.web.exception.AuthException;
import com.nhb.web.exception.PermissionDenied;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.ResourceRepository;

@Component(value="reservationServiceImpl")
public class ReservationServiceImpl extends AbstractService implements  ResourceRepository<Reservation, Long>{
	@Autowired
	private PlaccDaoMapper placcDaoMapper;
	
	
	@Override
	public void delete(Long arg0) {
		Session session=RequestWrapper.getSession();
		if(session.isRegistered()){
			Reservation r=placcDaoMapper.findOneReservation(arg0);
			Long id=session.getUseracc().getId();
			if(!id.equals(r.getUseracc().getId())){
				throw new PermissionDenied("BELONGS TO OTHER USER");
			}
			
			placcDaoMapper.deleteReservation(arg0);
		}else{
			throw new AuthException("NOT AUTHORIZED");
		}
			
	}
	

	
	
	@Override
	public Iterable<Reservation> findAll(QueryParams query) {
		String seat=getParam(query,"seat");
		String date=getParam(query,"date");
		String level=getParam(query,"level");		
		if(date==null) return null;
		final DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date dateO=null;
		try {
			dateO = format.parse(date);
		} catch (ParseException e) {
			logger.error("parse date error",e);
		}
		if(dateO==null) return null;
	
		Session session=RequestWrapper.getSession();
		Long userid=0L;
		if(session.getUseracc()!=null){
			userid=(Long)session.getUseracc().getId();
		}
		if(seat==null && level!=null) {
			return placcDaoMapper.listReservationsForLevel(Long.parseLong(level),userid,new Timestamp(dateO.getTime()));			
		}else{
			return placcDaoMapper.listReservationsForSeat(Long.parseLong(seat),userid,new Timestamp(dateO.getTime()));			
		}
	}

	@Override
	public Iterable<Reservation> findAll(Iterable<Long> arg0, QueryParams arg1) {
		
		return null;
	}

	@Override
	public Reservation findOne(Long arg0, QueryParams arg1) {
		return placcDaoMapper.findOneReservation(arg0);
	}

	@Override
	public <S extends Reservation> S save(S arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
