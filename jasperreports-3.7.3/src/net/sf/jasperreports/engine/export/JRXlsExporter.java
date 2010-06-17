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
 * Wolfgang - javabreak@users.sourceforge.net
 * Mario Daepp - mdaepp@users.sourceforge.net
 */
package net.sf.jasperreports.engine.export;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.font.TextAttribute;
import java.awt.geom.Dimension2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRCommonGraphicElement;
import net.sf.jasperreports.engine.JRCommonText;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRGenericPrintElement;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRLineBox;
import net.sf.jasperreports.engine.JRPen;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintFrame;
import net.sf.jasperreports.engine.JRPrintGraphicElement;
import net.sf.jasperreports.engine.JRPrintHyperlink;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintLine;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRRenderable;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBaseFont;
import net.sf.jasperreports.engine.export.data.BooleanTextValue;
import net.sf.jasperreports.engine.export.data.DateTextValue;
import net.sf.jasperreports.engine.export.data.NumberTextValue;
import net.sf.jasperreports.engine.export.data.StringTextValue;
import net.sf.jasperreports.engine.export.data.TextValue;
import net.sf.jasperreports.engine.export.data.TextValueHandler;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.FontInfo;
import net.sf.jasperreports.engine.type.LineDirectionEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.OrientationEnum;
import net.sf.jasperreports.engine.util.JRFontUtil;
import net.sf.jasperreports.engine.util.JRImageLoader;
import net.sf.jasperreports.engine.util.JRProperties;
import net.sf.jasperreports.engine.util.JRStyledText;

import org.apache.commons.collections.ReferenceMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;


/**
 * Exports a JasperReports document to XLS format. It has binary output type and exports the document to
 * a grid-based layout.
 * 
 * @author Teodor Danciu (teodord@users.sourceforge.net)
 * @version $Id: JRXlsExporter.java 3841 2010-06-01 10:18:32Z shertage $
 */
public class JRXlsExporter extends JRXlsAbstractExporter
{

	private static final Log log = LogFactory.getLog(JRXlsAbstractExporter.class);
	
	/**
	 * The exporter key, as used in
	 * {@link GenericElementHandlerEnviroment#getHandler(net.sf.jasperreports.engine.JRGenericElementType, String)}.
	 */
	public static final String XLS_EXPORTER_KEY = JRProperties.PROPERTY_PREFIX + "xls";
	
	private static Map hssfColorsCache = new ReferenceMap();

	protected Map loadedCellStyles = new HashMap();
	protected Map anchorLinks = new HashMap();
	protected Map pageLinks = new HashMap();
	protected Map anchorNames = new HashMap();

	/**
	 *
	 */
	protected HSSFWorkbook workbook = null;
	protected HSSFSheet sheet = null;
	protected HSSFRow row = null;
	protected HSSFCell cell = null;
	protected HSSFCellStyle emptyCellStyle = null;
	protected CreationHelper createHelper = null;


	/**
	 *
	 */
	protected short whiteIndex = (new HSSFColor.WHITE()).getIndex();
	protected short blackIndex = (new HSSFColor.BLACK()).getIndex();

	protected short backgroundMode = HSSFCellStyle.SOLID_FOREGROUND;

	protected HSSFDataFormat dataFormat = null;
	protected Map formatPatternsMap = null;

	protected ExporterNature nature = null;

	protected HSSFPatriarch patriarch = null;
	
	protected String password = null;

	protected class ExporterContext extends BaseExporterContext implements JRXlsExporterContext
	{
		public String getExportPropertiesPrefix()
		{
			return XLS_EXPORTER_PROPERTIES_PREFIX;
		}
	}
	
	protected JRXlsExporterContext exporterContext = new ExporterContext();

	
	protected void setParameters()
	{
		super.setParameters();

		formatPatternsMap = (Map)getParameter(JRXlsExporterParameter.FORMAT_PATTERNS_MAP);

		nature = new JRXlsExporterNature(filter, isIgnoreGraphics, isIgnorePageMargins);
		
		password = 
			getStringParameter(
				JRXlsExporterParameter.PASSWORD,
				JRXlsExporterParameter.PROPERTY_PASSWORD
				);
		
	}


	protected void setBackground()
	{
		if (!isWhitePageBackground)
		{
			backgroundMode = HSSFCellStyle.NO_FILL;
		}
	}


	protected void openWorkbook(OutputStream os)
	{
		workbook = new HSSFWorkbook();
		emptyCellStyle = workbook.createCellStyle();
		emptyCellStyle.setFillForegroundColor((new HSSFColor.WHITE()).getIndex());
		emptyCellStyle.setFillPattern(backgroundMode);
		dataFormat = workbook.createDataFormat();
		createHelper = workbook.getCreationHelper();
	}


	protected void createSheet(String name)
	{
		sheet = workbook.createSheet(name);
		patriarch = sheet.createDrawingPatriarch();
		sheet.getPrintSetup().setLandscape(jasperPrint.getOrientationValue() == OrientationEnum.LANDSCAPE);
		short paperSize = getSuitablePaperSize(jasperPrint);

		if(paperSize != -1)
		{
			sheet.getPrintSetup().setPaperSize(paperSize);
		}
		if(password != null)
		{
			sheet.protectSheet(password);
		}
	}

	protected void closeWorkbook(OutputStream os) throws JRException
	{
		try
		{
			for (Object anchorName : anchorNames.keySet())
			{
				HSSFName anchor = (HSSFName)anchorNames.get(anchorName);
				List linkList = (List)anchorLinks.get(anchorName);
				int index = anchor.getSheetIndex();
				anchor.setRefersToFormula("'" + workbook.getSheetName(index) + "'!"+ anchor.getRefersToFormula());
				
				if(linkList != null && !linkList.isEmpty())
				{
					for(Object hyperlink : linkList)
					{
						Hyperlink link = (Hyperlink)hyperlink;
						link.setAddress(anchor.getRefersToFormula());
					}
				}
				
			}
			for (Object pageIndex : pageLinks.keySet())
			{
				List linkList = (List)pageLinks.get(pageIndex);
				if(linkList != null && !linkList.isEmpty())
				{
					for(Object hyperlink : linkList)
					{
						Hyperlink link = (Hyperlink)hyperlink;
						if(isOnePagePerSheet)
						{
							link.setAddress("'" + workbook.getSheetName(((Integer)pageIndex).intValue() - 1)+ "'!A1");
						}
						else
						{
							link.setAddress("'" + workbook.getSheetName(0)+ "'!A1");
						}
					}
				}
				
			}
			
			workbook.write(os);
		}
		catch (IOException e)
		{
			throw new JRException("Error generating XLS report : " + jasperPrint.getName(), e);
		}
	}

