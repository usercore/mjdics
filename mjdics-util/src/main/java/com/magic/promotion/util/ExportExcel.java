package com.magic.promotion.util;


/*************************************************
  Copyright (C), 2006-2008, GLOT Technologies Co., Ltd. All rights reserved.
  File name:        ExcelWriter.java
  Author:           jkf5180       
  Version:          1.0        
  Date:             2004-8-19
  Description:      定义Excel的标题样式、数据样式、锁定样式等。
  Others:
  Function List:
                    1.defineCaptionStyle()
                        定义标题样式
                    2.defineDataStyle()
                        定义数据样式
                    3.defineLockStyle()
                        定义锁定样式
                    4.defineDateStyle()
                        定义日期样式
                    5.defineSheet()
                        定义Sheet样式及名称
                    6.defineFieldTitle(String[] headers)
                        定义标题
                    7.defineData(String[] types,int[] width,Hashtable tableDatas) 
                        定义数据
                    8.write(String[] headers,String[] types,int[] width,Hashtable tableDatas) 
                        写入Excel文件
  History:
                    1. Date:            2004-11-8
                       Author:          jkf5180
                       Modification:    增加注释
*************************************************/

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class ExportExcel {
	Logger log = LoggerFactory.getLogger(ExportExcel.class);
	private String filename = "";      //文件名
	private String outputdir = "";     //输出路径
	
	private HSSFCellStyle cellStyle2=null;
	
	/**
	 * 设置文件目录，目录必须存在
     * @param dir   文件路径
	 */
	public void setDIR(String dir) {
		this.outputdir = dir;
	}
    
    /**
     * 设置文件名，文件名不能为空。
     * @param fileName   文件名
     */
	public void setFileName(String fileName) {
		this.filename = fileName;
	}
	
	
	private HSSFWorkbook workBook = null;
	public HSSFWorkbook getWorkBook() {
		return workBook;
	}


	private HSSFSheet sheet = null; 

    /**
     * 定义数据样式
     */
	private HSSFCellStyle defineDataStyle() {
		if (null == workBook) {
			return null;
		}
		log.debug("定义数据样式--开始");
		//HSSFFont dataFont = workBook.createFont(); //数据字体
		
		HSSFFont data_font = workBook.createFont();
		// 设置字体大小12
		data_font.setFontHeightInPoints((short) 12);
		data_font.setColor((short) HSSFFont.COLOR_NORMAL);
		
		cellStyle2 = workBook.createCellStyle();  //数据样式
		
		//数据样式
		cellStyle2.setFont(data_font);
		cellStyle2.setWrapText(true);
		cellStyle2.setBorderLeft((short)1);
		cellStyle2.setBorderTop((short)1);
		cellStyle2.setBorderBottom( (short) 1);
		cellStyle2.setBorderRight( (short) 1);
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		log.debug("定义数据样式--结束");
		
		return cellStyle2;
	}
	
    /**
     * 定义锁定样式
     */
	private HSSFCellStyle defineLockStyle() {
		if (null == workBook) {
			return null;
		}
		log.debug("定义锁定样式--开始");
		HSSFFont lockFont = workBook.createFont(); //数据字体
		
		HSSFCellStyle lockCellStyle = workBook.createCellStyle();
		//锁定的样式
		lockCellStyle.setFont(lockFont);
		lockCellStyle.setBorderBottom( (short) 1);
		lockCellStyle.setBorderRight( (short) 1);
		lockCellStyle.setWrapText(true);
		lockCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		lockCellStyle.setLocked(true); //锁定单元格
		lockCellStyle.setFillPattern( (short) HSSFCellStyle.SOLID_FOREGROUND);
		lockCellStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		log.debug("定义锁定样式--结束");
		
		return lockCellStyle;
	}

    /**
     * 定义日期样式
     */
	private HSSFCellStyle defineDateStyle() {
		if (null == workBook) {
			return null;
		}
		log.debug("定义日期样式--开始");
		HSSFFont dateFont = workBook.createFont(); //数据字体
		HSSFCellStyle dateCellStyle = workBook.createCellStyle();
		//时间样式
		dateCellStyle.setFont(dateFont);
		dateCellStyle.setBorderBottom( (short) 1);
		dateCellStyle.setBorderRight( (short) 1);
		dateCellStyle.setWrapText(true);
		dateCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		log.debug("定义日期样式--结束");
		
		return dateCellStyle;
	}
	
    /**
     * 定义Sheet样式及名称
     */
	public void defineSheet() {
		if (null == workBook) {
			return;
		}

		log.debug("定义Sheet样式及名称--开始");
		sheet.setGridsPrinted(false);
		
		//    请改成所需的sheet名
		workBook.setSheetName(0, "sheet0");
		log.debug("定义Sheet样式及名称--结束");
		
		cellStyle2 = defineDataStyle();
	}
	public void defineSheet(String sheetName,int sheetNumber) {
		if (null == workBook) {
			return;
		}

		log.debug("定义Sheet样式及名称--开始");
		sheet.setGridsPrinted(false);
		
		//    请改成所需的sheet名
		workBook.setSheetName(sheetNumber, sheetName);
		log.debug("定义Sheet样式及名称--结束");
		
		cellStyle2 = defineDataStyle();
	}


	public void defineFieldTitle(String[] headers,int rown){
		log.debug("定义标题--开始");
		HSSFRow row = sheet.createRow(rown);
		HSSFCell cell = null; //单元格
		HSSFCellStyle cellStyle = workBook.createCellStyle();
		HSSFFont font = workBook.createFont();
		// 设置字体大小
		font.setFontHeightInPoints((short) 14);
		font.setColor(HSSFFont.COLOR_RED);
		cellStyle.setFillForegroundColor(HSSFColor.GOLD.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cellStyle.setFont(font);
		for (short colnum = (short) 0; colnum < (short) headers.length; colnum++) {
			cell = row.createCell(colnum);              //HSSFCell.CELL_TYPE_STRING);
			//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(headers[ (int) colnum]);
			cell.setCellStyle(cellStyle);
		}
		log.debug("定义标题--结束");
	}

    /**
     * 定义数据
     */
	public void defineData(String[] types,int[] width,Hashtable tableDatas) 
		throws Exception {
		log.debug("定义数据--开始");
		short rownum = (short)0;
		
		HSSFRow row = null;
		HSSFCell cell = null; //单元格
		
		HSSFCellStyle cellStyle2 = defineDataStyle();
		HSSFCellStyle lockCellStyle = defineLockStyle();
		HSSFCellStyle dateCellStyle = defineDateStyle();
		
		for (int i = 1; i <= tableDatas.size(); i++) {
			rownum++;
			row = sheet.createRow( (short) (rownum));

			Hashtable hrecord = (Hashtable) tableDatas.get(new Integer(i)); //一行数据
			for (int j = 1; j <= hrecord.size(); j++) {
				sheet.setColumnWidth( (short) (j - 1), (short) (width[j - 1] * 550));
				Object acell = hrecord.get(new Integer(j)); //一行中的一个单元格数据

				cell = row.createCell( (short) (j - 1));
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16); //ENCODING_COMPRESSED_UNICODE

				cell.setCellStyle(cellStyle2);
				if (j == 1) {
					cell.setCellStyle(lockCellStyle);
				}
				
//				log.debug("j = " + j);
				
				if ("double".equals(types[j - 1])) {
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
					cell.setCellValue( ( (Double) acell).doubleValue());
				}
				else if ("Date".equals(types[j - 1])) {
					cell.setCellValue( (java.util.Date) acell);
					cell.setCellStyle(dateCellStyle);
				}
				else if ("Calendar".equals(types[j - 1])) {
					cell.setCellValue( (Calendar) acell);
				}
				else if ("String".equals(types[j - 1])) {
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue( (String) acell);
				}
				else if ("boolean".equals(types[j - 1])) {
					cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
					cell.setCellValue( ( (Boolean) acell).booleanValue());
				}
				else {
					log.debug("导出 EXCEL 发生错误： 错误的数据类型!");
					throw new Exception("导出 EXCEL 发生错误： 错误的数据类型!");
				}
			}
		}
		log.debug("定义数据--结束");
	}
	
	public void clearStyle(HSSFCell cell, String data) throws Exception {
		HSSFCellStyle style = workBook.createCellStyle();  //数据样式
		
		//数据样式
		//cellStyle2.setFont(dataFont);
		style.setWrapText(true);
		style.setBorderLeft((short)0);
		style.setBorderTop((short)0);
		style.setBorderBottom( (short) 0);
		style.setBorderRight( (short) 0);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		cell.setCellStyle(style);
	}
	
	public void setAlignStyle(HSSFCell cell, short alignStyle) throws Exception {
		HSSFCellStyle style = workBook.createCellStyle();  //数据样式
		
		//数据样式
		//cellStyle2.setFont(dataFont);
		style.setWrapText(true);
		style.setBorderLeft((short)0);
		style.setBorderTop((short)0);
		style.setBorderBottom( (short) 0);
		style.setBorderRight( (short) 0);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(alignStyle);
		cell.setCellStyle(style);
	}
	
	public void setFontRedStyle(HSSFCell cell)throws Exception{
		HSSFFont font = workBook.createFont();
		font.setColor(HSSFColor.RED.index);
		
        HSSFCellStyle style = workBook.createCellStyle();
		
		//数据样式
		//cellStyle2.setFont(dataFont);
		style.setWrapText(true);
		style.setBorderLeft((short)0);
		style.setBorderTop((short)0);
		style.setBorderBottom( (short) 0);
		style.setBorderRight( (short) 0);
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		style.setFont(font);
		cell.setCellStyle(style);
	}
	public void writeData(HSSFCell cell, String data) throws Exception {
		log.debug("定义数据--开始");

		//cell.setEncoding(HSSFCell.ENCODING_UTF_16); // ENCODING_COMPRESSED_UNICODE
		if(cell.getCellStyle()==null)
		cell.setCellStyle(cellStyle2);
		
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(data);
		if(data!=null){
		short col=cell.getCellNum();
		short currentWidth=sheet.getColumnWidth(col);
		/*if(data.length()>currentWidth)
			sheet.setColumnWidth(col, (short)data.length());*/
		}
		log.debug("定义数据--结束");
	}


	public void writeImage(int col1,int row1,int col2,int row2, String fileName) throws Exception {
		 ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();;   
	     BufferedImage bufferImg = ImageIO.read(new File(fileName));  
	     ImageIO.write(bufferImg,"jpg",byteArrayOut);;   
	     int height=bufferImg.getHeight();
	     int width=bufferImg.getWidth();
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0,0,height,width,(short)col1,row1,(short)col2,row2);
		byteArrayOut.close();
		
	}
	public HSSFCell mergedRow(int row,int col,int count){
		HSSFCell cell=this.getCell(row, col);
		sheet.addMergedRegion(new Region(row,(short)col,row+count,(short)col));
		return cell;
	}
	
	public HSSFCell mergedCol(int row,int col,int count){
		Region region=new Region(row,(short)col,row,(short)(col+count));
		sheet.addMergedRegion(region);
		HSSFCell cell=this.getCell(row, col);
		cell.setCellStyle(cellStyle2);
		return cell;
	}
	public HSSFCell mergedColl(int row,int col,int count){
		Region region=new Region(row,(short)col,row,(short)(col+count));
		sheet.addMergedRegion(region);
		HSSFCell cell=this.getCell(row, col);
		cell.setCellStyle(cellStyle2);
		return cell;
	} 
	public HSSFCell getCell(int row, int col) {
		HSSFRow sheetRow = sheet.getRow(row);
		if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
		}
		HSSFCell cell = sheetRow.getCell((short) col);
		if (cell == null) {
			cell = sheetRow.createCell((short) col);
		}
		cell.setCellStyle(cellStyle2);
		return cell;
	}
	
	public HSSFCell getCell(int row, int col,short rowHeight,short fontSize,short align) {
		HSSFRow sheetRow = sheet.getRow(row);
		//if (sheetRow == null) {
			sheetRow = sheet.createRow(row);
			sheetRow.setHeight(rowHeight);
		//}
		HSSFCell cell = sheetRow.getCell((short) col);
		if (cell == null) {
			cell = sheetRow.createCell((short) col);
		}
		
		HSSFCellStyle cellStyle=null;
		cellStyle = workBook.createCellStyle();  //数据样式
		
		HSSFFont data_font = workBook.createFont();
		// 设置字体大小12
		data_font.setFontHeightInPoints(fontSize);
		data_font.setColor((short) HSSFFont.COLOR_NORMAL);
		//数据样式
		short type = HSSFCellStyle.BORDER_THIN ;
//		cellStyle.setBorderBottom(type);// 下边框   
//		cellStyle.setBorderLeft(type);// 左边框   
//		cellStyle.setBorderRight(type);// 右边框   
//		cellStyle.setBorderTop(type);// 上边框   
		cellStyle.setFont(data_font);
		cellStyle.setWrapText(true);
		/*cellStyle.setBorderLeft((short)0);
		cellStyle.setBorderTop((short)1);
		cellStyle.setBorderBottom( (short) 1);
		cellStyle.setBorderRight( (short) 1);*/
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle.setAlignment(align);
		cell.setCellStyle(cellStyle);
		return cell;
	}
	
	public void begin(String fileName){
		setFileName(fileName);
		workBook = new HSSFWorkbook(); // 工作薄
		sheet = workBook.createSheet(); // sheet
		sheet.setDefaultColumnWidth((short) 20);
		// 定义sheet样式及名称
		defineSheet();
		// 定义标题
	}
	public void begin(String fileName,String sheetName,int sheetNumber){
		setFileName(fileName);
		if(workBook == null){
		    workBook = new HSSFWorkbook(); // 工作薄
		}
		sheet = workBook.createSheet(); // sheet

		// 定义sheet样式及名称
		defineSheet(sheetName,sheetNumber);
	}
	
	public void close()throws Exception{
		FileOutputStream out = new FileOutputStream(outputdir+filename);
		workBook.write(out);
		out.close();
	}
	public HSSFSheet getSheet() {
		return sheet;
	}
	
	/*public static String toUtf8String(String fileName,HttpServletRequest request){ 
		String agent = request.getHeader("User-Agent");
        boolean isMSIE = (agent != null && agent.indexOf("MSIE") != -1);
        try{
        if( isMSIE ){
        	fileName = java.net.URLEncoder.encode(fileName,"UTF8");
        }else{
        	fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        }
        }catch(Exception e){
        	e.printStackTrace();
        }
	  return fileName; 
	}*/
}
