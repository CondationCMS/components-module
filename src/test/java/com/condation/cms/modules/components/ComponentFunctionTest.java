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
