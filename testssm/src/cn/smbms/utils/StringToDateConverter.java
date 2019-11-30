package cn.smbms.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String,Date> {
	private String datapattern;
	public StringToDateConverter(String datapattern) {
		System.out.println("StringToDateConverter Converter:"+datapattern);
		this.datapattern=datapattern;
	}
	@Override
	public Date convert(String s) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datapattern).parse(s);
			System.out.println("Converter Date"+date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
