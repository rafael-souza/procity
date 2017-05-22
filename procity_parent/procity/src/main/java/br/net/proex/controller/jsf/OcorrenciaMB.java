package br.net.proex.controller.jsf;

import javax.enterprise.inject.Produces;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;
import com.powerlogic.jcompany.config.collaboration.FormPattern;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;

import br.net.proex.entity.FotoOcorrencia;
import br.net.proex.entity.OcorrenciaEntity;
import br.net.proex.facade.IAppFacade;

@PlcConfigAggregation(entity = br.net.proex.entity.OcorrenciaEntity.class)
@PlcConfigForm (
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/ocorrencia"))


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("ocorrencia")
@PlcHandleException
@SessionScoped
public class OcorrenciaMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	@Inject  @QPlcDefault
	private IAppFacade facade;
		
	private MapModel advancedModel;
	
	private Marker marker;
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("ocorrencia")
	public OcorrenciaEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new OcorrenciaEntity();
              this.newEntity();                           
        }
        return (OcorrenciaEntity)this.entityPlc;     	
	}
	
	
	@Override
	public String edit() {	
		String retorno = super.edit();
		
		OcorrenciaEntity ocorrencia = (OcorrenciaEntity)this.entityPlc;
		
		if (null != ocorrencia.getFotoOcorrencia()){
			ocorrencia.setFotoOcorrencia((FotoOcorrencia) facade.downloadFile(contextMontaUtil.createContextParamMinimum(),
					FotoOcorrencia.class, ocorrencia.getFotoOcorrencia().getId()));
		}
		
		// ajustando o marcador no mapa
		advancedModel = new DefaultMapModel();
        
        //Shared coordinates
        LatLng coord = new LatLng(Double.parseDouble(ocorrencia.getLatitude()), 
        		Double.parseDouble(ocorrencia.getLongitude()));
          
        //Icons and Data
        advancedModel.addOverlay(new Marker(coord, "Tipo: " + ocorrencia.getTipoOcorrencia()
        		+ " - Data: " + ocorrencia.getDataFormatada()
        		+ " - Status: " + ocorrencia.getDescricaoStatus()));
        		
				
		return retorno;
	}

	
    public MapModel getAdvancedModel() {
        return advancedModel;
    }
      
    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }
      
    public Marker getMarker() {
        return marker;
    }
	
}
