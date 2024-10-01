package med.voll.api.Controller;

import io.swagger.v3.core.util.Json;
import med.voll.api.Domain.Consultas.AgendaDeConsultas;
import med.voll.api.Domain.Consultas.DadosAgendamentoConsulta;
import med.voll.api.Domain.Consultas.DetalhamentoDadosConsulta;
import med.voll.api.Domain.Medico.DadosDetalhamentoMedico;
import med.voll.api.Domain.Medico.Especialidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@AutoConfigureJsonTesters
class ConsultaControllerTest
{

    @Autowired
    private MockMvc mock;

    @Autowired
    private JacksonTester<DadosAgendamentoConsulta> dadosAgendamentoConsultaJSON;

    @Autowired
    private JacksonTester<DetalhamentoDadosConsulta> dadosDetalhamentoConsultaJSON;

    @MockBean
    private AgendaDeConsultas agendaDeConsultas;

    @Test
    @DisplayName("Deveria devolver http 400 quando informações estão inválidas")
    void agendar_cenario1() throws Exception
    {
        var response = mock.perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver http 200 quando informações estão válidas")
    void agendar_cenario2() throws Exception
    {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = Especialidade.CARDIOLOGIA;

        var dadosDetalhamentos = new DetalhamentoDadosConsulta(null, 2L, 5L, data);

        when(agendaDeConsultas.agendar(any())).thenReturn(dadosDetalhamentos);

        var response = mock
                .perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dadosAgendamentoConsultaJSON.write(
                        new DadosAgendamentoConsulta
                                (2L, 5L, data, especialidade)).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonEsperado = dadosDetalhamentoConsultaJSON.write(
                new DetalhamentoDadosConsulta(null, 2L, 5L, data)
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}