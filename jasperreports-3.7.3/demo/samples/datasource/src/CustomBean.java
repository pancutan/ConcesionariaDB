/*
 * JasperReports - Free Java Reporting Library.
 * Copyright (C) 2001 - 2009 Jaspersoft Corporation. All rights reserved.
 * http://www.jaspersoft.com
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of JasperReports.
 *
 * JasperReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JasperReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with JasperReports. If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: CustomBean.java 3030 2009-08-27 11:12:48Z teodord $
 */
public class CustomBean
{


	/**
	 *
	 */
	private String city = null;
	private Integer id = null;
	private String name = null;
	private String street = null;


	/**
	 *
	 */
	public CustomBean(
		String pcity,
		Integer pid,
		String pname,
		String pstreet
		)
	{
		city = pcity;
		id = pid;
		name = pname;
		street = pstreet;
	}


	/**
	 *
	 */
	public CustomBean getMe()
	{
		return this;
	}


	/**
	 *
	 */
	public String getCity()
	{
		return city;
	}


	/**
	 *
	 */
	public Integer getId()
	{
		return id;
	}


	/**
	 *
	 */
	public String getName()
	{
		return name;
	}


	/**
	 *
	 */
	public String getStreet()
	{
		return street;
	}


}
