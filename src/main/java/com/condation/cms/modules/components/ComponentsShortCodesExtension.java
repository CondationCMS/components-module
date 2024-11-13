package com.condation.cms.modules.components;

import java.util.Map;
import java.util.function.Function;

import com.condation.cms.api.extensions.RegisterShortCodesExtensionPoint;
import com.condation.cms.api.model.Parameter;

public class ComponentsShortCodesExtension extends RegisterShortCodesExtensionPoint {

    @Override
    public Map<String, Function<Parameter, String>> shortCodes() {
        return Map.of(
            "components", (params) -> {
                return "";
            }
        );
    }

}
