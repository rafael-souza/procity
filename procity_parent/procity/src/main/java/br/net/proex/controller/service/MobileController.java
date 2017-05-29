package br.net.proex.controller.service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.QueryParam;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;

import com.powerlogic.jcompany.commons.PlcException;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.facade.IPlcFacade;
import com.powerlogic.jcompany.commons.util.metamodel.PlcMetamodelUtil;
import com.powerlogic.jcompany.controller.jsf.util.PlcCreateContextUtil;
import com.powerlogic.jcompany.controller.rest.api.qualifiers.QPlcControllerName;
import com.powerlogic.jcompany.controller.rest.api.qualifiers.QPlcControllerQualifier;
import com.powerlogic.jcompany.controller.rest.api.stereotypes.SPlcController;
import com.powerlogic.jcompany.controller.rest.controllers.PlcBaseDynamicController;
import com.powerlogic.jcompany.controller.util.PlcBeanPopulateUtil;

import br.net.proex.entity.PessoaEntity;

/**
 * Atende a /rest/mobile.<caso_de_uso>
 */
@SPlcController
@QPlcControllerName("mobile")
@QPlcControllerQualifier("*")
public class MobileController<E, I> extends PlcBaseDynamicController<E, I> {
	
	/** atributo meta model util */
	@Inject @QPlcDefault 
	protected PlcMetamodelUtil metamodelUtil;		
		
	/**  atributo bena populate util */
	@Inject @QPlcDefault
	protected PlcBeanPopulateUtil beanPopulateUtil;

	/** atributo context monta util */
	@Inject @QPlcDefault
	private PlcCreateContextUtil contextMontaUtil;
	
	/** atributo log  */
	@Inject
	protected transient Logger log;
	
	/** atributo request */
	private HttpServletRequest request;

	/** atributo token  */
	private String token;
	

	//cria instancia do propertyUtilsBean para evitar diversas chamadas a metodo syncronized
	private static PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();
	
	/**
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	/**
	 * 
	 */
	protected void retrieveBefore(I identificadorEntidade) {
		System.out.println(identificadorEntidade);		
	};
	
	/**
	 * 
	 */
	@Override
	protected void updateBefore() {
	
		super.updateBefore();
	}
	
	/**
	 * 
	 */
	@Override
	protected void updateAfter() {		
		log.info(">>>>>>>>>>>>>>> Vai atualizar os produtos com estoque 0");		
	}
	
	/**
	 * 
	 * @param request
	 */
	@Inject
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 
	 * @param token
	 */
	@Inject
	public void setToken(@QueryParam("token") String token) {
		this.token = token;
	}
	
	/**
	 * 
	 */
	@Override
	protected void insertBefore() {				
//		// verificando se está inserindo uma lista de dados
//		if (getEntityCollection() != null && !getEntityCollection().isEmpty()) {
//			// percorrendo a lista para ajustar as fotos
//			for (E obj : getEntityCollection()){
//				// verificando se o obj é uma instancia de inscrição imobiliaria
//				if (obj instanceof MobInscricaoImobiliariaEntity){
//					MobInscricaoImobiliariaEntity inscricaoImobiliaria = (MobInscricaoImobiliariaEntity) obj;
//					// ajustando a foto fachada
//					ajustaFotoFachada(inscricaoImobiliaria);
//					// ajusta Foto adicional 1
//					ajustaFotoAdicional1(inscricaoImobiliaria);
//					// ajusta Foto adicional 2
//					ajustaFotoAdicional2(inscricaoImobiliaria);									
//				}										
//			}
//		// verificando se esta inserindo apenas um objeto 	
//		}  else if (getEntity() instanceof MobInscricaoImobiliariaEntity) {
//			MobInscricaoImobiliariaEntity inscricaoImobiliaria = (MobInscricaoImobiliariaEntity) getEntity();			
//			// ajustando a foto fachada
//			ajustaFotoFachada(inscricaoImobiliaria);
//			// ajusta Foto adicional 1
//			ajustaFotoAdicional1(inscricaoImobiliaria);
//			// ajusta Foto adicional 2
//			ajustaFotoAdicional2(inscricaoImobiliaria);
//																
//		}
	}
	
	
	
	/**
	 * 
	 */
	@Override
	protected void insertAfter() {		
		log.info(">>>>>>>>>>>>>>> Vai atualizar os produtos com estoque 0");		
	}
	
	/**
	 * 
	 */
	@Override
	public void retrieveCollection() {
		try {

			// A entidade é utilizada como filtro.
			if (getEntity() == null) {
				E instancia = getEntityType().newInstance();
				beanPopulateUtil.transferMapToBean(request.getParameterMap(), instancia);
				
				setEntity(instancia);
			}
			
			// verificando se é para buscar os usuários
			if (getEntity() instanceof PessoaEntity){
				// setando o usuario que está sincronizado os dados
				((PessoaEntity) this.getEntity()).setEmail(getUsuarioToken());							
			}
			
			// recupera coleção sem paginação
			retrieveCollectionBefore();			
			IPlcFacade facade = getFacade();
						
			try {
				if (getCollectionPage() != null && getCollectionPageRecords() != null) {
					retrievePagedCollection();
				} else {					
					//define quantidade de linhas para retornar na busca ao BD - 0 ilimitado
					setEntityCollection(facade.findList(getContext(), getEntity(), getCollectionOrder(), -1, 0));																												
				}
			} catch (Exception e) {
				handleExceptions(e);
			}
			retrieveCollectionAfter();
		} catch(PlcException plcE){
			throw plcE;	
		} catch (Exception e) {
			throw new PlcException("MobileController", "retrieveCollection", e, null, "");
		}
	}

	/**
	 * Retorna o usuário que está acessando o sistema
	 * @return
	 */
	private String getUsuarioToken() {
		return token.substring(token.indexOf("(") + 1, token.lastIndexOf(")"));
	}

	/**
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}
	

}
