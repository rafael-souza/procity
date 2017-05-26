package br.net.proex.persistence.jpa;

import java.util.Date;
import java.util.List;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.annotation.PlcAggregationDAOIoC;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcDataAccessObject;
import com.powerlogic.jcompany.persistence.jpa.PlcQuery;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryFirstLine;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryLineAmount;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryOrderBy;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryParameter;
import com.powerlogic.jcompany.persistence.jpa.PlcQueryService;

import br.net.proex.entity.OcorrenciaEntity;
import br.net.proex.entity.TipoOcorrenciaEntity;
import br.net.proex.enumeration.StatusOcorrencia;
import br.net.proex.enumeration.TipoSecretario;
/**
 * Classe de Persistência gerada pelo assistente
 */

@PlcAggregationDAOIoC(OcorrenciaEntity.class)
@SPlcDataAccessObject
@PlcQueryService
public class OcorrenciaDAO extends AppJpaDAO  {
	
	
	@PlcQuery("querySelMinhasTarefas")
	public native List<OcorrenciaEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoOcorrencia", expression="obj1 = :tipoOcorrencia") TipoOcorrenciaEntity tipoOcorrencia,
			@PlcQueryParameter(name="dataOcorrencia", expression="obj.dataOcorrencia = :dataOcorrencia  ") Date dataOcorrencia,
			@PlcQueryParameter(name="dataConclusao", expression="obj.dataConclusao = :dataConclusao  ") Date dataConclusao,
			@PlcQueryParameter(name="endereco", expression="obj.endereco like '%' || :endereco || '%' ") String endereco,
			@PlcQueryParameter(name="statusOcorrencia", expression="obj.statusOcorrencia = :statusOcorrencia") StatusOcorrencia statusOcorrencia,
			@PlcQueryParameter(name="protocolo", expression="obj.protocolo like :protocolo || '%' ") String protocolo,
			@PlcQueryParameter(name="listaSecretaria", expression="obj1.secretariaResponsavel in (:listaSecretaria) ") List<TipoSecretario> listaSecretaria 
	);

	@PlcQuery("querySelMinhasTarefas")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoOcorrencia", expression="obj1 = :tipoOcorrencia") TipoOcorrenciaEntity tipoOcorrencia,
			@PlcQueryParameter(name="dataOcorrencia", expression="obj.dataOcorrencia = :dataOcorrencia  ") Date dataOcorrencia,
			@PlcQueryParameter(name="dataConclusao", expression="obj.dataConclusao = :dataConclusao  ") Date dataConclusao,
			@PlcQueryParameter(name="endereco", expression="obj.endereco like '%' || :endereco || '%' ") String endereco,
			@PlcQueryParameter(name="statusOcorrencia", expression="obj.statusOcorrencia = :statusOcorrencia") StatusOcorrencia statusOcorrencia,
			@PlcQueryParameter(name="protocolo", expression="obj.protocolo like :protocolo || '%' ") String protocolo,
			@PlcQueryParameter(name="listaSecretaria", expression="obj1.secretariaResponsavel in (:listaSecretaria) ") List<TipoSecretario> listaSecretaria
	);		

	@PlcQuery("querySel")
	public native List<OcorrenciaEntity> findList(
			PlcBaseContextVO context,
			@PlcQueryOrderBy String dynamicOrderByPlc,
			@PlcQueryFirstLine Integer primeiraLinhaPlc, 
			@PlcQueryLineAmount Integer numeroLinhasPlc,		   
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoOcorrencia", expression="obj1 = :tipoOcorrencia") TipoOcorrenciaEntity tipoOcorrencia,
			@PlcQueryParameter(name="dataOcorrencia", expression="obj.dataOcorrencia = :dataOcorrencia  ") Date dataOcorrencia,
			@PlcQueryParameter(name="dataConclusao", expression="obj.dataConclusao = :dataConclusao  ") Date dataConclusao,
			@PlcQueryParameter(name="endereco", expression="obj.endereco like '%' || :endereco || '%' ") String endereco,
			@PlcQueryParameter(name="statusOcorrencia", expression="obj.statusOcorrencia = :statusOcorrencia") StatusOcorrencia statusOcorrencia,
			@PlcQueryParameter(name="protocolo", expression="obj.protocolo like :protocolo || '%' ") String protocolo
	);

	@PlcQuery("querySel")
	public native Long findCount(
			PlcBaseContextVO context,
			
			@PlcQueryParameter(name="id", expression="obj.id = :id") Long id,
			@PlcQueryParameter(name="tipoOcorrencia", expression="obj1 = :tipoOcorrencia") TipoOcorrenciaEntity tipoOcorrencia,
			@PlcQueryParameter(name="dataOcorrencia", expression="obj.dataOcorrencia = :dataOcorrencia  ") Date dataOcorrencia,
			@PlcQueryParameter(name="dataConclusao", expression="obj.dataConclusao = :dataConclusao  ") Date dataConclusao,
			@PlcQueryParameter(name="endereco", expression="obj.endereco like '%' || :endereco || '%' ") String endereco,
			@PlcQueryParameter(name="statusOcorrencia", expression="obj.statusOcorrencia = :statusOcorrencia") StatusOcorrencia statusOcorrencia,
			@PlcQueryParameter(name="protocolo", expression="obj.protocolo like :protocolo || '%' ") String protocolo
	);
		
	
}
