package com.nhb.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpRequestResponseHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;

import com.nhb.domain.user.Useracc;

@Component
@Scope(value = "singleton")
public class WebSecurityContextRepository implements SecurityContextRepository {

	private static final Logger logger = LoggerFactory.getLogger(WebSecurityContextRepository.class);
	public static final String WTS_SESSION = "wts_session";

	@Override
	public SecurityContext loadContext(HttpRequestResponseHolder requestResponseHolder) {
		HttpServletRequest req = requestResponseHolder.getRequest();
		SessionCache sessionCache = (SessionCache) req.getSession(true).getAttribute(WTS_SESSION);
		SecurityContext context = SecurityContextHolder.getContext();
		if (sessionCache != null && sessionCache.getAuthenticationToken() != null) {
			Authentication auth = (Authentication) sessionCache.getAuthenticationToken();
			context.setAuthentication(auth);
			return context;
		}
		return context;
	}

	@Override
	public void saveContext(SecurityContext context, HttpServletRequest request, HttpServletResponse response) {
		
	}

	public SessionCache createSessionCache(Useracc useracc) {
		SessionCache sc = new SessionCache(useracc.getLogin());
		sc.setUser(useracc);
		return sc;
	}

	public void addSessionCache(SessionCache cache, HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(WTS_SESSION) != null) {
			logger.warn("security context already found for username:" + cache.getUser());
		}
		cache.setHttpSessionId(session.getId());
		session.setAttribute(WTS_SESSION, cache);
	}

	public SessionCache getSessionCache(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (SessionCache) session.getAttribute(WTS_SESSION);
	}

	public void deleteSessionCache(HttpServletRequest request) {
		SessionCache sessionCache = (SessionCache)(request.getSession().getAttribute(WTS_SESSION));
		if (sessionCache != null) {
			logger.warn("security context found and remove for username:" + sessionCache.getUser());
			request.getSession().removeAttribute(WTS_SESSION);
		}
	}

	@Override
	public boolean containsContext(HttpServletRequest request) {
		if (request.getSession().getAttribute(WTS_SESSION) != null) {
			return true;
		}
		return false;
	}

}