	protected void setColumnWidth(int col, int width)
	{
		sheet.setColumnWidth(col, 43 * width);
	}

	protected void setRowHeight(int rowIndex, int lastRowHeight)
	{
		row = sheet.getRow(rowIndex);
		if (row == null)
		{
			row = sheet.createRow(rowIndex);
		}

		row.setHeightInPoints(lastRowHeight);
	}

	protected void setCell(JRExporterGridCell gridCell, int colIndex, int rowIndex)
	{
		HSSFCell emptyCell = row.getCell(colIndex);
		if (emptyCell == null)
		{
			emptyCell = row.createCell(colIndex);
			emptyCell.setCellStyle(emptyCellStyle);
		}
	}

	protected void removeColumn(int colIndex)
	{
		sheet.setColumnHidden(colIndex, true);
//      sheet.setColumnGroupCollapsed((short)colIndex, true);
//      for(int rowIndex = sheet.getLastRowNum(); rowIndex >= 0; rowIndex--)
//      {
//          HSSFRow row = sheet.getRow(rowIndex);
//          HSSFCell cell = row.getCell((short)colIndex);
//          if (cell != null)
//          {
//              row.removeCell(cell);
//          }
//      }
	}

	protected void addBlankCell(JRExporterGridCell gridCell, int colIndex, int rowIndex)
	{
		cell = row.createCell(colIndex);

		short mode = backgroundMode;
		short backcolor = whiteIndex;
		if (!isIgnoreCellBackground && gridCell.getCellBackcolor() != null)
		{
			mode = HSSFCellStyle.SOLID_FOREGROUND;
			backcolor = getNearestColor(gridCell.getCellBackcolor()).getIndex();
		}

		short forecolor = blackIndex;
		if (gridCell.getForecolor() != null)
		{
			forecolor = getNearestColor(gridCell.getForecolor()).getIndex();
		}

		HSSFCellStyle cellStyle =
			getLoadedCellStyle(
				mode,
				backcolor,
				HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_TOP,
				(short)0,
				getLoadedFont(getDefaultFont(), forecolor, null, getLocale()),
				gridCell
				);

		cell.setCellStyle(cellStyle);
	}

	protected void addOccupiedCell(OccupiedGridCell occupiedGridCell, int colIndex, int rowIndex)
	{
	}
	
	/**
	 *
	 */
	protected void exportLine(JRPrintLine line, JRExporterGridCell gridCell, int colIndex, int rowIndex)
	{
		short forecolor = getNearestColor(line.getLinePen().getLineColor()).getIndex();

		int side = BoxStyle.TOP;
		float ratio = line.getWidth() / line.getHeight();
		if (ratio > 1)
		{
			if (line.getDirectionValue() == LineDirectionEnum.TOP_DOWN)
			{
				side = BoxStyle.TOP;
			}
			else
			{
				side = BoxStyle.BOTTOM;
			}
		}
		else
		{
			if (line.getDirectionValue() == LineDirectionEnum.TOP_DOWN)
			{
				side = BoxStyle.LEFT;
			}
			else
			{
				side = BoxStyle.RIGHT;
			}
		}
		BoxStyle boxStyle = new BoxStyle(side, line.getLinePen());

		short mode = backgroundMode;
		short backcolor = whiteIndex;
		if (!isIgnoreCellBackground && gridCell.getCellBackcolor() != null)
		{
			mode = HSSFCellStyle.SOLID_FOREGROUND;
			backcolor = getNearestColor(gridCell.getCellBackcolor()).getIndex();
		}

		HSSFCellStyle cellStyle =
			getLoadedCellStyle(
				mode,
				backcolor,
				HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_TOP,
				(short)0,
				getLoadedFont(getDefaultFont(), forecolor, null, getLocale()),
				boxStyle
				);

		createMergeRegion(gridCell, colIndex, rowIndex, cellStyle);

		cell = row.createCell(colIndex);
		cell.setCellStyle(cellStyle);
	}


	/**
	 *
	 */
	protected void exportRectangle(JRPrintGraphicElement element, JRExporterGridCell gridCell, int colIndex, int rowIndex)
	{
		short forecolor = getNearestColor(element.getLinePen().getLineColor()).getIndex();

		short mode = backgroundMode;
		short backcolor = whiteIndex;
		if (!isIgnoreCellBackground && gridCell.getCellBackcolor() != null)
		{
			mode = HSSFCellStyle.SOLID_FOREGROUND;
			backcolor = getNearestColor(gridCell.getCellBackcolor()).getIndex();
		}

		HSSFCellStyle cellStyle =
			getLoadedCellStyle(
				mode,
				backcolor,
				HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_TOP,
				(short)0,
				getLoadedFont(getDefaultFont(), forecolor, null, getLocale()),
				gridCell
				);

		createMergeRegion(gridCell, colIndex, rowIndex, cellStyle);

		cell = row.createCell(colIndex);
		cell.setCellStyle(cellStyle);
	}



	public void exportText(JRPrintText textElement, JRExporterGridCell gridCell, int colIndex, int rowIndex) throws JRException
	{
		JRStyledText styledText = getStyledText(textElement);

		if (styledText == null)
		{
			return;
		}

		short forecolor = getNearestColor(textElement.getForecolor()).getIndex();

		TextAlignHolder textAlignHolder = getTextAlignHolder(textElement);
		short horizontalAlignment = getHorizontalAlignment(textAlignHolder);
		short verticalAlignment = getVerticalAlignment(textAlignHolder);
		short rotation = getRotation(textAlignHolder);

		short mode = backgroundMode;
		short backcolor = whiteIndex;
		if (!isIgnoreCellBackground && gridCell.getCellBackcolor() != null)
		{
			mode = HSSFCellStyle.SOLID_FOREGROUND;
			backcolor = getNearestColor(gridCell.getCellBackcolor()).getIndex();
		}

		StyleInfo baseStyle =
			new StyleInfo(
				mode,
				backcolor,
				horizontalAlignment,
				verticalAlignment,
				rotation,
				getLoadedFont(textElement, forecolor, null, getTextLocale(textElement)),
				gridCell, 
				JRProperties.getBooleanProperty(textElement, JRXlsAbstractExporterParameter.PROPERTY_WRAP_TEXT, true)
				);
		createTextCell(textElement, gridCell, colIndex, rowIndex, styledText, baseStyle, forecolor);
	}


