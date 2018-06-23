package operacion;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clase.Pedido;
import util.Fecha;

/** 
 * Resolvedor, Agenda nuevos eventos en el futuro y permite ver los proximos eventos pendientes.
 */
public class RecordarEventos implements Operacion {
	
	private Operacion siguiente;


	public RecordarEventos() {
	}

/*	public String intentarResponder(Pedido pedido) {
		if (consulta(mensaje)) {
			if ((mensaje.matches(".*" + "agrega" + ".*"))) // identifico que el pedido es agregar un evento
			{
				mensaje = mensaje.substring(mensaje.indexOf(":") + 1);
				return agregarEvento(mensaje.substring(mensaje.indexOf(":") + 1));// solo le pasa la fecha del evento

			} else if (mensaje.matches(".*" + "proximo" + ".*")) {
				return proximoEvento();
			}
		}

		return null;

	}
*/
	/*
	private String proximoEvento() {
		Evento e = new Evento();
		e = e.proximoEvento();
		if(e!=null)
			return e.toString();
		else
			return "No hay pr�ximos eventos";
	}
	*/

	/*
	private String agregarEvento(Pedido pedido) {
		Evento e = new Evento();
		e.setFecha( new Fecha(evento));
		e.setDescripcion("Evento del d�a " + evento);
		if(e.crearEvento(e))
			return "Evento agregado";
		else
			return "No se pudo agregar el evento";
	}
	*/

	@Override
	public void siguiente(Operacion siguiente) {
		this.siguiente = siguiente;
	}

	@Override
	public String calcular(Pedido pedido) {
		String regex_recordar = ".*(?:agrega|recorda|agenda).*:(.*) (\\d+)(?: de |\\/)([a-zA-Z]*|\\d+)(?: de |\\/)(\\d+)";
		Pattern pattern_recordar = Pattern.compile(regex_recordar, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_recordar = pattern_recordar.matcher(pedido.getMensaje());
		while(matcher_recordar.find()) {
			if(matcher_recordar.matches()) {
				String desc = matcher_recordar.group(1).trim();
				Fecha fecha = null;
				try {
					fecha = new Fecha(matcher_recordar.group(2)+"/"+matcher_recordar.group(3)+"/"+matcher_recordar.group(4),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e) {
					return siguiente.calcular(pedido);
				}
				
				Evento e = new Evento(fecha, desc);
				if(e.guardarEvento()) {
					return pedido.getNameUsuario() + " Evento agregado";
				} else {
					return pedido.getNameUsuario() + " No se pudo agregar el evento";
				}
					
			}
		}
		
		String regex_proximo = ".*proximo.*evento.*";
		Pattern pattern_proximo = Pattern.compile(regex_proximo, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_proximo = pattern_proximo.matcher(pedido.getMensaje());
		while(matcher_proximo.find()) {
			if(matcher_proximo.matches()) {
				Evento e = new Evento().proximoEvento();
				if(e != null) {
					/* Calcular la diferencia
					Fecha actual = new Fecha();
										
					System.out.println(actual.toString());
					Fecha fec = e.getFecha();
					fec.restaFechas_Dias(actual);
					*/
					return pedido.getNameUsuario() + " El pr�ximo evento es: " + e.toString().trim();
				}
				else {
					return pedido.getNameUsuario() + " No ten�s eventos";
				}
			}

		}
		
		return siguiente.calcular(pedido);
	}
}
