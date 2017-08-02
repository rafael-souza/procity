package br.net.proex.entity;


import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;

import br.net.proex.enumeration.StatusOcorrencia;
import br.net.proex.enumeration.TipoSecretario;
/**
 * Classe Concreta gerada a partir do assistente
 */
@SPlcEntity
@Entity
@Table(name="ocorrencia")
@SequenceGenerator(name="se_ocorrencia", sequenceName="se_ocorrencia")
@Access(AccessType.FIELD)
@Audited
@NamedQueries({
	@NamedQuery(name="OcorrenciaEntity.queryMan", query="from OcorrenciaEntity"),	
	@NamedQuery(name="OcorrenciaEntity.querySel", 
		query="select obj.id as id, "
			+ "obj1.id as tipoOcorrencia_id , "
			+ "obj1.descricao as tipoOcorrencia_descricao, "
			+ "obj.dataOcorrencia as dataOcorrencia, "
			+ "obj.dataConclusao as dataConclusao, "
			+ "obj.observacaoConclusao as observacaoConclusao, "
			+ "obj.observacao as observacao, "
			+ "obj.endereco as endereco, "
			+ "obj.protocolo as protocolo, "
			+ "obj.statusOcorrencia as statusOcorrencia, "
			+ "obj2.id as pessoa_id , "
			+ "obj2.nome as pessoa_nome, "
			+ "obj2.email as pessoa_email, "
			+ "obj.responsavelConclusao as responsavelConclusao, "
			+ "obj.latitude as latitude, "
			+ "obj.longitude as longitude, "
			+ "obj3.id as fotoOcorrencia_id "
			+ "from "
			+ "OcorrenciaEntity obj "
			+ "left outer join obj.tipoOcorrencia as obj1 "
			+ "left outer join obj.pessoa as obj2 "
			+ "left outer join obj.fotoOcorrencia as obj3 "
			+ "order by obj.id asc"),
	@NamedQuery(name="OcorrenciaEntity.queryMinhasTarefas", 
	query="select obj.id as id, "
		+ "obj1.id as tipoOcorrencia_id , "
		+ "obj1.descricao as tipoOcorrencia_descricao, "
		+ "obj1.secretariaResponsavel as tipoOcorrencia_secretariaResponsavel, "
		+ "obj.dataOcorrencia as dataOcorrencia, "
		+ "obj.dataConclusao as dataConclusao, "
		+ "obj.endereco as endereco, "
		+ "obj.protocolo as protocolo, "
		+ "obj.statusOcorrencia as statusOcorrencia, "
		+ "obj2.id as pessoa_id , "
		+ "obj2.nome as pessoa_nome, "
		+ "obj2.email as pessoa_email, "
		+ "obj.responsavelConclusao as responsavelConclusao, "
		+ "obj.latitude as latitude, "
		+ "obj.longitude as longitude "
		+ "from "
		+ "OcorrenciaEntity obj "
		+ "left outer join obj.tipoOcorrencia as obj1 "
		+ "left outer join obj.pessoa as obj2 "
		+ "where obj.statusOcorrencia <> 'ABE' order by obj.id asc"),		
	@NamedQuery(name="OcorrenciaEntity.querySelPorPessoa", 
		query="select obj "			
			+ "from "
			+ "OcorrenciaEntity obj "
			+ "left outer join obj.tipoOcorrencia as obj1 "
			+ "left outer join obj.pessoa as obj2 "
			+ "left outer join obj.fotoOcorrencia as obj3 "
			+ "where "
			+ "obj2.id =:idPessoa "
			+ "order by obj.id asc"),	
	@NamedQuery(name="OcorrenciaEntity.querySelLookup", query="select id as id, latitude as latitude from OcorrenciaEntity where id = ? order by id asc")
})
public class OcorrenciaEntity extends Ocorrencia {

	private static final long serialVersionUID = 1L;
	
	@Transient
	private List<TipoSecretario> listaSecretaria;
	
	@Transient
	private String observacaoHistorico;
	
	@Transient
	private String textoDocumento;	
	
	@Transient
	private String descricaoTipo;
	
	@Transient
	private String conteudoBinarioFoto;
	
	@Transient
	private String fotoApp;	
	
	@Transient
	private StatusOcorrencia statusDiferenteABE;
 	
