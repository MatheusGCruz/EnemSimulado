package com.enemSimulado.auxiliary;

import org.springframework.stereotype.Service;

@Service
public class BusinessAuxiliary {

	public Integer languageQuantity(Integer questionQuantities) {
		Integer returnInteger =	questionQuantities/5;
		if(returnInteger > 5) {
			returnInteger = 5;
		}
		return returnInteger;
	}
}


