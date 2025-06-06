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

import java.util.Map;
import java.util.function.Function;

import com.condation.cms.api.extensions.RegisterTemplateComponentExtensionPoint;
import com.condation.cms.api.model.Parameter;
import com.condation.modules.api.annotation.Extension;

@Extension(RegisterTemplateComponentExtensionPoint.class)
public class RegisterTemplateComponentExtension extends RegisterTemplateComponentExtensionPoint {

    @Override
	public Map<String, Function<Parameter, String>> components() {
		var componentFunction = new ComponentFunction(
				getContext(), getRequestContext()
                );

        return Map.of(
            "components", componentFunction::execute
        );
	}

}
