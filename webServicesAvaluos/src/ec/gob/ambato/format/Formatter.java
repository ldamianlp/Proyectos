package ec.gob.ambato.format;



import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


import com.google.gson.Gson;

public class Formatter implements Serializable{
	private static final long serialVersionUID = 8561977639483801316L;

	public enum Types{XML, JSON};
	private ArrayList<?> myObjects = null;
	private Object myObject = null;
	private Types myType; 
	public Formatter(String inType, ArrayList<?> inObjects){
		this.myType = Types.valueOf(inType.toUpperCase());
		this.myObjects = inObjects;
	}

	public Formatter(String inType, Object inObject){
		this.myType = Types.valueOf(inType.toUpperCase());
		this.myObject = inObject;
	}

	public StringBuilder getData(){
		switch (this.myType) {
		case JSON:
			return this.getJSONFormat();		
		}
		return null;
	}

	private StringBuilder getJSONFormat(){
		StringBuilder outCadena = null;
		if(myObject == null){
			outCadena = new StringBuilder("[");
			for (int i = 0 ; i < this.myObjects.size(); i++){
				if(i > 0)
					outCadena.append(",");
				if(this.myObjects.get(i).getClass() == LinkedHashMap.class || this.myObjects.get(i).getClass() == HashMap.class){
					HashMap<String, Object> tmpMap = (HashMap<String, Object>) this.myObjects.get(i);
					outCadena.append(new Gson().toJson(tmpMap));
				}else
					outCadena.append(new Gson().toJson(this.myObjects.get(i)));
			}
			outCadena.append("]");
		}else{
			if(myObject instanceof Map<?, ?>)
				outCadena = new StringBuilder(new Gson().toJson((Map<?, ?>)myObject));
			else
				outCadena = new StringBuilder(new Gson().toJson(myObject));
		}
		return outCadena;
	}
}
