package br.net.proex.controller.service;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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

import br.net.proex.entity.FotoConteudoOcorrencia;
import br.net.proex.entity.FotoOcorrencia;
import br.net.proex.entity.OcorrenciaEntity;
import br.net.proex.entity.PessoaEntity;
import br.net.proex.facade.IAppFacade;

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
	
	@Inject @QPlcDefault
	private IAppFacade facade;
	

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
		if (getEntity() instanceof OcorrenciaEntity) {
			OcorrenciaEntity ocorrencia = (OcorrenciaEntity) getEntity();			
			// verificando se as fotos foram tiradas
			if(null != ocorrencia.getFotoApp()){
				try {
					String base64Image = ocorrencia.getFotoApp().replace(" ", "+").split(",")[1];
					byte[] fotoFachada = Base64.getDecoder().decode(base64Image.getBytes());
					//byte[] fotoFachada = Base64.decode(base64Image);														
					// criand o objeto de conteudo
					FotoConteudoOcorrencia fotoConteudo = new FotoConteudoOcorrencia();
					fotoConteudo.setBinaryContent(fotoFachada);
					// criando o objeto da foto
					FotoOcorrencia foto = new FotoOcorrencia();							
					foto.setBinaryContent(fotoConteudo);
					foto.setNome("");
					foto.setType("image/jpg");	
					foto.setDataUltAlteracao(new Date());
					foto.setLength(fotoConteudo.getBinaryContent().length);					
					
					ocorrencia.setFotoOcorrencia(foto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
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
			
			// quando e para buscar por ocorrencias faz o filtro por pessoas
			if (getEntity() instanceof OcorrenciaEntity){
				PessoaEntity pessoa = facade.findPessoaByEmail(contextMontaUtil.createContextParamMinimum(), getUsuarioToken());
				((OcorrenciaEntity) this.getEntity()).setPessoa(pessoa);
			}
			
			// recupera coleção sem paginação
			retrieveCollectionBefore();			
			IPlcFacade facade = getFacade();
						
			try {
				if (getCollectionPage() != null && getCollectionPageRecords() != null) {
					retrievePagedCollection();
				} else {					
					
					if (getEntity() instanceof OcorrenciaEntity){
						//define quantidade de linhas para retornar na busca ao BD - 0 ilimitado
						setEntityCollection(ajustarFoto(facade.findList(getContext(), getEntity(), getCollectionOrder(), -1, 0)));
					} else {
						//define quantidade de linhas para retornar na busca ao BD - 0 ilimitado
						setEntityCollection(facade.findList(getContext(), getEntity(), getCollectionOrder(), -1, 0));																												
					}
					
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

	private Collection<E> ajustarFoto(Collection listaOcorrencias) {
		List<OcorrenciaEntity> lista = (List<OcorrenciaEntity>) listaOcorrencias;
		// verificando se a lista possui algum registro
		if (null != lista && lista.size() > 0){
			// percorrendo a lista para ajustar as atividades
			for (OcorrenciaEntity ocorrencia : lista){
				if (null != ocorrencia.getFotoOcorrencia() && null != ocorrencia.getFotoOcorrencia().getId()){
					ocorrencia.setFotoOcorrencia((FotoOcorrencia) facade.downloadFile(contextMontaUtil.createContextParamMinimum(),
							FotoOcorrencia.class, ocorrencia.getFotoOcorrencia().getId()));
															
					StringBuilder sb = new StringBuilder();
					sb.append("data:image/png;base64,");
					sb.append(Base64.getEncoder().encodeToString(ocorrencia.getFotoOcorrencia().getBinaryContent().getBinaryContent()));
					ocorrencia.setConteudoBinarioFoto(sb.toString());
										
				}
				
				if (null != ocorrencia.getTipoOcorrencia() && null != ocorrencia.getTipoOcorrencia().getDescricao()){
					ocorrencia.setDescricaoTipo(ocorrencia.getTipoOcorrencia().getDescricao());
				}

			}
		}
				
		return (Collection<E>)lista;
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
