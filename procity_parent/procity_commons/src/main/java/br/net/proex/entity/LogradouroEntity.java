package br.net.proex.entity;


import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import javax.persistence.SequenceGenerator;
import javax.persistence.AccessType;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcEntity;
import javax.persistence.Access;
/**
 * Classe Concreta gerada a partir do assistente
 */
@SPlcEntity
@Entity
@Table(name="logradouro")
@SequenceGenerator(name="se_logradouro", sequenceName="se_logradouro")
@Access(AccessType.FIELD)
@Audited
@NamedQueries({
	@NamedQuery(name="LogradouroEntity.queryMan", query="from LogradouroEntity"),
	@NamedQuery(name="LogradouroEntity.querySel", query="select id as id, nome as nome, cep as cep from LogradouroEntity order by nome asc"),
	@NamedQuery(name="LogradouroEntity.querySelLookup", query="select id as id, nome as nome from LogradouroEntity where id = ? order by id asc")
})
public class LogradouroEntity extends Logradouro {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public LogradouroEntity() {
    }
    
	@Override
	public String toString() {
		return getNome();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Logradouro other = (Logradouro) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
