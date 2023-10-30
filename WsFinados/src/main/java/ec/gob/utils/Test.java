package ec.gob.utils;

import java.util.Arrays;
import java.util.List;

public class Test {
	private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(Test.class);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String lv_cadena="";
		UtilsDOA util =  new UtilsDOA();
		lv_cadena = util.validarCedula("1234567890");
		LOGGER.info("consultaLicencia: " + lv_cadena);

		SpFinaCatNegocioDAO obj  = new SpFinaCatNegocioDAO();
		obj.getRequisitosByCategoria(1);*/

		//		String a = Enums.Errores.e03.getCodError();
		//		System.out.println(a);
//		String puestoslibres = "626,629,632,633,634,636,637,639,640,641,642.643.644,645";
//		String codlibres = "626,629,632,633,634,636,637,639,640,641,642.643.644,645";
		String puestoslibres = "626,629";
		String codlibres = "626,629,632";
		String[] alPuestos = puestoslibres.split(",");
		int pCantidadPuestos = Integer.valueOf("2");


		List<String> listPuestosLibres = Arrays.asList(alPuestos);	

		String inicioS = listPuestosLibres.get(0);
		int inicio = Integer.valueOf(inicioS);// Integer(inicioS);
		String finS = listPuestosLibres.get(listPuestosLibres.size()-1);
		int fin = Integer.valueOf(finS);//new Integer(finS);
		int atras = inicio;

		int conta = 0;
		/*if(pCantidadPuestos == 1)
			conta = 1;
		else 
			conta = 0;*/
		
		if(pCantidadPuestos>1) {
			if(inicio!=fin) {
				for(int i=inicio;i<=fin ;i++) {
					//				int totalLibres = listPuestosLibres.size();
					//				for (int k = 0;k<totalLibres;k++) {
					//					if(Long.valueOf(i)==Long.valueOf((String)listPuestosLibres.get(k))) {



					//if(i!=inicio) {
					if(listPuestosLibres.contains(String.valueOf(i))) {
						if(i!=inicio) {
							if(atras==(i-1)) {
								atras = i;
								conta ++;
								if(conta>=pCantidadPuestos) {
									break;
								}

							}
						}else {
							if(atras==(i)) {
								atras = i;
								conta ++;
								if(conta>=pCantidadPuestos) {
									break;
								}
							}
						}
						
						/*if(i!=inicio) {


							if(atras==(i-1)) {
								atras = i;
								conta ++;
								if(conta>=pCantidadPuestos) {
									break;
								}

							}else {
								atras = 0;
								conta = 0;
							}


						}
						else {
							if(pCantidadPuestos == 1 && i==inicio) {
								atras = i;
								conta ++;
								if(conta>=pCantidadPuestos) {
									break;
								}
							}
						}
							*/


					}else {
						conta = 0;
					}
					atras = i;
				}
			}
		}
		else {
			if(listPuestosLibres.size()>=pCantidadPuestos) {
				conta ++;					
			}
		}
		

		if(conta>=pCantidadPuestos) {
			System.out.println("SI HAY DISPONIBILIDAD DE PUESTOS CONSECUTIVOS EN EL BLOQUE");
Util util = new Util();
			Object resultado = util.sorteoConsecutivos(puestoslibres,codlibres, pCantidadPuestos);
			System.out.println("Resultado sorteo: "+resultado);
			/*HashMap<String,Object> hashPuestos = new HashMap<String, Object>();
			hashPuestos.put("COD_BLOQ", hash.get("COD_BLOQ"));
			hashPuestos.put("BLOQUE", hash.get("BLOQUE"));
			resultado.setBloque(gson.toJson(hashPuestos));
			resultado.setPuestos(gson.toJson(lstPuestosParam));
			resultado.setOcupados(gson.toJson(lstPuestosOcup));
			break;*/
			
		}else {
			//salata al siguiente bloque
			System.out.println("NO HAY DISPONIBILIDAD DE PUESTOS EN EL BLOQUE - SALTA AL SIGUIENTE BLOQUE - PUESTOS CONTINUOS");
			//continue;
		}
	
	}
}
