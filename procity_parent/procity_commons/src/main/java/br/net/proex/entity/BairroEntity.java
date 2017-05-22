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
@Table(name="bairro")
@SequenceGenerator(name="se_bairro", sequenceName="se_bairro")
@Access(AccessType.FIELD)
@Audited
@NamedQueries({
	@NamedQuery(name="BairroEntity.queryMan", query="from BairroEntity"),
	@NamedQuery(name="BairroEntity.querySel", query="select id as id, nome as nome from BairroEntity order by nome asc"),
	@NamedQuery(name="BairroEntity.querySelLookup", query="select id as id, nome as nome from BairroEntity where id = ? order by id asc")
})
public class BairroEntity extends Bairro {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public BairroEntity() {
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
		Bairro other = (Bairro) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
