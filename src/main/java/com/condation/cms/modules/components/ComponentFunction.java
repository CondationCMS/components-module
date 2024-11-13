package com.condation.cms.modules.components;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import com.condation.cms.api.template.TemplateEngine;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ComponentFunction {

    private final TemplateEngine templateEngine;
    private final boolean devMode;
    private final String templateExtension;

    public String render (String name) {
        return render(name, Collections.emptyMap());
    }

    public String render (String name, Map<String, Object> data) {

        var templateFile = "components/%s.%s".formatted(name, templateExtension);

        TemplateEngine.Model model = new TemplateEngine.Model(null, null);
        model.values.putAll(data);

        try {
            return templateEngine.render(templateFile, model);
        } catch (IOException e) {
            log.error("", e);

            if (devMode) {
                return "[component %s not found]".formatted(name);
            }
        }

        return "";
    }
}
