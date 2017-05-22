/* Jaguar-jCompany Developer Suite. Powerlogic 2010-2014. Please read licensing information or contact Powerlogic 
 * for more information or contribute with this project: suporte@powerlogic.com.br - www.powerlogic.com.br        */ 
package br.net.proex.commons;

import com.powerlogic.jcompany.commons.PlcConstants;

/**
 * Implementar aqui constantes específicas da Aplicação
 * 
 */
public interface AppConstants extends PlcConstants {
    
    String NOME_CONSTANTE = "valorConstante";	
    
    String AUD_CASO_DE_USO = "casoDeUso";
    
    String AUD_ACAO_REALIZADA = "acaoRealizada";    
    
    String formatoUSA = "yyyy-MM-dd";
    
	/** atributo que armazena a lista de id´s na seção para navegação */
	String SESSION_LIST_ID = "listaId";
	
	/** atributo para pegar o id por reflexão */
	String ID = "id";	
    	
}
