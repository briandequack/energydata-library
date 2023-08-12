package nl.energydata.library.log;

import java.util.function.Supplier;

import nl.energydata.library.objectholder.ObjectHolder;
import nl.energydata.library.session.SessionGroup;

public interface ILogService {

	<T>T executeSafeWithLog(String message, Supplier<T> dbOperation);
	
	void executeSafeWithLog(String message, Runnable dbOperation);

	ObjectHolder setLocalObjectHolder(ObjectHolder objectHolder);

	void clearLocal();
	

}
