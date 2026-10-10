/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.stockmanagement.web.resource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.openmrs.api.ValidationException;
import org.openmrs.module.stockmanagement.api.dto.BatchJobDTO;
import org.openmrs.module.stockmanagement.api.dto.StockItemDTO;
import org.openmrs.module.stockmanagement.api.dto.StockItemPackagingUOMDTO;
import org.openmrs.module.stockmanagement.api.dto.StockOperationDTO;
import org.openmrs.module.stockmanagement.api.dto.StockRuleDTO;
import org.openmrs.module.stockmanagement.api.dto.UserRoleScopeDTO;
import org.openmrs.web.test.jupiter.BaseModuleWebContextSensitiveTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Since webservices.rest 3.2.0, DelegatingCrudResource no longer validates the delegate on create and
 * update, and core only validates OpenmrsObjects. The resources whose delegate is a DTO therefore run its
 * validator themselves before saving.
 */
public class ResourceValidationTest extends BaseModuleWebContextSensitiveTest {

	@Test
	public void save_shouldRejectAnInvalidStockOperation() {
		assertFieldRejected("operationTypeUuid", () -> new StockOperationResource().save(new StockOperationDTO()));
	}

	@Test
	public void save_shouldRejectAnInvalidStockItem() {
		assertFieldRejected("drugUuid", () -> new StockItemResource().save(new StockItemDTO()));
	}

	@Test
	public void save_shouldRejectAnInvalidStockRule() {
		assertFieldRejected("stockItemUuid", () -> new StockRuleResource().save(new StockRuleDTO()));
	}

	@Test
	public void save_shouldRejectAnInvalidUserRoleScope() {
		assertFieldRejected("userUuid", () -> new UserRoleScopeResource().save(new UserRoleScopeDTO()));
	}

	@Test
	public void save_shouldRejectAnInvalidBatchJob() {
		assertFieldRejected("batchJobType", () -> new BatchJobResource().save(new BatchJobDTO()));
	}

	@Test
	public void save_shouldRejectAnInvalidStockItemPackagingUOM() {
		assertFieldRejected("packagingUomUuid",
		    () -> new StockItemPackagingUOMResource().save(new StockItemPackagingUOMDTO()));
	}

	private void assertFieldRejected(String field, Executable save) {
		ValidationException exception = assertThrows(ValidationException.class, save);
		assertNotNull(exception.getErrors().getFieldError(field));
	}
}
