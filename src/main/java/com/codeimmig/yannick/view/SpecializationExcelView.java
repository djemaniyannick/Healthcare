package com.codeimmig.yannick.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.codeimmig.yannick.model.Specialization;

public class SpecializationExcelView  extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//1.Define your own excel file name
		response.addHeader("Content-Disposition", "attachment;filename=SPECS.xlsx");
		//2.read data given by controller
		@SuppressWarnings("unchecked")
		List<Specialization> list=(List<Specialization>) model.get("list");
		//3-create one sheet
		Sheet sheet=workbook.createSheet("Specialization");
		//4-Create Row as header 
		 setHead(sheet);
		 //5-create row#1 onwards from List<T>
		 setBody(sheet, list);
	}

	private void setHead(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("CODE");
		row.createCell(2).setCellValue("NAME");
		row.createCell(3).setCellValue("NOTE");
	}
	
	private void setBody(Sheet sheet, List<Specialization> list) {
		int rowNum=1;
		for (Specialization specialization : list) {
			Row row=sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(specialization.getId());
			row.createCell(1).setCellValue(specialization.getSpecCode());
			row.createCell(2).setCellValue(specialization.getSpecName());
			row.createCell(3).setCellValue(specialization.getSpecNote());
		}
		
	}
}
