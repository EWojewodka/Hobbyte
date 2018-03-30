/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.webrest.hobbyte.app.user.dao.ExtranetUserDao;
import com.webrest.hobbyte.app.user.dao.ExtranetUserPolicyDao;
import com.webrest.hobbyte.app.user.model.ExtranetUser;
import com.webrest.hobbyte.app.user.model.enums.Gender;
import com.webrest.hobbyte.core.criteria.CriteriaFilter;

/**
 * @author Emil Wojewódka
 *
 * @since 24 mar 2018
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExtranetUserDaoTest {

	@Autowired
	private ExtranetUserDao dao;
	
	@Autowired
	private ExtranetUserPolicyDao userPolicyDao;

	private static final String TEST_EMAIL = "dsafnasdf432432+dsa@gmaildcscc.com";

	private static final String TEST_LOGIN = "123SDF!@#FDS";

	private static final String TEST_PASSWORD = "098dsadcxzc4214";

	private ExtranetUser testUser = getTestUser();

	private ExtranetUser getTestUser() {
		ExtranetUser user = new ExtranetUser();
		user.setEmail(TEST_EMAIL);
		user.setLogin(TEST_LOGIN);
		user.setPassword(TEST_PASSWORD);
		return user;
	}

	@Test
	@Transactional
	public void testDao() {
		try {
			dao.save(testUser); // save
			
			testCount();
			testExists();
			testUpdate();
			testSimpleFind();
			testCriteriaFind();
			
			dao.delete(testUser);
		} catch (Exception e) {
			e.printStackTrace();
			assertNull(e);
		}
	}

	public void testCount() throws Exception {
		assertTrue(dao.count() > 0);
	}

	public void testExists() throws Exception {
		assertTrue(dao.exists(testUser));
		assertTrue(userPolicyDao.exists(testUser.createOrGetUserPolicy()));
	}

	public void testUpdate() throws Exception {
		testUser.setGender(Gender.MALE);
		dao.save(testUser);
		assertEquals(Gender.MALE, testUser.getGender());
	}

	public void testSimpleFind() {
		assertEquals(TEST_LOGIN, dao.findByLogin(TEST_LOGIN).getLogin());
	}

	public void testCriteriaFind() {
		testCriteriaWhere();
		testCriteriaLimit();
	}
	
	public void testCriteriaWhere() {
		List<ExtranetUser> find = dao.find(new CriteriaFilter().addWhere("login", TEST_LOGIN));
		assertTrue(!find.isEmpty());
	}
	
	public void testCriteriaLimit() {
		CriteriaFilter withoutLimit = new CriteriaFilter();
		assertTrue(dao.find(withoutLimit).size() > 1);
		
		CriteriaFilter criteriaFilter = withoutLimit.setLimit(1);
		List<ExtranetUser> result = dao.find(criteriaFilter);
		assertTrue(result.size() == 1);
	}
}