	protected void createTextCell(final JRPrintText textElement, final JRExporterGridCell gridCell, final int colIndex, final int rowIndex, final JRStyledText styledText, final StyleInfo baseStyle, final short forecolor) throws JRException
	{
		String formula = textElement.getPropertiesMap().getProperty(JRAbstractExporter.PROPERTY_CELL_FORMULA);
		String textStr = styledText.getText();
		
		if(formula != null)
		{	
			formula = formula.trim();
			if(formula.startsWith("="))
			{
				formula = formula.substring(1);
			}
			try
			{
				TextValue value = getTextValue(textElement, textStr);
				
				if (value instanceof NumberTextValue && ((NumberTextValue)value).getPattern() != null)
				{
					baseStyle.setDataFormat(
						dataFormat.getFormat(
							getConvertedPattern(((NumberTextValue)value).getPattern())
							)
						);
				}
				else if (value instanceof DateTextValue && ((DateTextValue)value).getPattern() != null)
				{
					baseStyle.setDataFormat(
							dataFormat.getFormat(
								getConvertedPattern(((DateTextValue)value).getPattern())
								)
							);
					
				}
				
				HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
				cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
				cell.setCellFormula(formula);
				endCreateCell(cellStyle);
				return;
			}
			catch(Exception e)
			{
				if(log.isWarnEnabled())
				{
					log.warn(e.getMessage());
				}
			}
		}

		if (isDetectCellType)
		{
			TextValue value = getTextValue(textElement, textStr);
			value.handle(new TextValueHandler()
			{
				public void handle(StringTextValue textValue)
				{
					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (JRCommonText.MARKUP_NONE.equals(textElement.getMarkup()))
					{
						setStringCellValue(textValue.getText());
					}
					else
					{
						setRichTextStringCellValue(styledText, forecolor, textElement, getTextLocale(textElement));
					}
					endCreateCell(cellStyle);
				}

				public void handle(NumberTextValue textValue)
				{
					if (textValue.getPattern() != null)
					{
						baseStyle.setDataFormat(
							dataFormat.getFormat(
								getConvertedPattern(textValue.getPattern())
								)
							);
					}

					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (textValue.getValue() == null)
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue().doubleValue());
					}
					endCreateCell(cellStyle);
				}

				public void handle(DateTextValue textValue)
				{
					baseStyle.setDataFormat(
						dataFormat.getFormat(
							getConvertedPattern(textValue.getPattern())
							)
						);
					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (textValue.getValue() == null)
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue());
					}
					endCreateCell(cellStyle);
				}

