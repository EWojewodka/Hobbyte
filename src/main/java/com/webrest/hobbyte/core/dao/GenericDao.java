package com.webrest.hobbyte.core.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.webrest.hobbyte.core.criteria.CriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter;
import com.webrest.hobbyte.core.criteria.ICriteriaFilter.OrderDirections;
import com.webrest.hobbyte.core.dao.DaoListener.DaoListenerState;
import com.webrest.hobbyte.core.model.DatabaseObject;
import com.webrest.hobbyte.core.utils.Asserts;
import com.webrest.hobbyte.core.utils.ClassUtils;
import com.webrest.hobbyte.core.utils.StringUtils;
import com.webrest.hobbyte.core.utils.collection.PriorityComparer;

/**
 * Basic Database Access Object implementation.
 * 
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
@Component
@Transactional(rollbackOn = Exception.class)
@SuppressWarnings("unchecked")
public class GenericDao<T extends DatabaseObject> implements IGenericDao<T, Integer> {

	@PersistenceContext
	protected EntityManager em;

	private List<DaoListener<DatabaseObject>> listeners = new LinkedList<>();

	private ListenerExecutor listenerExecutor = new ListenerExecutor();

	protected void addListener(DaoListener<? extends DatabaseObject> daoListener) {
		Asserts.notNull(daoListener, "Cannot add null listener");
		listeners.add((DaoListener<DatabaseObject>) daoListener);
	}

	@Override
	public void save(T databaseObject) {
		boolean isUpdate = databaseObject.getId() > 0;
		listenerExecutor.execute(isUpdate ? DaoListenerState.BEFORE_SAVE : DaoListenerState.BEFORE_FIRST_SAVE,
				databaseObject);
		if (isUpdate)
			databaseObject = em.merge(databaseObject);
		em.persist(databaseObject);
		listenerExecutor.execute(isUpdate ? DaoListenerState.AFTER_SAVE : DaoListenerState.AFTER_FIRST_SAVE,
				databaseObject);
	}

	@Override
	public Long count() {
		String sql = String.format("SELECT COUNT(t) FROM %s t", getGenericType().getSimpleName());
		TypedQuery<?> createQuery = em.createQuery(sql, Long.class);
		return (Long) createQuery.getSingleResult();
	}

	@Override
	public T create() {
		throw new NotYetImplementedException("This method must be override by subclass");
	}

	@Override
	public void delete(T databaseObject) {
		listenerExecutor.execute(DaoListenerState.BEFORE_REMOVE, databaseObject);
		em.remove(em.contains(databaseObject) ? databaseObject : em.merge(databaseObject));
		listenerExecutor.execute(DaoListenerState.AFTER_REMOVE, databaseObject);
	}

	@Override
	public void delete(Integer id) {
		delete(getById(id));
	}

	@Override
	public boolean exists(T databaseObject) {
		return exists(databaseObject.getId());
	}

	@Override
	public boolean exists(Integer id) {
		return getById(id) != null;
	}

	@Override
	public T getById(Integer id) {
		return (T) em.find(getGenericType(), id);
	}

	@Override
	public List<T> findAllBy(String fieldName, Object value) {
		return find(new CriteriaFilter(fieldName, value));
	}

	@Override
	public T findBy(String fieldName, Object value) {
		List<T> result = findAllBy(fieldName, value);
		return result.isEmpty() ? null : result.get(0);
	}

	@Override
	public List<T> find(ICriteriaFilter<?> criteriaFilter) {
		Criteria criteria = em.unwrap(Session.class).createCriteria(getGenericType());
		
		//Add where
		criteria.add(Restrictions.allEq(criteriaFilter.getWhere()));
		
		//Add where in
		Map<String, Object[]> whereIn = criteriaFilter.getWhereIn();
		whereIn.forEach((c, v) -> {
			criteria.add(Restrictions.in(c, v));
		});
		
		//set limit
		int limit = criteriaFilter.getLimit();
		if (limit > 0)
			criteria.setMaxResults(limit);

		//Set order
		String orderBy = criteriaFilter.getOrderBy();
		if (!StringUtils.isEmpty(orderBy)) {
			criteria.addOrder(criteriaFilter.getOrderDirection() == OrderDirections.ASC ? Order.asc(orderBy)
					: Order.desc(orderBy));
		}

		//Set distinct
		if (criteriaFilter.isDistinct())
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	// ---- UTILS ----

	private Class<?> _genericType;

	/**
	 * Return type of object whicha re handled by this instance of Database Access
	 * Object.
	 */
	protected Class<?> getGenericType() {
		if (_genericType == null)
			_genericType = ClassUtils.getGenericType(this.getClass());
		return _genericType;
	}

	private class ListenerExecutor {

		/**
		 * Execute methods from {@link DaoListener}
		 * 
		 * @param state
		 * @param dbo
		 */
		@SuppressWarnings("rawtypes")
		void execute(DaoListenerState state, DatabaseObject dbo) {
			listeners.sort(new PriorityComparer());
			Consumer<DaoListener> consumer = null;

			switch (state) {
			case LOAD:
				consumer = (l -> {
					l.onLoad(dbo);
				});
				break;
			case AFTER_REMOVE:
				consumer = (l -> {
					l.afterRemove(dbo);
				});
				break;
			case AFTER_SAVE:
				consumer = (l -> {
					l.afterSave(dbo);
				});
				break;
			case BEFORE_REMOVE:
				consumer = (l -> {
					l.beforeRemove(dbo);
				});
				break;
			case BEFORE_SAVE:
				consumer = (l -> {
					l.beforeSave(dbo);
				});
				break;
			case CREATE:
				consumer = (l -> {
					l.onCreate();
				});
				break;
			case BEFORE_FIRST_SAVE:
				consumer = (l -> {
					l.beforeFirstSave(dbo);
				});
				break;
			case AFTER_FIRST_SAVE:
				consumer = (l -> {
					l.afterFirstSave(dbo);
				});
				break;
			default:
				throw new IllegalArgumentException("Command " + state + "is not implemented yet. :<");
			}

			_execute(consumer);
		}

		@SuppressWarnings("rawtypes")
		// for shorter code.
		private void _execute(Consumer<DaoListener> listenerConsumer) {
			listeners.forEach(listenerConsumer);
		}

	}

}
