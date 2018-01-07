package it.polito.dp2.NFV.sol1;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.util.ValidationEventCollector;

class NfvValidationEventCollector extends ValidationEventCollector {

	@Override
	public boolean handleEvent(ValidationEvent event) {
		super.handleEvent(event);
		return true;
	}

}
