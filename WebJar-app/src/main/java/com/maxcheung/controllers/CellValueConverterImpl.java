package com.maxcheung.controllers;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Service;

import com.maxcheung.models.FormatType;
import com.maxcheung.models.CellValue;
import com.maxcheung.models.CellValueHighChartBar;
import com.maxcheung.models.CellValueHighChartPie;

/**
 * Default implementation that is able to accept an cell value and convert this into a another cell value.
 */
@Service
public class CellValueConverterImpl implements CellValueConverter{

	DozerBeanMapper mapper = new DozerBeanMapper();

    /**
     * Convert from Cell Value to Cell Value
     * 
     * @param source
     * @param formatType
     * @return convert CellValue
     */
	@Override
    public CellValue  convert(CellValue source,  FormatType formatType) {
    	if ( formatType == FormatType.HIGHCHARTPIE) {
    		return mapper.map(source, CellValueHighChartPie.class);
    	} else  if ( formatType == FormatType.HIGHCHARTBAR) {
    		return mapper.map(source, CellValueHighChartBar.class);
    	}
        return source;
    }


    
}
