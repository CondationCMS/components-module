package com.condation.cms.modules.components;

/*-
 * #%L
 * components-module
 * %%
 * Copyright (C) 2024 CondationCMS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.condation.cms.api.feature.features.HookSystemFeature;
import com.condation.cms.api.feature.features.IsDevModeFeature;
import com.condation.cms.api.feature.features.TemplateEngineFeature;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.condation.cms.api.hooks.HookSystem;
import com.condation.cms.api.model.Parameter;
import com.condation.cms.api.module.CMSModuleContext;
import com.condation.cms.api.module.CMSRequestContext;
import com.condation.cms.api.template.TemplateEngine;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class ComponentFunction {

    private final TemplateEngine templateEngine;
    private final HookSystem hookSystem;
    private final boolean devMode;
    private final String templateExtension;
	private final CMSRequestContext cmsRequestContext;
	
	public ComponentFunction (CMSModuleContext context, CMSRequestContext requestContext) {
		this.templateEngine = requestContext.get(TemplateEngineFeature.class).templateEngine();
		this.hookSystem = requestContext.get(HookSystemFeature.class).hookSystem();
		this.devMode = requestContext.has(IsDevModeFeature.class);
		this.templateExtension = Helpers.getTemplateFileExtension(context);
		this.cmsRequestContext = requestContext;
	}
	
    public String render (String name) {
        return render(name, Collections.emptyMap());
    }

    private Optional<String> tryHook (String name, Map<String, Object> data) {

        var hookName = "components/%s".formatted(name);

        var result = hookSystem.execute(hookName, data);

        if (!result.results().isEmpty()
            && result.results().getFirst() instanceof String stringValue) {
            return Optional.of(stringValue);
        }

        return Optional.empty();
    }

    public String render (String name, Map<String, Object> data) {

        var hookResult = tryHook(name, data);
        if (hookResult.isPresent()) {
            return hookResult.get();
        }

        var templateFile = "components/%s.%s".formatted(name, templateExtension);

		TemplateEngine.Model model = new TemplateEngine.Model(null, null, cmsRequestContext);
        model.values.putAll(data);

        try {
            return templateEngine.render(templateFile, model);
        } catch (Exception e) {
            log.error("", e);

            if (devMode) {
                return "[component %s not found]".formatted(name);
            }
        }

        return "";
    }

    public String execute (Parameter parameter) {
        var name = (String)parameter.get("component");
        
        var model = new HashMap<>(parameter);
        model.remove("component");

        return render(name, model);
    }
}
