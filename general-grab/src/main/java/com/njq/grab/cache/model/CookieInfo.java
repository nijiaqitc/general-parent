package com.njq.grab.cache.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.http.client.CookieStore;

public class CookieInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CookieStore store;
	private Date expirationDate;

	public CookieStore getStore() {
		return store;
	}

	public void setStore(CookieStore store) {
		this.store = store;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
