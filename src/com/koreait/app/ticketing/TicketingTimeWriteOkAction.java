package com.koreait.app.ticketing;

import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.action.Action;
import com.koreait.action.ActionTo;
import com.koreait.app.ticketing.dao.TicketingDAO;
import com.koreait.app.ticketing.dao.TimeDTO;

public class TicketingTimeWriteOkAction implements Action{
	@Override
	public ActionTo execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String starttime = req.getParameter("starttime");
		String endtime = req.getParameter("endtime");
		int cinemaid = Integer.parseInt(req.getParameter("cinemaid"));
		String movietitle = req.getParameter("movietitle");

		req.setAttribute("movietitle", movietitle);
		
		TimeDTO newTime = new TimeDTO();
		newTime.setStarttime(starttime);
		newTime.setEndtime(endtime);
		newTime.setCinemaid(cinemaid);
		newTime.setMovietitle(movietitle);
		System.out.println(movietitle);
		
		ActionTo acto = new ActionTo();
		TicketingDAO tdao = new TicketingDAO();
		acto.setRedirect(true);
		if(tdao.insertTime(newTime)) {
			movietitle = URLEncoder.encode(movietitle,"UTF-8");
			acto.setPath(req.getContextPath()+"/ticketing/TicketingTimeList.ti?movietitle="+movietitle);
			System.out.println("1");
		}
		else {
			movietitle = URLEncoder.encode(movietitle,"UTF-8");
			acto.setPath(req.getContextPath()+"/ticketing/TicketingTimeList.ti?movietitle="+movietitle);			
			Cookie cookie = new Cookie("w", "f");
			resp.addCookie(cookie);
			System.out.println("2");
		}
		return acto;
	}
}
