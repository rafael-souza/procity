package br.net.proex.controller.jsf;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;

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
import com.powerlogic.jcompany.controller.jsf.PlcEntityList;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;
import com.powerlogic.jcompany.controller.jsf.util.PlcCreateContextUtil;

import br.net.proex.entity.OcorrenciaEntity;
import br.net.proex.entity.PrefeituraEntity;
import br.net.proex.enumeration.StatusOcorrencia;
import br.net.proex.facade.IAppFacade;

@PlcConfigAggregation(
		entity = br.net.proex.entity.OcorrenciaEntity.class)
@PlcConfigForm (	
	formPattern=FormPattern.Con,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/mapaocorrencias"),
	selection = @com.powerlogic.jcompany.config.collaboration.PlcConfigSelection(
			apiQuerySel = "querySel2",
			pagination = @com.powerlogic.jcompany.config.collaboration.PlcConfigPagination(numberByPage=50))
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("mapaocorrencias")
@PlcHandleException
@SessionScoped
public class MapaOcorrenciasMB extends AppMB  {

	private static final long serialVersionUID = 1L;

	private MapModel advancedModel;
	
	private Marker marker;
	
	private String latitude;
	
	private String longitude;
	
	@Inject  @QPlcDefault
	private IAppFacade facade;	
	

	@Inject @QPlcDefault
	protected PlcCreateContextUtil contextMontaUtil;	
			
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("mapaocorrencias")
	public OcorrenciaEntity createEntityPlc() {

         if (this.entityPlc==null) {
              this.entityPlc = new OcorrenciaEntity();
              this.newEntity();
              // criando os marcadores
              alimentaMarcadoresMapa();
              
              // pegando a latitude e longitude da cidade
              PrefeituraEntity prefeitura = new PrefeituraEntity();
              prefeitura.setId(1L);
              List<PrefeituraEntity> lista = (List<PrefeituraEntity>)facade.findList(contextMontaUtil.createContextParamMinimum(), prefeitura, "", 0, 0);
              if (null != lista && lista.size() > 0){
            	  setLatitude(lista.get(0).getLatitude());
            	  setLongitude(lista.get(0).getLongitude());
              }                                         
         }

        return (OcorrenciaEntity) this.entityPlc;   
        
	}

	/**
	 * Lista de entidades da ação injetado pela CDI
	*/
	@Produces  @Named("mapaocorrenciasLista")
	public PlcEntityList createEntityListPlc() {
		
		if (this.entityListPlc==null) {
			this.entityListPlc = new PlcEntityList();
			this.newObjectList();
		}
		return this.entityListPlc;
	}		
	
	/**
	 * 
	 */
	@Override
	public String search() {		
		String retorno = super.search();
		alimentaMarcadoresMapa();		
		return retorno;
	}
	
	/**
	 * 
	 */
	private void alimentaMarcadoresMapa() {
		if (null != this.entityListPlc && null != this.entityListPlc.getItensPlc()){
			// recuperando a lista com os resultados
			List<OcorrenciaEntity> listaOcorrencia = (List<OcorrenciaEntity>)(Object) this.entityListPlc.getItensPlc();
			// verificando se tem algum resultado
			if (null != listaOcorrencia && listaOcorrencia.size() > 0){
				// ajustando o marcador no mapa
				advancedModel = new DefaultMapModel();
				
				// percorre a lista para a montagem da lista dos pontos do mapa
				for (OcorrenciaEntity ocorrencia : listaOcorrencia){
					   //Shared coordinates
			        LatLng coord = new LatLng(Double.parseDouble(ocorrencia.getLatitude()), 
			        		Double.parseDouble(ocorrencia.getLongitude()));
			          
			        //Icons and Data
			        Marker marker = new Marker(coord, "Cód: "+ ocorrencia.getId() + " - Tipo: " + ocorrencia.getTipoOcorrencia()
	        			+ " - Data: " + ocorrencia.getDataFormatada()
	        			+ " - Status: " + ocorrencia.getDescricaoStatus());
			        marker.setIcon(ocorrencia.getIconMarkerByStatus(ocorrencia.getStatusOcorrencia()));
			        advancedModel.addOverlay(marker);			        		
				}
			}
		}		
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}	
	
}
