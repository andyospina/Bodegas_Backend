package com.bodegas;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class BodegaControllerIntegracionTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getBodegasDevuelveLosDatosDePruebaConSusProductos() throws Exception {
        mockMvc.perform(get("/bodegas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre").value("Bodega Norte"))
                .andExpect(jsonPath("$[0].productos", hasSize(2)))
                .andExpect(jsonPath("$[0].productos[0].nombre").value("Computador portatil"))
                .andExpect(jsonPath("$[0].productos[0].cantidad").value(15))
                .andExpect(jsonPath("$[1].nombre").value("Bodega Sur"))
                .andExpect(jsonPath("$[1].productos", hasSize(1)))
                .andExpect(jsonPath("$[1].productos[0].nombre").value("Impresora"));
    }

    @Test
    void postBodegasRegistraUnaBodegaNuevaSinProductos() throws Exception {
        mockMvc.perform(post("/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Bodega Centro\",\"ubicacion\":\"Carrera 7 # 32-10\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Bodega Centro"))
                .andExpect(jsonPath("$.productos", hasSize(0)));

        mockMvc.perform(get("/bodegas"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].nombre").value("Bodega Centro"));
    }

    @Test
    void postBodegasConCamposVaciosResponde400() throws Exception {
        mockMvc.perform(post("/bodegas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"ubicacion\":\" \"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.ubicacion").exists());
    }
}
