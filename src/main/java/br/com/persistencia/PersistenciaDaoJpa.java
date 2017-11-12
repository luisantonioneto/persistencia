package br.com.persistencia;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

public class PersistenciaDaoJpa<E> implements PersistenciaDao<E> {

	private Class<E> entity;
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public PersistenciaDaoJpa() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) type;
		entity = (Class<E>) pt.getActualTypeArguments()[0];
	}
	
	public final void setEntity(Class<E> entity) {
		this.entity = entity;
	}

	@Transactional
	public E salvar(E entity) throws Exception {
		this.em.persist(entity);
		return entity;
	}
	
	@Transactional
	public E atualizar(E entity) throws Exception{
		this.em.merge(entity);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<E> listar() {
		return em.createQuery("SELECT e FROM " + entity.getName() + " e").getResultList();
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<E> listarPorNome() throws Exception {
		try {
			String consulta = "SELECT e FROM " + entity.getName() + " e "
					+ "ORDER BY e.nome";
			return em.createQuery(consulta).getResultList();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	

	@Transactional
	public E buscarPorId(int id) {
		return (E) em.find(entity, id);
	}

	@Transactional(value=TxType.REQUIRES_NEW)
	public void excluir(E entity) throws Exception {
		try {
			entity = em.merge(entity);
			em.remove(entity);			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}	
	
	public EntityManager getEntityManager(){
		return em;
	}
}
