package com.condation.cms.modules.components;

import static org.mockito.Mockito.times;

import java.io.IOException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.condation.cms.api.template.TemplateEngine;

@ExtendWith(MockitoExtension.class)
public class ComponentFunctionTest {

    @Mock
    TemplateEngine templateEngine;

    ComponentFunction sut;

    @BeforeEach
    void setup () {
        sut = new ComponentFunction(templateEngine, true, "html");
    }

    @Test
    void error_handling () throws IOException {

        Mockito.when(templateEngine.render(Mockito.anyString(), Mockito.any(TemplateEngine.Model.class))).thenThrow(new IOException("template not found"));

        var result = sut.render("address");

        Assertions.assertThat(result).isEqualTo("[component address not found]");

        Mockito.verify(templateEngine, times(1))
            .render(Mockito.eq("components/address.html"), Mockito.any(TemplateEngine.Model.class));
    }

    @Test
    void templateEngine_render () throws IOException {

        Mockito.when(templateEngine.render(Mockito.anyString(), Mockito.any(TemplateEngine.Model.class))).thenReturn("My address!");

        var result = sut.render("address");

        Assertions.assertThat(result).isEqualTo("My address!");

        Mockito.verify(templateEngine, times(1))
            .render(Mockito.eq("components/address.html"), Mockito.any(TemplateEngine.Model.class));
    }
}