    /*
     * Construtor padrao
     */
    public OcorrenciaEntity() {
    }
	@Override
	public String toString() {
		return getDescricaoStatus();
	}
	
	/**
	 * 	
	 * @param data
	 * @return
	 */
	public String getDataFormatada() {
		try {
			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
			return dataFormatada.format(this.getDataOcorrencia());
		} catch (Exception e) {
			return null;
		}
	}	
	
	
	/**
	 * 	
	 * @param data
	 * @return
	 */
	public String getDataConclusaoFormatada() {
		try {
			SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
			return dataFormatada.format(this.getDataConclusao());
		} catch (Exception e) {
			return null;
		}
	}		
	
	/**
	 * 
	 * @param string
	 * @return
	 */
	public String getDescricaoStatus() {
		if (null != this.getStatusOcorrencia()){
			switch (this.getStatusOcorrencia()) {
			case ABE:
				return "Em Aberto";	
			case ENC:
				return "Encaminhada";					
			case ANA:
				return "Em Análise";					
			case CON:
				return "Concluída";			
			}
		}
		return "";
	}	
	
	/**
	 * 
	 * @param statusOcorrencia
	 * @return
	 */
    public String getIconMarkerByStatus(StatusOcorrencia statusOcorrencia) {
		if (null != this.getStatusOcorrencia()){
			switch (this.getStatusOcorrencia()) {
			case ABE:
				return "http://maps.google.com/mapfiles/ms/micons/red-dot.png";		
			case ENC:
				return "http://maps.google.com/mapfiles/ms/micons/blue-dot.png";					
			case ANA:
				return "http://maps.google.com/mapfiles/ms/micons/yellow-dot.png";					
			case CON:
				return "http://maps.google.com/mapfiles/ms/micons/green-dot.png";			
			}
		}
		return "";
	}	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ocorrencia other = (Ocorrencia) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	/**
	 * @return the listaSecretaria
	 */
	public List<TipoSecretario> getListaSecretaria() {
		return listaSecretaria;
	}
	/**
	 * @param listaSecretaria the listaSecretaria to set
	 */
	public void setListaSecretaria(List<TipoSecretario> listaSecretaria) {
		this.listaSecretaria = listaSecretaria;
	}
	/**
	 * @return the observacaoHistorico
	 */
	public String getObservacaoHistorico() {
		return observacaoHistorico;
	}
	/**
	 * @param observacaoHistorico the observacaoHistorico to set
	 */
	public void setObservacaoHistorico(String observacaoHistorico) {
		this.observacaoHistorico = observacaoHistorico;
	}
	/**
	 * @return the textoDocumento
	 */
	public String getTextoDocumento() {
		return textoDocumento;
	}
	/**
	 * @param textoDocumento the textoDocumento to set
	 */
	public void setTextoDocumento(String textoDocumento) {
		this.textoDocumento = textoDocumento;
	}
	/**
	 * @return the descricaoTipo
	 */
	public String getDescricaoTipo() {		
		return descricaoTipo;
	}
	/**
	 * @param descricaoTipo the descricaoTipo to set
	 */
	public void setDescricaoTipo(String descricaoTipo) {
		this.descricaoTipo = descricaoTipo;
	}
	/**
	 * @return the conteudoBinarioFoto
	 */
	public String getConteudoBinarioFoto() {
		return conteudoBinarioFoto;
	}
	/**
	 * @param conteudoBinarioFoto the conteudoBinarioFoto to set
	 */
	public void setConteudoBinarioFoto(String conteudoBinarioFoto) {
		this.conteudoBinarioFoto = conteudoBinarioFoto;
	}
	/**
	 * @return the fotoApp
	 */
	public String getFotoApp() {
		return fotoApp;
	}
	/**
	 * @param fotoApp the fotoApp to set
	 */
	public void setFotoApp(String fotoApp) {
		this.fotoApp = fotoApp;
	}
	/**
	 * @return the statusDiferenteABE
	 */
	public StatusOcorrencia getStatusDiferenteABE() {
		return statusDiferenteABE;
	}
	/**
	 * @param statusDiferenteABE the statusDiferenteABE to set
	 */
	public void setStatusDiferenteABE(StatusOcorrencia statusDiferenteABE) {
		this.statusDiferenteABE = statusDiferenteABE;
	}


}
