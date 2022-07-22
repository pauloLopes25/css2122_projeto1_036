package client;

import java.util.Calendar;
import java.util.Date;

public class DataCorrente {
	public static Date get() {
	Calendar in = Calendar.getInstance();
	in.set(2022, 2, 20, 10, 15,00);
	return in.getTime();
	}
}
