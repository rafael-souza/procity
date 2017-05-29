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
@Table(name="pessoa")
@SequenceGenerator(name="se_pessoa", sequenceName="se_pessoa")
@Access(AccessType.FIELD)
@Audited
@NamedQueries({
	@NamedQuery(name="PessoaEntity.queryMan", query="from PessoaEntity"),
	@NamedQuery(name="PessoaEntity.querySel", query="select obj.id as id, obj.nome as nome,  obj.email as email, obj.celular as celular, obj.senha as senha from PessoaEntity obj order by obj.nome asc"),
	@NamedQuery(name="PessoaEntity.querySelLookup", query="select id as id, nome as nome from PessoaEntity where id = ? order by id asc")
})
public class PessoaEntity extends Pessoa {

	private static final long serialVersionUID = 1L;
 	
    /*
     * Construtor padrao
     */
    public PessoaEntity() {
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
		Pessoa other = (Pessoa) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

}