				public void handle(BooleanTextValue textValue)
				{
					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (textValue.getValue() == null)
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue().booleanValue());
					}
					endCreateCell(cellStyle);
				}

			});
		}
		else if (isAutoDetectCellType)
		{
			HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
			try
			{
				cell.setCellValue(Double.parseDouble(textStr));
			}
			catch(NumberFormatException e)
			{
				if (JRCommonText.MARKUP_NONE.equals(textElement.getMarkup()))
				{
					setStringCellValue(textStr);
				}
				else
				{
					setRichTextStringCellValue(styledText, forecolor, textElement, getTextLocale(textElement));
				}
			}
			endCreateCell(cellStyle);
		}
		else
		{
			HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
			if (JRCommonText.MARKUP_NONE.equals(textElement.getMarkup()))
			{
				setStringCellValue(textStr);
			}
			else
			{
				setRichTextStringCellValue(styledText, forecolor, textElement, getTextLocale(textElement));
			}
			endCreateCell(cellStyle);
		}
		
		String anchorName = textElement.getAnchorName();
		if(anchorName != null)
		{
			HSSFName aName = workbook.createName();
//			aName.setNameName(JRStringUtil.getJavaIdentifier(anchorName));
			aName.setSheetIndex(workbook.getSheetIndex(sheet));
			CellReference cRef = new CellReference(rowIndex, colIndex);
			aName.setRefersToFormula(cRef.formatAsString());
			anchorNames.put(anchorName, aName);
		}

		setHyperlinkCell(textElement);
	}


	protected HSSFCellStyle initCreateCell(JRExporterGridCell gridCell, int colIndex, int rowIndex, StyleInfo baseStyle)
	{
		HSSFCellStyle cellStyle = getLoadedCellStyle(baseStyle);
		createMergeRegion(gridCell, colIndex, rowIndex, cellStyle);
		cell = row.createCell(colIndex);
		return cellStyle;
	}

	protected void endCreateCell(HSSFCellStyle cellStyle)
	{
		cell.setCellStyle(cellStyle);
	}
	
	protected final void setStringCellValue(String textStr)
	{
		//cell.setCellValue(JRStringUtil.replaceDosEOL(textStr));
		//cell.setCellValue(textStr);
		cell.setCellValue(new HSSFRichTextString(textStr));
	}
	
	protected final void setRichTextStringCellValue(JRStyledText styledText, short forecolor, JRFont defaultFont, Locale locale)
	{	
		if(styledText != null)
		{
			cell.setCellValue(getRichTextString(styledText, forecolor, defaultFont, locale));
		}
	}

	protected HSSFRichTextString getRichTextString(JRStyledText styledText, short forecolor, JRFont defaultFont, Locale locale)
	{
		String text = styledText.getText();
		HSSFRichTextString richTextStr = new HSSFRichTextString(text);
		int runLimit = 0;
		AttributedCharacterIterator iterator = styledText.getAttributedString().getIterator();

		while(runLimit < styledText.length() && (runLimit = iterator.getRunLimit()) <= styledText.length())
		{
			Map attributes = iterator.getAttributes();
			JRFont runFont = attributes.isEmpty()? defaultFont : new JRBaseFont(attributes);
			short runForecolor = attributes.get(TextAttribute.FOREGROUND) != null ? 
					getNearestColor((Color)attributes.get(TextAttribute.FOREGROUND)).getIndex() :
					forecolor;
			HSSFFont font = getLoadedFont(runFont, runForecolor, attributes, locale);
			richTextStr.applyFont(iterator.getIndex(), runLimit, font);
			iterator.setIndex(runLimit);
		}
		return richTextStr;
	}

	protected void createMergeRegion(JRExporterGridCell gridCell, int colIndex, int rowIndex, HSSFCellStyle cellStyle)
	{
		int rowSpan = isCollapseRowSpan ? 1 : gridCell.getRowSpan();
		if (gridCell.getColSpan() > 1 || rowSpan > 1)
		{
			sheet.addMergedRegion(new CellRangeAddress(rowIndex, (rowIndex + rowSpan - 1), 
					colIndex, (colIndex + gridCell.getColSpan() - 1)));

			for(int i = 0; i < rowSpan; i++)
			{
				HSSFRow spanRow = sheet.getRow(rowIndex + i);
				if (spanRow == null)
				{
					spanRow = sheet.createRow(rowIndex + i);
				}
				for(int j = 0; j < gridCell.getColSpan(); j++)
				{
					HSSFCell spanCell = spanRow.getCell((colIndex + j));
					if (spanCell == null)
					{
						spanCell = spanRow.createCell((colIndex + j));
					}
					spanCell.setCellStyle(cellStyle);
				}
			}
		}
	}

	private short getHorizontalAlignment(TextAlignHolder alignment)
	{
		switch (alignment.horizontalAlignment)
		{
			case RIGHT:
				return HSSFCellStyle.ALIGN_RIGHT;
			case CENTER:
				return HSSFCellStyle.ALIGN_CENTER;
			case JUSTIFIED:
				return HSSFCellStyle.ALIGN_JUSTIFY;
			case LEFT:
			default:
				return HSSFCellStyle.ALIGN_LEFT;
		}
	}

	private short getVerticalAlignment(TextAlignHolder alignment)
	{
		switch (alignment.verticalAlignment)
		{
			case BOTTOM:
				return HSSFCellStyle.VERTICAL_BOTTOM;
			case MIDDLE:
				return HSSFCellStyle.VERTICAL_CENTER;
			case JUSTIFIED:
				return HSSFCellStyle.VERTICAL_JUSTIFY;
			case TOP:
			default:
				return HSSFCellStyle.VERTICAL_TOP;
		}
	}

	private short getRotation(TextAlignHolder alignment)
	{
		switch (alignment.rotation)
		{
			case LEFT:
				return 90;
			case RIGHT:
				return -90;
			case UPSIDE_DOWN:
			case NONE:
			default:
				return 0;
		}
	}

	/**
	 *
	 */
	protected static HSSFColor getNearestColor(Color awtColor)
	{
		HSSFColor color = (HSSFColor) hssfColorsCache.get(awtColor);

		if (color == null)
		{
			Map triplets = HSSFColor.getTripletHash();
			if (triplets != null)
			{
				Collection keys = triplets.keySet();
				if (keys != null && keys.size() > 0)
				{
					Object key = null;
					HSSFColor crtColor = null;
					short[] rgb = null;
					int diff = 0;
					int minDiff = 999;
					for (Iterator it = keys.iterator(); it.hasNext();)
					{
						key = it.next();

						crtColor = (HSSFColor) triplets.get(key);
						rgb = crtColor.getTriplet();

						diff = Math.abs(rgb[0] - awtColor.getRed()) + Math.abs(rgb[1] - awtColor.getGreen()) + Math.abs(rgb[2] - awtColor.getBlue());

						if (diff < minDiff)
						{
							minDiff = diff;
							color = crtColor;
						}
					}
				}
			}

			hssfColorsCache.put(awtColor, color);
		}

		return color;
	}


	/**
	 *
	 */
	protected HSSFFont getLoadedFont(JRFont font, short forecolor, Map attributes, Locale locale)
	{
		HSSFFont cellFont = null;

		String fontName = font.getFontName();
		if (fontMap != null && fontMap.containsKey(fontName))
		{
			fontName = (String) fontMap.get(fontName);
		}
		else
		{
			FontInfo fontInfo = JRFontUtil.getFontInfo(fontName, locale);
			if (fontInfo != null)
			{
				//fontName found in font extensions
				FontFamily family = fontInfo.getFontFamily();
				String exportFont = family.getExportFont(getExporterKey());
				if (exportFont != null)
				{
					fontName = exportFont;
				}
			}
		}
		
		short superscriptType = HSSFFont.SS_NONE;
		
		if( attributes != null && attributes.get(TextAttribute.SUPERSCRIPT) != null)
		{
			Object value = attributes.get(TextAttribute.SUPERSCRIPT);
			if(TextAttribute.SUPERSCRIPT_SUPER.equals(value))
			{
				superscriptType = HSSFFont.SS_SUPER;
			}
			else if(TextAttribute.SUPERSCRIPT_SUB.equals(value))
			{
				superscriptType = HSSFFont.SS_SUB;
			}
			
		}
		for (int i = 0; i < loadedFonts.size(); i++)
		{
			HSSFFont cf = (HSSFFont)loadedFonts.get(i);

			short fontSize = (short)font.getFontSize();
			if (isFontSizeFixEnabled)
			{
				fontSize -= 1;
			}
			if (
				cf.getFontName().equals(fontName) &&
				(cf.getColor() == forecolor) &&
				(cf.getFontHeightInPoints() == fontSize) &&
				((cf.getUnderline() == HSSFFont.U_SINGLE)?(font.isUnderline()):(!font.isUnderline())) &&
				(cf.getStrikeout() == font.isStrikeThrough()) &&
				((cf.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD)?(font.isBold()):(!font.isBold())) &&
				(cf.getItalic() == font.isItalic()) &&
				(cf.getTypeOffset() == superscriptType)
				)
			{
				cellFont = cf;
				break;
			}
		}

		if (cellFont == null)
		{
			cellFont = workbook.createFont();

			cellFont.setFontName(fontName);
			cellFont.setColor(forecolor);

			short fontSize = (short)font.getFontSize();
			if (isFontSizeFixEnabled)
			{
				fontSize -= 1;
			}
			cellFont.setFontHeightInPoints(fontSize);

			if (font.isUnderline())
			{
				cellFont.setUnderline(HSSFFont.U_SINGLE);
			}
			if (font.isStrikeThrough())
			{
				cellFont.setStrikeout(true);
			}
			if (font.isBold())
			{
				cellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			}
			if (font.isItalic())
			{
				cellFont.setItalic(true);
			}

			cellFont.setTypeOffset(superscriptType);
			loadedFonts.add(cellFont);
		}

		return cellFont;
	}


	protected HSSFCellStyle getLoadedCellStyle(StyleInfo style)
	{
		HSSFCellStyle cellStyle = (HSSFCellStyle) loadedCellStyles.get(style);
		if (cellStyle == null)
		{
			cellStyle = workbook.createCellStyle();

			cellStyle.setFillForegroundColor(style.backcolor);
			cellStyle.setFillPattern(style.mode);
			cellStyle.setAlignment(style.horizontalAlignment);
			cellStyle.setVerticalAlignment(style.verticalAlignment);
			cellStyle.setRotation(style.rotation);
			cellStyle.setFont(style.font);
			cellStyle.setWrapText(style.wrapText);

			if (style.hasDataFormat())
			{
				cellStyle.setDataFormat(style.getDataFormat());
			}

			if (!isIgnoreCellBorder)
			{
				BoxStyle box = style.box;
				cellStyle.setBorderTop(box.borderStyle[BoxStyle.TOP]);
				cellStyle.setTopBorderColor(box.borderColour[BoxStyle.TOP]);
				cellStyle.setBorderLeft(box.borderStyle[BoxStyle.LEFT]);
				cellStyle.setLeftBorderColor(box.borderColour[BoxStyle.LEFT]);
				cellStyle.setBorderBottom(box.borderStyle[BoxStyle.BOTTOM]);
				cellStyle.setBottomBorderColor(box.borderColour[BoxStyle.BOTTOM]);
				cellStyle.setBorderRight(box.borderStyle[BoxStyle.RIGHT]);
				cellStyle.setRightBorderColor(box.borderColour[BoxStyle.RIGHT]);
			}

			loadedCellStyles.put(style, cellStyle);
		}
		return cellStyle;
	}

	protected HSSFCellStyle getLoadedCellStyle(
			short mode,
			short backcolor,
			short horizontalAlignment,
			short verticalAlignment,
			short rotation,
			HSSFFont font,
			JRExporterGridCell gridCell
			)
	{
		StyleInfo style = new StyleInfo(mode, backcolor, horizontalAlignment, verticalAlignment, rotation, font, gridCell);
		return getLoadedCellStyle(style);
	}

	protected HSSFCellStyle getLoadedCellStyle(
		short mode,
		short backcolor,
		short horizontalAlignment,
		short verticalAlignment,
		short rotation,
		HSSFFont font,
		BoxStyle box
		)
	{
		StyleInfo style = new StyleInfo(mode, backcolor, horizontalAlignment, verticalAlignment, rotation, font, box);
		return getLoadedCellStyle(style);
	}

	/**
	 *
	 */
	protected static short getBorderStyle(JRPen pen)
	{
		float lineWidth = pen.getLineWidth().floatValue();

		if (lineWidth > 0f)
		{
			switch (pen.getLineStyleValue())
			{
				case DOUBLE :
				{
					return HSSFCellStyle.BORDER_DOUBLE;
				}
				case DOTTED :
				{
					return HSSFCellStyle.BORDER_DOTTED;
				}
				case DASHED :
				{
					if (lineWidth >= 1f)
					{
						return HSSFCellStyle.BORDER_MEDIUM_DASHED;
					}

					return HSSFCellStyle.BORDER_DASHED;
				}
				case SOLID :
				default :
				{
					if (lineWidth >= 2f)
					{
						return HSSFCellStyle.BORDER_THICK;
					}
					else if (lineWidth >= 1f)
					{
						return HSSFCellStyle.BORDER_MEDIUM;
					}
					else if (lineWidth >= 0.5f)
					{
						return HSSFCellStyle.BORDER_THIN;
					}

					return HSSFCellStyle.BORDER_HAIR;
				}
			}
		}

		return HSSFCellStyle.BORDER_NONE;
	}

	protected void exportImage(JRPrintImage element, JRExporterGridCell gridCell, int colIndex, int rowIndex, int emptyCols) throws JRException
	{
		try
		{
			int topPadding =
				Math.max(element.getLineBox().getTopPadding().intValue(), getImageBorderCorrection(element.getLineBox().getTopPen()));
			int leftPadding =
				Math.max(element.getLineBox().getLeftPadding().intValue(), getImageBorderCorrection(element.getLineBox().getLeftPen()));
			int bottomPadding =
				Math.max(element.getLineBox().getBottomPadding().intValue(), getImageBorderCorrection(element.getLineBox().getBottomPen()));
			int rightPadding =
				Math.max(element.getLineBox().getRightPadding().intValue(), getImageBorderCorrection(element.getLineBox().getRightPen()));

			//pngEncoder.setImage( null );

			int availableImageWidth = element.getWidth() - leftPadding - rightPadding;
			availableImageWidth = availableImageWidth < 0 ? 0 : availableImageWidth;

			int availableImageHeight = element.getHeight() - topPadding - bottomPadding;
			availableImageHeight = availableImageHeight < 0 ? 0 : availableImageHeight;

			JRRenderable renderer = element.getRenderer();

			if (
				renderer != null &&
				availableImageWidth > 0 &&
				availableImageHeight > 0
				)
			{
				if (renderer.getType() == JRRenderable.TYPE_IMAGE)
				{
					// Image renderers are all asked for their image data and dimension at some point.
					// Better to test and replace the renderer now, in case of lazy load error.
					renderer = JRImageRenderer.getOnErrorRendererForImageData(renderer, element.getOnErrorTypeValue());
					if (renderer != null)
					{
						renderer = JRImageRenderer.getOnErrorRendererForDimension(renderer, element.getOnErrorTypeValue());
					}
				}
			}
			else
			{
				renderer = null;
			}

			if (renderer != null)
			{
				int normalWidth = availableImageWidth;
				int normalHeight = availableImageHeight;

				Dimension2D dimension = renderer.getDimension();
				if (dimension != null)
				{
					normalWidth = (int) dimension.getWidth();
					normalHeight = (int) dimension.getHeight();
				}

				float xalignFactor = 0f;
				switch (element.getHorizontalAlignmentValue())
				{
					case RIGHT:
					{
						xalignFactor = 1f;
						break;
					}
					case CENTER:
					{
						xalignFactor = 0.5f;
						break;
					}
					case LEFT:
					default:
					{
						xalignFactor = 0f;
						break;
					}
				}

				float yalignFactor = 0f;
				switch (element.getVerticalAlignmentValue())
				{
					case BOTTOM:
					{
						yalignFactor = 1f;
						break;
					}
					case MIDDLE:
					{
						yalignFactor = 0.5f;
						break;
					}
					case TOP:
					default:
					{
						yalignFactor = 0f;
						break;
					}
				}

				BufferedImage bi = new BufferedImage(element.getWidth(), element.getHeight(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D grx = bi.createGraphics();

				switch (element.getScaleImageValue())
				{
					case CLIP:
					{
						int xoffset = (int) (xalignFactor * (availableImageWidth - normalWidth));
						int yoffset = (int) (yalignFactor * (availableImageHeight - normalHeight));

						Shape oldClipShape = grx.getClip();

						grx.clip(
							new Rectangle(
								leftPadding,
								topPadding,
								availableImageWidth,
								availableImageHeight
								)
							);

						try
						{
							renderer.render(
								grx,
								new Rectangle(
									xoffset + leftPadding,
									yoffset + topPadding,
									normalWidth,
									normalHeight
									)
								);
						}
						finally
						{
							grx.setClip(oldClipShape);
						}

						break;
					}
					case FILL_FRAME:
					{
						renderer.render(
							grx,
							new Rectangle(
								leftPadding,
								topPadding,
								availableImageWidth,
								availableImageHeight
								)
							);

						break;
					}
					case RETAIN_SHAPE:
					default:
					{
						if (element.getHeight() > 0)
						{
							double ratio = (double) normalWidth / (double) normalHeight;

							if (ratio > (double) availableImageWidth / (double) availableImageHeight)
							{
								normalWidth = availableImageWidth;
								normalHeight = (int) (availableImageWidth / ratio);
							}
							else
							{
								normalWidth = (int) (availableImageHeight * ratio);
								normalHeight = availableImageHeight;
							}

							int xoffset = leftPadding + (int) (xalignFactor * (availableImageWidth - normalWidth));
							int yoffset = topPadding + (int) (yalignFactor * (availableImageHeight - normalHeight));

							renderer.render(
								grx,
								new Rectangle(
									xoffset,
									yoffset,
									normalWidth,
									normalHeight
									)
								);
						}

						break;
					}
				}

				short mode = backgroundMode;
				short backcolor = whiteIndex;
				if (!isIgnoreCellBackground && gridCell.getCellBackcolor() != null)
				{
					mode = HSSFCellStyle.SOLID_FOREGROUND;
					backcolor = getNearestColor(gridCell.getCellBackcolor()).getIndex();
				}

				short forecolor = getNearestColor(element.getLineBox().getPen().getLineColor()).getIndex();

				if(element.getModeValue() == ModeEnum.OPAQUE )
				{
					backcolor = getNearestColor(element.getBackcolor()).getIndex();
				}

				HSSFCellStyle cellStyle =
					getLoadedCellStyle(
						mode,
						backcolor,
						HSSFCellStyle.ALIGN_LEFT,
						HSSFCellStyle.VERTICAL_TOP,
						(short)0,
						getLoadedFont(getDefaultFont(), forecolor, null, getLocale()),
						gridCell
						);

				createMergeRegion(gridCell, colIndex, rowIndex, cellStyle);

				cell = row.createCell(colIndex);
				cell.setCellStyle(cellStyle);

				HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, 
						(short) colIndex, rowIndex, 
						(short) (colIndex + gridCell.getColSpan()), 
						rowIndex + (isCollapseRowSpan ? 1 : gridCell.getRowSpan()));
				anchor.setAnchorType(2);
				//pngEncoder.setImage(bi);
				//int imgIndex = workbook.addPicture(pngEncoder.pngEncode(), HSSFWorkbook.PICTURE_TYPE_PNG);
				int imgIndex = workbook.addPicture(JRImageLoader.loadImageDataFromAWTImage(bi, JRRenderable.IMAGE_TYPE_PNG), HSSFWorkbook.PICTURE_TYPE_PNG);
				patriarch.createPicture(anchor, imgIndex);
				
//				setHyperlinkCell(element);
			}
		}
		catch (Exception ex)
		{
			throw new JRException("The cell cannot be added", ex);
		}
		catch (Error err)
		{
			throw new JRException("The cell cannot be added", err);
		}
	}


	protected void exportFrame(JRPrintFrame frame, JRExporterGridCell gridCell, int x, int y)
	{
		short mode = backgroundMode;
		short backcolor = whiteIndex;
		if (frame.getModeValue() == ModeEnum.OPAQUE)
		{
			mode = HSSFCellStyle.SOLID_FOREGROUND;
			backcolor = getNearestColor(frame.getBackcolor()).getIndex();
		}

		short forecolor = getNearestColor(frame.getForecolor()).getIndex();

		HSSFCellStyle cellStyle =
			getLoadedCellStyle(
				mode,
				backcolor,
				HSSFCellStyle.ALIGN_LEFT,
				HSSFCellStyle.VERTICAL_TOP,
				(short)0,
				getLoadedFont(getDefaultFont(), forecolor, null, getLocale()),
				gridCell
				);

		createMergeRegion(gridCell, x, y, cellStyle);

		cell = row.createCell(x);
		cell.setCellStyle(cellStyle);
	}


	protected void exportGenericElement(JRGenericPrintElement element, JRExporterGridCell gridCell, int colIndex, int rowIndex, int emptyCols) throws JRException
	{
		GenericElementXlsHandler handler = (GenericElementXlsHandler) 
		GenericElementHandlerEnviroment.getHandler(
				element.getGenericType(), XLS_EXPORTER_KEY);

		if (handler != null)
		{
			handler.exportElement(exporterContext, element, gridCell, colIndex, rowIndex);
		}
		else
		{
			if (log.isDebugEnabled())
			{
				log.debug("No XLS generic element handler for " 
						+ element.getGenericType());
			}
		}
	}


	protected ExporterNature getNature()
	{
		return nature;
	}

	/**
	 * This method is intended to modify a given format pattern so to include
	 * only the accepted proprietary format characters. The resulted pattern
	 * will possibly truncate the original pattern
	 * @param pattern
	 * @return pattern converted to accepted proprietary formats
	 */
	private String getConvertedPattern(String pattern)
	{
		if (formatPatternsMap != null && formatPatternsMap.containsKey(pattern))
		{
			return (String) formatPatternsMap.get(pattern);
		}
		return pattern;
	}

	private final short getSuitablePaperSize(JasperPrint jasP)
	{

		if (jasP == null)
		{
			return -1;
		}
		long width = 0;
		long height = 0;
		short ps = -1;

		if ((jasP.getPageWidth() != 0) && (jasP.getPageHeight() != 0))
		{

			double dWidth = (jasP.getPageWidth() / 72.0);
			double dHeight = (jasP.getPageHeight() / 72.0);

			height = Math.round(dHeight * 25.4);
			width = Math.round(dWidth * 25.4);

			// Compare to ISO 216 A-Series (A3-A5). All other ISO 216 formats
			// not supported by POI Api yet.
			// A3 papersize also not supported by POI Api yet.
			for (int i = 4; i < 6; i++)
			{
				int w = calculateWidthForDinAN(i);
				int h = calculateHeightForDinAN(i);

				if (((w == width) && (h == height)) || ((h == width) && (w == height)))
				{
					if (i == 4)
					{
						ps = HSSFPrintSetup.A4_PAPERSIZE;
					}
					else if (i == 5)
					{
						ps = HSSFPrintSetup.A5_PAPERSIZE;
					}
					break;
				}
			}
			
			//envelope sizes
			if (ps == -1)
			{
				// ISO 269 sizes - "Envelope DL" (110 � 220 mm)
				if (((width == 110) && (height == 220)) || ((width == 220) && (height == 110)))
				{
					ps = HSSFPrintSetup.ENVELOPE_DL_PAPERSIZE;
				}
			}

			// Compare to common North American Paper Sizes (ANSI X3.151-1987).
			if (ps == -1)
			{
				// ANSI X3.151-1987 - "Letter" (216 � 279 mm)
				if (((width == 216) && (height == 279)) || ((width == 279) && (height == 216)))
				{
					ps = HSSFPrintSetup.LETTER_PAPERSIZE;
				}
				// ANSI X3.151-1987 - "Legal" (216 � 356 mm)
				if (((width == 216) && (height == 356)) || ((width == 356) && (height == 216)))
				{
					ps = HSSFPrintSetup.LEGAL_PAPERSIZE;
				}
				// ANSI X3.151-1987 - "Executive" (190 � 254 mm)
				else if (((width == 190) && (height == 254)) || ((width == 254) && (height == 190)))
				{
					ps = HSSFPrintSetup.EXECUTIVE_PAPERSIZE;
				}
				// ANSI X3.151-1987 - "Ledger/Tabloid" (279 � 432 mm)
				// Not supported by POI Api yet.
				
			}
		}
		return ps;
	}
	
	protected void setHyperlinkCell(JRPrintHyperlink hyperlink)
	{
		
		String href = null;
		Hyperlink link = null;
		JRHyperlinkProducer customHandler = getHyperlinkProducer(hyperlink);
		if (customHandler == null)
		{
			switch (hyperlink.getHyperlinkTypeValue())
			{
				case REFERENCE:
				{
					href = hyperlink.getHyperlinkReference();
					if (href != null)
					{
						link = createHelper.createHyperlink(Hyperlink.LINK_URL);
					    link.setAddress(href);
					}
					break;
				}
				case LOCAL_ANCHOR :
				{
					href = hyperlink.getHyperlinkAnchor();
					if (href != null)
					{
						link = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
					    if(anchorLinks.containsKey(href))
					    {
					    	((List)anchorLinks.get(href)).add(link);
					    }
					    else
					    {
					    	List hrefList = new ArrayList();
					    	hrefList.add(link);
					    	anchorLinks.put(href, hrefList);
					    }
					    
					}
					break;
					
				}
				case LOCAL_PAGE :
				{
					Integer hrefPage = hyperlink.getHyperlinkPage();
					if (hrefPage != null)
					{
						link = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);
					    if(pageLinks.containsKey(hrefPage))
					    {
					    	((List)pageLinks.get(hrefPage)).add(link);
					    }
					    else
					    {
					    	List hrefList = new ArrayList();
					    	hrefList.add(link);
					    	pageLinks.put(hrefPage, hrefList);
					    }
					}
					break;
				}
				case REMOTE_ANCHOR :
				{
					href = hyperlink.getHyperlinkReference();
					if (href != null && hyperlink.getHyperlinkAnchor() != null)
					{
						href = href + "#" + hyperlink.getHyperlinkAnchor();
						link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
					    link.setAddress(href);
					    
					}
					break;
					
				}
				case REMOTE_PAGE :
				{
					href = hyperlink.getHyperlinkReference();
					if (href != null && hyperlink.getHyperlinkPage() != null)
					{
						href = href + "#JR_PAGE_ANCHOR_0_" + hyperlink.getHyperlinkPage().toString();
						link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
					    link.setAddress(href);
					    
					}
					break;
					
				}
				case NONE:
				default:
				{
				}
			}
			if(link != null)
			{
				//TODO: make tooltips functional
//				if(hyperlink.getHyperlinkTooltip() != null)
//				{
//				    link.setLabel(hyperlink.getHyperlinkTooltip());
//				}
				cell.setHyperlink(link);
			}
			
		}
		else
		{
			//FIXME: to handle a custom hyperlink
			//href = customHandler.getHyperlink(hyperlink);
		}
	}

	protected static class BoxStyle
	{
		protected static final int TOP = 0;
		protected static final int LEFT = 1;
		protected static final int BOTTOM = 2;
		protected static final int RIGHT = 3;

		protected short[] borderStyle = new short[4];
		protected short[] borderColour = new short[4];
		private int hash;

		public BoxStyle(int side, JRPen pen)
		{
			borderStyle[side] = getBorderStyle(pen);
			borderColour[side] = getNearestColor(pen.getLineColor()).getIndex();

			hash = computeHash();
		}

		public BoxStyle(JRExporterGridCell gridCell)
		{
			JRLineBox lineBox = gridCell.getBox();
			if (lineBox != null)
			{
				setBox(lineBox);
			}
			JRPrintElement element = gridCell.getElement();
			if (element instanceof JRCommonGraphicElement)
			{
				setPen(((JRCommonGraphicElement)element).getLinePen());
			}

			hash = computeHash();
		}

		public void setBox(JRLineBox box)
		{
			borderStyle[TOP] = getBorderStyle(box.getTopPen());
			borderColour[TOP] = getNearestColor(box.getTopPen().getLineColor()).getIndex();

			borderStyle[BOTTOM] = getBorderStyle(box.getBottomPen());
			borderColour[BOTTOM] = getNearestColor(box.getBottomPen().getLineColor()).getIndex();

			borderStyle[LEFT] = getBorderStyle(box.getLeftPen());
			borderColour[LEFT] = getNearestColor(box.getLeftPen().getLineColor()).getIndex();

			borderStyle[RIGHT] = getBorderStyle(box.getRightPen());
			borderColour[RIGHT] = getNearestColor(box.getRightPen().getLineColor()).getIndex();

			hash = computeHash();
		}

		public void setPen(JRPen pen)
		{
			if (
				borderStyle[TOP] == HSSFCellStyle.BORDER_NONE
				&& borderStyle[LEFT] == HSSFCellStyle.BORDER_NONE
				&& borderStyle[BOTTOM] == HSSFCellStyle.BORDER_NONE
				&& borderStyle[RIGHT] == HSSFCellStyle.BORDER_NONE
				)
			{
				short style = getBorderStyle(pen);
				short colour = getNearestColor(pen.getLineColor()).getIndex();

				borderStyle[TOP] = style;
				borderStyle[BOTTOM] = style;
				borderStyle[LEFT] = style;
				borderStyle[RIGHT] = style;

				borderColour[TOP] = colour;
				borderColour[BOTTOM] = colour;
				borderColour[LEFT] = colour;
				borderColour[RIGHT] = colour;
			}

			hash = computeHash();
		}

		private int computeHash()
		{
			int hashCode = borderStyle[TOP];
			hashCode = 31*hashCode + borderColour[TOP];
			hashCode = 31*hashCode + borderStyle[BOTTOM];
			hashCode = 31*hashCode + borderColour[BOTTOM];
			hashCode = 31*hashCode + borderStyle[LEFT];
			hashCode = 31*hashCode + borderColour[LEFT];
			hashCode = 31*hashCode + borderStyle[RIGHT];
			hashCode = 31*hashCode + borderColour[RIGHT];
			return hashCode;
		}

		public int hashCode()
		{
			return hash;
		}

		public boolean equals(Object o)
		{
			BoxStyle b = (BoxStyle) o;

			return
				b.borderStyle[TOP] == borderStyle[TOP] &&
				b.borderColour[TOP] == borderColour[TOP] &&
				b.borderStyle[BOTTOM] == borderStyle[BOTTOM] &&
				b.borderColour[BOTTOM] == borderColour[BOTTOM] &&
				b.borderStyle[LEFT] == borderStyle[LEFT] &&
				b.borderColour[LEFT] == borderColour[LEFT] &&
				b.borderStyle[RIGHT] == borderStyle[RIGHT] &&
				b.borderColour[RIGHT] == borderColour[RIGHT];
		}

		public String toString()
		{
			return "(" +
				borderStyle[TOP] + "/" + borderColour[TOP] + "," +
				borderStyle[BOTTOM] + "/" + borderColour[BOTTOM] + "," +
				borderStyle[LEFT] + "/" + borderColour[LEFT] + "," +
				borderStyle[RIGHT] + "/" + borderColour[RIGHT] + ")";
		}
	}


	protected static class StyleInfo
	{
		protected final short mode;
		protected final short backcolor;
		protected final short horizontalAlignment;
		protected final short verticalAlignment;
		protected final short rotation;
		protected final HSSFFont font;
		protected final BoxStyle box;
		protected final boolean wrapText;
		private short dataFormat = -1;
		private int hashCode;

		public StyleInfo(
				short mode,
				short backcolor,
				short horizontalAlignment,
				short verticalAlignment,
				short rotation,
				HSSFFont font,
				JRExporterGridCell gridCell
				)
			{
				this(
					mode,
					backcolor,
					horizontalAlignment,
					verticalAlignment,
					rotation,
					font,
					new BoxStyle(gridCell),
					true
					);
			}

		public StyleInfo(
				short mode,
				short backcolor,
				short horizontalAlignment,
				short verticalAlignment,
				short rotation,
				HSSFFont font,
				JRExporterGridCell gridCell,
				boolean wrapText
				)
			{
				this(
					mode,
					backcolor,
					horizontalAlignment,
					verticalAlignment,
					rotation,
					font,
					new BoxStyle(gridCell),
					wrapText
					);
			}

		public StyleInfo(
				short mode,
				short backcolor,
				short horizontalAlignment,
				short verticalAlignment,
				short rotation,
				HSSFFont font,
				BoxStyle box
				)
			{
			this(
					mode,
					backcolor,
					horizontalAlignment,
					verticalAlignment,
					rotation,
					font,
					box,
					true
					);
			}

		public StyleInfo(
				short mode,
				short backcolor,
				short horizontalAlignment,
				short verticalAlignment,
				short rotation,
				HSSFFont font,
				BoxStyle box,
				boolean wrapText
				)
			{
				this.mode = mode;
				this.backcolor = backcolor;
				this.horizontalAlignment = horizontalAlignment;
				this.verticalAlignment = verticalAlignment;
				this.rotation = rotation;
				this.font = font;

				this.box = box;
				this.wrapText = wrapText;

				hashCode = computeHash();
			}

		protected int computeHash()
		{
			int hash = mode;
			hash = 31*hash + backcolor;
			hash = 31*hash + horizontalAlignment;
			hash = 31*hash + verticalAlignment;
			hash = 31*hash + rotation;
			hash = 31*hash + (font == null ? 0 : font.getIndex());
			hash = 31*hash + (box == null ? 0 : box.hashCode());
			hash = 31*hash + dataFormat;
			hash = 31*hash + (this.wrapText ? 0 : 1);
			return hash;
		}

		public void setDataFormat(short dataFormat)
		{
			this.dataFormat = dataFormat;
			hashCode = computeHash();
		}

		public boolean hasDataFormat()
		{
			return dataFormat != -1;
		}

		public short getDataFormat()
		{
			return dataFormat;
		}

		public int hashCode()
		{
			return hashCode;
		}

		public boolean equals(Object o)
		{
			StyleInfo s = (StyleInfo) o;

			return s.mode == mode
					&& s.backcolor == backcolor
					&& s.horizontalAlignment == horizontalAlignment
					&& s.verticalAlignment == verticalAlignment
					&& s.rotation == rotation
					&& (s.font == null ? font == null : (font != null && s.font.getIndex() == font.getIndex()))
					&& (s.box == null ? box == null : (box != null && s.box.equals(box)))
					&& s.rotation == rotation && s.wrapText == wrapText;
		}

		public String toString()
		{
			return "(" +
				mode + "," + backcolor + "," +
				horizontalAlignment + "," + verticalAlignment + "," +
				rotation + "," + font + "," +
				box + "," + dataFormat + "," + wrapText + ")";
		}
	}

	/**
	 *
	 */
	protected String getExporterKey()
	{
		return XLS_EXPORTER_KEY;
	}

}
