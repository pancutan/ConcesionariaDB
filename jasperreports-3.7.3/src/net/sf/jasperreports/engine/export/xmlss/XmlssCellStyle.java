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
package net.sf.jasperreports.engine.export.xmlss;

import java.awt.Color;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import net.sf.jasperreports.engine.JRAlignment;
import net.sf.jasperreports.engine.JRFont;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JRStyle;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RotationEnum;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.type.VerticalAlignEnum;
import net.sf.jasperreports.engine.util.JRColorUtil;


/**
 * @author sanda zaharia (shertage@users.sourceforge.net)
 * @version $Id: XmlssCellStyle.java 3665 2010-03-31 14:22:48Z shertage $
 */
public class XmlssCellStyle extends XmlssBorderStyle
{

	private static final String ALIGNMENT_LEFT = "Left";
	private static final String ALIGNMENT_RIGHT = "Right";
	private static final String ALIGNMENT_CENTER = "Center";
	private static final String ALIGNMENT_TOP = "Top";
	private static final String ALIGNMENT_BOTTOM = "Bottom";
	private static final String READING_ORDER_LTR = "LeftToRight";
	private static final String READING_ORDER_RTL = "RightToLeft";
	private static final String ROTATE_NONE = "0";
	private static final String ROTATE_LEFT = "90";
	private static final String ROTATE_RIGHT = "-90";
	private static final String STYLE_AUTOMATIC = "Automatic";
	private static final String STYLE_NONE = "None";
	private static final String UNDERLINE_STYLE_SINGLE = "Single";
	
	//private String fill = null;
	private final String id;

	private String backcolor = XmlssCellStyle.STYLE_AUTOMATIC;
	
	private String horizontalAlignment = XmlssCellStyle.ALIGNMENT_LEFT;
	private String verticalAlignment = XmlssCellStyle.ALIGNMENT_TOP;
	private String readingOrder = XmlssCellStyle.READING_ORDER_LTR;
	private String rotate = XmlssCellStyle.ROTATE_NONE;
	private String shrinkToFit;
	private String wrapText = "1";
	
	private JRStyle style;
	private String verticalPosition = XmlssCellStyle.STYLE_NONE;
	private String pattern;
	private String forecolor = XmlssCellStyle.STYLE_AUTOMATIC;
	private JRFont defaultFont;
	private String excelFontName;
	/**
	 *
	 */
	public XmlssCellStyle(
			Writer styleWriter, 
			JRPrintElement element,
			Color cellBackground,
			String pattern,
			boolean isShrinkToFit,
			JRFont defaultFont,
			Map fontMap)
	{
		super(styleWriter, element);

		this.style = element.getStyle() != null ? element.getStyle() : element.getDefaultStyleProvider().getDefaultStyle();
		this.defaultFont = defaultFont;
		this.pattern = pattern;
		this.shrinkToFit = String.valueOf(getBitValue(isShrinkToFit));
		
		if (ModeEnum.OPAQUE == element.getModeValue())
		{
			if(element.getBackcolor() != null)
			{
				backcolor = "#" + JRColorUtil.getColorHexa(element.getBackcolor());
			}
			else if(style.getBackcolor() != null)
			{
				backcolor = "#" + JRColorUtil.getColorHexa(style.getBackcolor());
			}
		}
		else
		{
			if(cellBackground != null)
			{
				backcolor = "#" + JRColorUtil.getColorHexa(cellBackground);
			}
		}
		
		if(element.getForecolor() != null)
		{
			forecolor = "#" + JRColorUtil.getColorHexa(element.getForecolor());
		}
		else if(style.getForecolor() != null)
		{
			forecolor = "#" + JRColorUtil.getColorHexa(style.getForecolor());
		}
		
		RotationEnum rotation = element instanceof JRPrintText ? ((JRPrintText)element).getRotationValue() : RotationEnum.NONE;
		rotate = getRotation(rotation);
		
		
		if(element instanceof JRPrintText && ((JRPrintText)element).getRunDirectionValue() == RunDirectionEnum.RTL)
		{
			readingOrder = XmlssCellStyle.READING_ORDER_RTL;
		}
		
		JRAlignment alignment = element instanceof JRAlignment ? (JRAlignment)element : null;
		if (alignment != null)
		{
			horizontalAlignment = getHorizontalAlignment(alignment.getHorizontalAlignmentValue(), alignment.getVerticalAlignmentValue(), rotation);
			verticalAlignment = getVerticalAlignment(alignment.getHorizontalAlignmentValue(), alignment.getVerticalAlignmentValue(), rotation);
		}
		
		if(style!= null)
		{
			String fontName = style.getFontName();
			excelFontName = (fontMap != null && fontMap.containsKey(fontName)) 
				? (String) fontMap.get(fontName) 
				: fontName;
			
			id =horizontalAlignment + "|" +
				verticalAlignment + "|" +
				readingOrder + "|" +
				rotate + "|" +
				shrinkToFit + "|" +
				super.getId() + "|" +
				excelFontName + "|" +
				style.getFontSize() + "|" +
				forecolor + "|" +
				style.isItalic() + "|" +
				style.isBold() + "|" +
				style.isStrikeThrough() + "|" +
				style.isUnderline() + "|" +
				verticalPosition + "|" +
				backcolor + "|" +
				this.pattern;
		}
		else
		{
			String fontName = defaultFont.getFontName();
			excelFontName = (fontMap != null && fontMap.containsKey(fontName)) 
				? (String) fontMap.get(fontName) 
				: fontName;
			id =horizontalAlignment + "|" +
				verticalAlignment + "|" +
				readingOrder + "|" +
				rotate + "|" +
				shrinkToFit + "|" +
				super.getId() + "|" +
				excelFontName + "|" +
				defaultFont.getFontSize() + "|" +
				forecolor + "|" +
				defaultFont.isItalic() + "|" +
				defaultFont.isBold() + "|" +
				defaultFont.isStrikeThrough() + "|" +
				defaultFont.isUnderline() + "|" +
				verticalPosition + "|" +
				backcolor + "|" +
				this.pattern;
		}
	}
	
