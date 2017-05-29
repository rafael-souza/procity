package br.net.proex.controller.service;

import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;

import com.powerlogic.jcompany.commons.PlcException;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefaultLiteral;
import com.powerlogic.jcompany.commons.util.cdi.PlcCDIUtil;
import com.powerlogic.jcompany.controller.rest.api.qualifiers.QPlcControllerName;
import com.powerlogic.jcompany.controller.rest.api.qualifiers.QPlcConversorMediaType;
import com.powerlogic.jcompany.controller.rest.api.qualifiers.QPlcConversorToController;
import com.powerlogic.jcompany.controller.rest.api.stereotypes.SPlcConversor;
import com.powerlogic.jcompany.controller.rest.conversors.IPlcRestRendererUtil;

import br.net.proex.commons.AppBeanMessages;

@SPlcConversor
@QPlcControllerName("mobile")
@QPlcConversorMediaType( { "application/json", "*/*", "charset=utf8"})
@QPlcConversorToController(type=MobileController.class)
public class MobileConversor<C> extends MobileJsonConversor<C> {
	
	@Inject
	@QPlcDefault
	@QPlcConversorMediaType("application.mob/json")
	private IPlcRestRendererUtil restRendererUtil;
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void writeEntityCollection(C _container, java.io.OutputStream outputStream) {
		
		try {
			MobileController container = (MobileController)_container;

			HashMap<String, Object> response = new HashMap<String, Object>();
			response.put(getUcName(), container.getEntityCollection());
			response.put("messages", getMessages());
			Object plcSelProps = container.getSelectedProperties();
			if (plcSelProps == null) {
				plcSelProps = getSelectedProperties();
			}
			response.put("plcSelProps", plcSelProps);
			writeEntityCollectionPagination(_container, response);
			writeResponse(outputStream,response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void readEntity(C _container, java.io.InputStream inputStream) {
		
		if (validaParametroToken(_container)) {			
			super.readEntity(_container, inputStream);		
		}		
		
	};
	
	/**
	 * 
	 */
	public void readEntityCollection(C _container, java.io.InputStream inputStream) {		
		if (validaParametroToken(_container)) {
			super.readEntityCollection(_container, inputStream);
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	private String[] getSelectedProperties() {
		
		String[] propriedades = null;
		
		if (this.getUcName().equals("pessoa")) {
			propriedades = "id,nome,email,senha".split(",");
		} else if (this.getUcName().equals("ocorrencia")) {
			propriedades = "id,pessoa,tipoOcorrencia,dataOcorrencia,dataConclusao,latitude,longitude,statusOcorrencia,fotoOcorrencia,observacao,protocolo,observacaoConclusao".split(",");
		} 
		
		return propriedades;
	}
	
	
	/**
	 * 
	 * @param _container
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Boolean validaParametroToken(C _container) {
		MobileController container = (MobileController)_container;
		
		MobUtil util = PlcCDIUtil.getInstance().getInstanceByType(MobUtil.class, QPlcDefaultLiteral.INSTANCE);
		
		if (!util.validaToken(container.getToken())) {
			throw new PlcException("Token Inválido");
		}
		
		return true;
	}

}
