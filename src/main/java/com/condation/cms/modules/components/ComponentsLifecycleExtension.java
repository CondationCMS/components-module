package com.condation.cms.modules.components;

import com.condation.cms.api.module.CMSModuleContext;
import com.condation.cms.api.module.CMSRequestContext;
import com.condation.modules.api.ModuleLifeCycleExtension;
import com.condation.modules.api.annotation.Extension;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author t.marx
 */
@Slf4j
@Extension(ModuleLifeCycleExtension.class)
public class ComponentsLifecycleExtension extends ModuleLifeCycleExtension<CMSModuleContext, CMSRequestContext> {


	@Override
	public void init() {

	}

	@Override
	public void activate() {
	}

}