	/**
	 *
	 */
	public String getId()
	{
		return id; 
	}

	/**
	 *
	 */
	public void write(String cellStyleName) throws IOException
	{
		styleWriter.write("<ss:Style ss:ID=\"");
		styleWriter.write(cellStyleName);
		styleWriter.write("\">\n");
		
		styleWriter.write(" <ss:Alignment");
		styleWriter.write(" ss:Horizontal=\"" + horizontalAlignment + "\"");
		styleWriter.write(" ss:Vertical=\"" + verticalAlignment + "\"");
		styleWriter.write(" ss:ReadingOrder=\"" + readingOrder + "\"");
		styleWriter.write(" ss:Rotate=\"" + rotate + "\"");
		styleWriter.write(" ss:ShrinkToFit=\"" + shrinkToFit + "\"");
		styleWriter.write(" ss:WrapText=\"" + wrapText + "\"");
				
		styleWriter.write("/>\n");

		styleWriter.write(" <ss:Borders>");
		writeBorder(TOP_BORDER);
		writeBorder(LEFT_BORDER);
		writeBorder(BOTTOM_BORDER);
		writeBorder(RIGHT_BORDER);
		styleWriter.write(" </ss:Borders>\n");
		
		styleWriter.write(" <ss:Font");
		styleWriter.write(" ss:FontName=\"" + excelFontName + "\"");
		if(style != null)
		{
			if(style.getFontSize() != null)
			{
				styleWriter.write(" ss:Size=\"" + style.getFontSize() + "\"");
			}
			if(style.isBold() != null)
			{
				styleWriter.write(" ss:Bold=\"" + getBitValue(style.isBold().booleanValue()) + "\"");
			}
			if(style.isItalic() != null)
			{
				styleWriter.write(" ss:Italic=\"" + getBitValue(style.isItalic().booleanValue()) + "\"");
			}
			if(style.isStrikeThrough() != null)
			{
				styleWriter.write(" ss:StrikeThrough=\"" + getBitValue(style.isStrikeThrough().booleanValue()) + "\"");
			}
			if(style.isUnderline() != null)
			{
				styleWriter.write(" ss:Underline=\"" + getUnderlineStyle(style.isUnderline().booleanValue()) + "\"");
			}
		}
		else if(defaultFont != null)
		{
			styleWriter.write(" ss:Size=\"" + defaultFont.getFontSize() + "\"");
			styleWriter.write(" ss:Bold=\"" + getBitValue(defaultFont.isBold()) + "\"");
			styleWriter.write(" ss:Italic=\"" + getBitValue(defaultFont.isItalic()) + "\"");
			styleWriter.write(" ss:StrikeThrough=\"" + getBitValue(defaultFont.isStrikeThrough()) + "\"");
			styleWriter.write(" ss:Underline=\"" + getUnderlineStyle(defaultFont.isUnderline()) + "\"");
		}
		styleWriter.write(" ss:Color=\"" + forecolor + "\"");
		styleWriter.write("/>\n");
		
		styleWriter.write(" <ss:Interior");
		styleWriter.write(" ss:Color=\"" + backcolor + "\"");
		styleWriter.write(" ss:Pattern=\"Solid\"");
		styleWriter.write("/>\n");
		
		styleWriter.write(" <ss:NumberFormat");
		styleWriter.write(" ss:Format=\"" + pattern + "\"");
		styleWriter.write("/>\n");
		
		styleWriter.write(" <ss:Protection/>\n");
		
		styleWriter.write("</ss:Style>\n");
		
	}

