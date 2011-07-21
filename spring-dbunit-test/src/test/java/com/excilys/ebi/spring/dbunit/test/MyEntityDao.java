/*
 * Copyright 2010-2011 eBusiness Information, Groupe Excilys (www.excilys.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.excilys.ebi.spring.dbunit.test;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Test DAO
 * 
 * @author author <a href="mailto:slandelle@excilys.com">Stephane LANDELLE</a>
 */
@Repository
public class MyEntityDao extends HibernateDaoSupport implements IMyEntityDao {

	@Autowired
	public MyEntityDao(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public List<MyEntity> loadAll() {
		return getHibernateTemplate().loadAll(MyEntity.class);
	}

	public MyEntity getById(String id) {
		Assert.hasText(id, "id required");
		return getHibernateTemplate().get(MyEntity.class, id);
	}

	public MyEntity getByName(final String name) {
		Assert.hasText(name, "name required");
		return getHibernateTemplate().execute(new HibernateCallback<MyEntity>() {
			public MyEntity doInHibernate(Session session) throws HibernateException, SQLException {
				return (MyEntity) session.createQuery("from " + MyEntity.class.getName() + " where name=:name").setString("name", name).uniqueResult();
			}
		});
	}

	public void saveOrUpdate(MyEntity entity) {
		Assert.notNull(entity, "entity required");
		getHibernateTemplate().saveOrUpdate(entity);
	}

	public void delete(MyEntity entity) {
		Assert.notNull(entity, "entity required");
		getHibernateTemplate().delete(entity);
	}

	public void deleteById(final String id) {
		Assert.hasText(id, "id required");
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery("delete from " + MyEntity.class.getName() + " where id=:id").setString("id", id).executeUpdate();
			}
		});
	}
}
