package com.nhb.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nhb.domain.company.Building;
import com.nhb.domain.company.Company;
import com.nhb.domain.company.Level;
import com.nhb.domain.reservation.Reservation;

public interface PlaccDaoMapper {
	public Company findOneCompany(Long id);
	public List<Company> findAllCompany();
	public Building findOneBuilding(Long id);
	public Level findOneLevel(@Param("levelId") Long id,@Param("userId") Long userid, @Param("date") Timestamp date);
	
	public List<Reservation> listReservationsForLevel(@Param("levelId") Long level,@Param("userId") Long userid, @Param("date") Timestamp date);
	public List<Reservation> listReservationsForSeat(@Param("seatId") Long seat,@Param("userId") Long userId,@Param("date") Timestamp date);
		
	public List<Reservation> listReservationsForUser(@Param("userId") Long userid, @Param("date") Timestamp date);
	
	public void prepareReservation(Reservation reservation);
	public void changeReservation(Reservation reservation);
	
	public void finishReservation(Reservation reservation);
	public Reservation findOneReservation(@Param("reservId") Long id);
	public Reservation findOneReservationByUserAndDate(@Param("userId")Long user,@Param("date") Timestamp date);
	public void deleteReservation(Long id);
	
}
