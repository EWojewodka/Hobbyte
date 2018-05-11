/**
 * 
 */
package com.webrest.hobbyte.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.webrest.hobbyte.app.user.model.enums.ProfileVisibility;
import com.webrest.hobbyte.core.platform.AvailablePlatformProfiles;

/**
 * @author Emil Wojewódka
 *
 * @since 17 mar 2018
 */
public class EnumUtilsTest {

	@Test
	public void findByIdTest() {
		ProfileVisibility byId = ProfileVisibility.getById(ProfileVisibility.PUBLIC.getId());
		Assert.assertEquals(ProfileVisibility.PUBLIC, byId);
	}
	
	@Test
	public void findByIdNegativeTest() {
		ProfileVisibility byId = ProfileVisibility.getById(ProfileVisibility.PUBLIC.getId());
		Assert.assertNotEquals(ProfileVisibility.FOR_FRIENDS, byId);
	}
	
	@Test
	public void findByCode() {
		AvailablePlatformProfiles findByCode = EnumUtils.findByCode(AvailablePlatformProfiles.class, AvailablePlatformProfiles.DEVELOPMENT.getCode());
		Assert.assertNotNull(findByCode);
	}
	
	@Test
	public void findByCodes() {
		List<AvailablePlatformProfiles> profiles = new ArrayList<>();
		profiles.add(AvailablePlatformProfiles.DEVELOPMENT);
		profiles.add(AvailablePlatformProfiles.PRODUCTION);
		List<AvailablePlatformProfiles> findByCodes = EnumUtils.findByCodes(AvailablePlatformProfiles.class, StringUtils.toGenericStringCodes(profiles, ","), ",");
		Assert.assertNotNull(findByCodes);
		Assert.assertTrue(findByCodes.size() == 2);
	}
	
}
