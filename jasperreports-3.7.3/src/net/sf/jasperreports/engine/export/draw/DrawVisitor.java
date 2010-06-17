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

/*
 * Contributors:
 * Eugene D - eugenedruy@users.sourceforge.net 
 * Adrian Jackson - iapetus@users.sourceforge.net
 * David Taylor - exodussystems@users.sourceforge.net
 * Lars Kristensen - llk@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export.draw;

import java.awt.Graphics2D;

import net.sf.jasperreports.crosstabs.JRCrosstab;
import net.sf.jasperreports.engine.JRBreak;
import net.sf.jasperreports.engine.JRChart;
import net.sf.jasperreports.engine.JRComponentElement;
import net.sf.jasperreports.engine.JRElementGroup;
import net.sf.jasperreports.engine.JREllipse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRGenericElement;
import net.sf.jasperreports.engine.JRImage;
import net.sf.jasperreports.engine.JRLine;
import net.sf.jasperreports.engine.JRRectangle;
import net.sf.jasperreports.engine.JRReport;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRStaticText;
import net.sf.jasperreports.engine.JRSubreport;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVisitor;
import net.sf.jasperreports.engine.convert.ConvertVisitor;
import net.sf.jasperreports.engine.convert.ReportConverter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.TextRenderer;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;


/**
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: DrawVisitor.java 3178 2009-10-28 10:02:20Z teodord $
 */
public class DrawVisitor implements JRVisitor
{
	
	protected TextRenderer textRenderer = null;

	protected ConvertVisitor convertVisitor = null;
	protected Graphics2D grx = null;

	protected LineDrawer lineDrawer = new LineDrawer();
	protected RectangleDrawer rectangleDrawer = new RectangleDrawer();
	protected EllipseDrawer ellipseDrawer = new EllipseDrawer();
	protected ImageDrawer imageDrawer = new ImageDrawer();
	protected TextDrawer textDrawer = null;
	protected FrameDrawer frameDrawer = null;
	
	/**
	 *
	 */
	public DrawVisitor(JRReport report, Graphics2D grx)
	{
		this(new ReportConverter(report, true, true), grx);

		setTextRenderer(report);
	}

	/**
	 *
	 */
	public DrawVisitor(ReportConverter reportConverter, Graphics2D grx)
	{
		this.convertVisitor = new ConvertVisitor(reportConverter);
		setTextRenderer(reportConverter.getReport());
		setGraphics2D(grx);
		frameDrawer.setClip(true);
	}

	/**
	 *
	 */
	public void setGraphics2D(Graphics2D grx)
	{
		this.grx = grx;
	}

	/**
	 *
	 */
	public void setTextRenderer(JRReport report)
	{
		textRenderer = 
			new TextRenderer(
				JRProperties.getBooleanProperty(report, JRGraphics2DExporter.MINIMIZE_PRINTER_JOB_SIZE, true),
				JRProperties.getBooleanProperty(report, JRStyledText.PROPERTY_AWT_IGNORE_MISSING_FONT, false)
				);
		
		textDrawer = new TextDrawer(textRenderer);
		frameDrawer = new FrameDrawer(null, textRenderer);
	}

	/**
	 *
	 */
	public void visitBreak(JRBreak breakElement)
	{
		//FIXMEDRAW
	}

	/**
	 *
	 */
	public void visitChart(JRChart chart)
	{
		try
		{
			imageDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(chart), 
				-chart.getX(), 
				-chart.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	/**
	 *
	 */
	public void visitCrosstab(JRCrosstab crosstab)
	{
		try
		{
			frameDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(crosstab), 
				-crosstab.getX(), 
				-crosstab.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	/**
	 *
	 */
	public void visitElementGroup(JRElementGroup elementGroup)
	{
		//nothing to draw. elements are drawn individually.
	}

	/**
	 *
	 */
	public void visitEllipse(JREllipse ellipse)
	{
		ellipseDrawer.draw(
			grx,
			convertVisitor.getVisitPrintElement(ellipse), 
			-ellipse.getX(), 
			-ellipse.getY()
			);
	}

	/**
	 *
	 */
	public void visitFrame(JRFrame frame)
	{
		try
		{
			frameDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(frame), 
				-frame.getX(), 
				-frame.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	/**
	 *
	 */
	public void visitImage(JRImage image)
	{
		try
		{
			imageDrawer.draw(
					grx,
					convertVisitor.getVisitPrintElement(image), 
					-image.getX(), 
					-image.getY()
					);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	/**
	 *
	 */
	public void visitLine(JRLine line)
	{
		lineDrawer.draw(
			grx,
			convertVisitor.getVisitPrintElement(line), 
			-line.getX(), 
			-line.getY()
			);
	}

	/**
	 *
	 */
	public void visitRectangle(JRRectangle rectangle)
	{
		rectangleDrawer.draw(
			grx,
			convertVisitor.getVisitPrintElement(rectangle), 
			-rectangle.getX(), 
			-rectangle.getY()
			);
	}

	/**
	 *
	 */
	public void visitStaticText(JRStaticText staticText)
	{
		textDrawer.draw(
			grx,
			convertVisitor.getVisitPrintElement(staticText), 
			-staticText.getX(), 
			-staticText.getY()
			);
	}

	/**
	 *
	 */
	public void visitSubreport(JRSubreport subreport)
	{
		try
		{
			imageDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(subreport), 
				-subreport.getX(), 
				-subreport.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	/**
	 *
	 */
	public void visitTextField(JRTextField textField)
	{
		textDrawer.draw(
			grx,
			convertVisitor.getVisitPrintElement(textField), 
			-textField.getX(), 
			-textField.getY()
			);
	}

	public void visitComponentElement(JRComponentElement componentElement)
	{
		try
		{
			imageDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(componentElement), 
				-componentElement.getX(), 
				-componentElement.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

	public void visitGenericElement(JRGenericElement element)
	{
		try
		{
			imageDrawer.draw(
				grx,
				convertVisitor.getVisitPrintElement(element), 
				-element.getX(), 
				-element.getY()
				);
		}
		catch (JRException e)
		{
			throw new JRRuntimeException(e);
		}
	}

}
