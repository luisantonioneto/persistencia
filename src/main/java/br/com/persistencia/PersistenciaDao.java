package br.com.persistencia;

import java.util.List;

import javax.persistence.EntityManager;

public interface PersistenciaDao<E> {

	public E salvar(E entity) throws Exception;
	
	public E atualizar(E entity) throws Exception;
	
	public List<E> listar();
	
	public List<E> listarPorNome() throws Exception;
	
	public E buscarPorId(int id);
	
	public void excluir(E entity) throws Exception;
	
	public EntityManager getEntityManager();
}