	/**
	 *
	 */
	public static String getVerticalAlignment(
		HorizontalAlignEnum horizontalAlignment, 
		VerticalAlignEnum verticalAlignment, 
		RotationEnum rotation
		)
	{
		switch(rotation)
		{
			case LEFT:
			{
				switch (horizontalAlignment)
				{
					case RIGHT :
						return XmlssCellStyle.ALIGNMENT_TOP;
					case CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JUSTIFIED :
					case LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
				}
			}
			case RIGHT:
			{
				switch (horizontalAlignment)
				{
					case RIGHT :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
					case CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JUSTIFIED :
					case LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_TOP;
				}
			}
			case UPSIDE_DOWN:
			case NONE:
			default:
			{
				switch (verticalAlignment)
				{
					case BOTTOM :
						return XmlssCellStyle.ALIGNMENT_BOTTOM;
					case MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_TOP;
				}
			}
		}
	}
	
	/**
	 *
	 */
	public static String getHorizontalAlignment(
		HorizontalAlignEnum horizontalAlignment, 
		VerticalAlignEnum verticalAlignment, 
		RotationEnum rotation
		)
	{
		switch(rotation)
		{
			case LEFT:
			{
				switch (verticalAlignment)
				{
					case BOTTOM :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
					case MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_LEFT;
				}
			}
			case RIGHT:
			{
				switch (verticalAlignment)
				{
					case BOTTOM :
						return XmlssCellStyle.ALIGNMENT_LEFT;
					case MIDDLE :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case TOP :
					default :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
				}
			}
			case UPSIDE_DOWN:
			case NONE:
			default:
			{
				switch (horizontalAlignment)
				{
					case RIGHT :
						return XmlssCellStyle.ALIGNMENT_RIGHT;
					case CENTER :
						return XmlssCellStyle.ALIGNMENT_CENTER;
					case JUSTIFIED :
					case LEFT :
					default :
						return XmlssCellStyle.ALIGNMENT_LEFT;
				}
			}
		}
	}
	
	
	private String getRotation(RotationEnum rotation)
	{
		switch (rotation)
		{
			case LEFT:
				return XmlssCellStyle.ROTATE_LEFT;
			case RIGHT:
				return XmlssCellStyle.ROTATE_RIGHT;
			case NONE:
			default:
				return XmlssCellStyle.ROTATE_NONE;
		}
	}
	
	private byte getBitValue(boolean isTrue)
	{
			return isTrue ? (byte)1 : 0;
	}

	private String getUnderlineStyle(boolean isUnderline)
	{
		if(isUnderline)
		{
			return XmlssCellStyle.UNDERLINE_STYLE_SINGLE;
		}
		return XmlssCellStyle.STYLE_NONE;
	}
	
}

