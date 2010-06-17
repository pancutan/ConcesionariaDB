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
package net.sf.jasperreports.components.ofc;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.export.GenericElementXlsHandler;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterContext;

/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: ChartPdfHandler.java 3031 2009-08-27 11:14:57Z teodord $
 */
public class ChartXlsHandler extends BaseChartHandler implements GenericElementXlsHandler
{
	public void exportElement(
		JRXlsExporterContext exporterContext,
		JRGenericPrintElement element,
		JRExporterGridCell gridCell,
		int colIndex,
		int rowIndex
		)
	{
		JRXlsExporter exporter = (JRXlsExporter)exporterContext.getExporter();
		
		JRExporterGridCell newGridCell = getGridCellReplacement(exporterContext, element, gridCell); 
		
		try
		{
			exporter.exportText((JRPrintText)newGridCell.getElement(), newGridCell, colIndex, rowIndex);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}
}
