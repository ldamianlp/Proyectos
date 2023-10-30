package ec.gob.enumeracion;

public enum TypeTtipoDocumento {
	

	    CED, RUC, PASS;

	    static {
	      Enum.valueOf(TypeTtipoDocumento.class, TypeTtipoDocumento.CED.name());
	    }

	    public boolean isValid() {
	        return true;
	    }

}

