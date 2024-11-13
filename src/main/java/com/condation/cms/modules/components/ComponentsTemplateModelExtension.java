package com.condation.cms.modules.components;

import com.condation.cms.api.configuration.configs.SiteConfiguration;
import com.condation.cms.api.extensions.TemplateModelExtendingExtensionPoint;
import com.condation.cms.api.feature.features.ConfigurationFeature;
import com.condation.cms.api.feature.features.IsDevModeFeature;
import com.condation.cms.api.feature.features.TemplateEngineFeature;
import com.condation.cms.api.template.TemplateEngine.Model;

public class ComponentsTemplateModelExtension extends TemplateModelExtendingExtensionPoint{

    @Override
    public void extendModel(Model model) {
        model.values.put(
            "components", 
            new ComponentFunction(
                getRequestContext().get(TemplateEngineFeature.class).templateEngine(),
                getRequestContext().has(IsDevModeFeature.class),
                getTemplateFileExtension()
                )
        );
    }

    private String getTemplateFileExtension () {
        var templateExtension = getContext().get(ConfigurationFeature.class).configuration()
                    .get(SiteConfiguration.class)
                    .siteProperties()
                    .getOrDefault("template.extension", "html");
        
        if (templateExtension.startsWith(".")) {
            templateExtension = templateExtension.substring(1);
        }
        
        return templateExtension;
    }

}
