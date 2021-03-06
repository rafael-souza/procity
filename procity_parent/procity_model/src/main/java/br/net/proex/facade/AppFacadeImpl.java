package br.net.proex.facade;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import com.powerlogic.jcompany.commons.PlcBaseContextVO;
import com.powerlogic.jcompany.commons.config.qualifiers.QPlcDefault;
import com.powerlogic.jcompany.commons.config.stereotypes.SPlcFacade;
import com.powerlogic.jcompany.facade.PlcFacadeImpl;

import br.net.proex.entity.DenunciaEntity;
import br.net.proex.entity.OcorrenciaEntity;
import br.net.proex.entity.PessoaEntity;
import br.net.proex.entity.PrefeituraEntity;
import br.net.proex.entity.SugestaoEntity;
import br.net.proex.entity.seg.SegMenuEntity;
import br.net.proex.entity.seg.SegPerfilEntity;
import br.net.proex.entity.seg.SegUsuarioEntity;
import br.net.proex.persistence.jpa.DenunciaDAO;
import br.net.proex.persistence.jpa.OcorrenciaDAO;
import br.net.proex.persistence.jpa.PessoaDAO;
import br.net.proex.persistence.jpa.PrefeituraDAO;
import br.net.proex.persistence.jpa.SegMenuDAO;
import br.net.proex.persistence.jpa.SegPerfilDAO;
import br.net.proex.persistence.jpa.SegUsuarioDAO;
import br.net.proex.persistence.jpa.SugestaoDAO;

@QPlcDefault
@SPlcFacade
public class AppFacadeImpl extends PlcFacadeImpl implements IAppFacade{

	@Inject 
	private SegUsuarioDAO usuarioDAO;

	@Inject 
	private SegPerfilDAO perfilDAO;
	
	@Inject 
	private SegMenuDAO menuDAO;
	
	@Inject 
	private PrefeituraDAO prefeituraDAO;	
	
	@Inject 
	private PessoaDAO pessoaDAO;
	
	@Inject 
	private OcorrenciaDAO ocorrenciaDAO;
	
	@Inject 
	private SugestaoDAO sugestaoDAO;
	
	@Inject
	private DenunciaDAO denunciaDAO;
	
	

	@Override
	public SegUsuarioEntity findUsuarioByLogin(PlcBaseContextVO context, String login) {
		return usuarioDAO.findByLogin(context, login);
	}

	@Override
	public SegPerfilEntity findByPerfil(PlcBaseContextVO context, SegPerfilEntity perfil) {		
		return (SegPerfilEntity) perfilDAO.findById(context,	SegPerfilEntity.class, perfil.getId());

	}

	@Override
	public List<SegMenuEntity> findMenus(PlcBaseContextVO context) {
		return menuDAO.findList(context, new SegMenuEntity(), "", 0, 0);
	}

	@Override
	public SegUsuarioEntity recuperaUsuario(PlcBaseContextVO context, SegUsuarioEntity usuario) { 
		return (SegUsuarioEntity)usuarioDAO.findById(context, SegUsuarioEntity.class, usuario.getId());
	}

	@Override
	public PrefeituraEntity findPrefeituraById(PlcBaseContextVO context, Long id) {		
		return (PrefeituraEntity)prefeituraDAO.findById(context, PrefeituraEntity.class, id);
	}

	@Override
	public PessoaEntity findPessoaByEmail(PlcBaseContextVO context, String email) {
		return pessoaDAO.findPessoaByEmail(context, email);
	}

	@Override
	public List<OcorrenciaEntity> findOcorrenciaPorPessoa(PlcBaseContextVO context, Long idPessoa) {
		return ocorrenciaDAO.findOcorrenciaPorPessoa(context,idPessoa);
	}

	/**
	 * 
	 */
	@Override
	public OcorrenciaEntity findOcorrenciaById(PlcBaseContextVO context, Long id) {
		return (OcorrenciaEntity)ocorrenciaDAO.findById(context, OcorrenciaEntity.class, id);
	}

	@Override
	public List<SugestaoEntity> buscarSugestaoPorProtocolo(PlcBaseContextVO context, String protocolo) {
		return sugestaoDAO.buscarSugestaoPorProtocolo(context, protocolo);
	}

	@Override
	public List<DenunciaEntity> buscarDenunciaPorProtocolo(PlcBaseContextVO context, String protocolo) {
		return denunciaDAO.buscarDenunciaPorProtocolo(context, protocolo);
	}
	
}
