package br.net.proex.entity;


import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import javax.persistence.SequenceGenerator;
import javax.persistence.AccessType;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;

import br.net.proex.enumeration.StatusOcorrencia;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Access;
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
	@NamedQuery(name="OcorrenciaEntity.querySel2", query="select obj.id as id, obj1.id as tipoOcorrencia_id , obj1.descricao as tipoOcorrencia_descricao, obj.dataOcorrencia as dataOcorrencia, obj.dataConclusao as dataConclusao, obj.statusOcorrencia as statusOcorrencia, obj2.id as pessoa_id , obj2.nome as pessoa_nome, obj.responsavelConclusao as responsavelConclusao, obj.latitude as latitude, obj.longitude as longitude from OcorrenciaEntity obj left outer join obj.tipoOcorrencia as obj1 left outer join obj.pessoa as obj2 order by obj.responsavelConclusao asc"),
	@NamedQuery(name="OcorrenciaEntity.queryMan", query="from OcorrenciaEntity"),
	@NamedQuery(name="OcorrenciaEntity.querySel", query="select obj.id as id, obj1.id as pessoa_id , obj1.nome as pessoa_nome, obj2.id as tipoOcorrencia_id , obj2.descricao as tipoOcorrencia_descricao, obj.dataOcorrencia as dataOcorrencia, obj.statusOcorrencia as statusOcorrencia from OcorrenciaEntity obj left outer join obj.pessoa as obj1 left outer join obj.tipoOcorrencia as obj2 order by obj.id asc"),
	@NamedQuery(name="OcorrenciaEntity.querySelLookup", query="select id as id, latitude as latitude from OcorrenciaEntity where id = ? order by id asc")
})
public class OcorrenciaEntity extends Ocorrencia {

	private static final long serialVersionUID = 1L;
 	
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
	 * @param string
	 * @return
	 */
	public String getDescricaoStatus() {
		if (null != this.getStatusOcorrencia()){
			switch (this.getStatusOcorrencia()) {
			case ABE:
				return "Em Aberto";		
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

}
