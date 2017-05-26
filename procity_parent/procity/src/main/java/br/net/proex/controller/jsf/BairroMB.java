package br.net.proex.controller.jsf;

import javax.enterprise.inject.Produces;
import javax.inject.Named;


import br.net.proex.entity.BairroEntity;

import com.powerlogic.jcompany.commons.annotation.PlcUriIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcMB;
import com.powerlogic.jcompany.controller.jsf.annotations.PlcHandleException;
import com.powerlogic.jcompany.config.collaboration.FormPattern;

import com.powerlogic.jcompany.config.collaboration.PlcConfigFormLayout;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm;
import com.powerlogic.jcompany.config.collaboration.PlcConfigForm.ExclusionMode;



 
import com.powerlogic.jcompany.config.aggregation.PlcConfigAggregation;

@PlcConfigAggregation(entity = br.net.proex.entity.BairroEntity.class)
@PlcConfigForm (
	formPattern=FormPattern.Man,
	formLayout = @PlcConfigFormLayout(dirBase="/WEB-INF/fcls/bairro")
)


/**
 * Classe de Controle gerada pelo assistente
 */
 
@SPlcMB
@PlcUriIoC("bairro")
@PlcHandleException
public class BairroMB extends AppMB  {

	private static final long serialVersionUID = 1L;
	
	
     		
	/**
	* Entidade da ação injetado pela CDI
	*/
	@Produces  @Named("bairro")
	public BairroEntity createEntityPlc() {
        if (this.entityPlc==null) {
              this.entityPlc = new BairroEntity();
              this.newEntity();
        }
        return (BairroEntity)this.entityPlc;     	
	}
		
}
