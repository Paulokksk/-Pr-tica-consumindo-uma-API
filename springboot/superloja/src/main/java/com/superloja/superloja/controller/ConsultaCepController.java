package com.superloja.superloja.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.superloja.superloja.model.endereco.CepDTO;

@RestController
public class ConsultaCepController {

    private String apiURL = "https://viacep.com.br/ws";

    @GetMapping("/consulta-cep")
    public String consultaCepPorUFECidadeELocalidade(
            @RequestParam String uf,
            @RequestParam String cidade,
            @RequestParam String localidade) {

        String urlConsulta = apiURL + "/" + uf + "/" + cidade + "/" + localidade + "/json";

        RestTemplate restTemplate = new RestTemplate();
        CepDTO[] resultados = restTemplate.getForObject(urlConsulta, CepDTO[].class);

        StringBuilder response = new StringBuilder();

        if (resultados != null && resultados.length > 0) {
            response.append("CEPs encontrados:\n");
            for (CepDTO resultado : resultados) {
                response.append("CEP: ").append(resultado.cep()).append("\n");
                response.append("Logradouro: ").append(resultado.logradouro()).append("\n");
                response.append("Bairro: ").append(resultado.bairro()).append("\n");
                response.append("\n");
            }
        } else {
            response.append("Nenhum CEP encontrado para a UF, cidade e localidade especificadas.");
        }

        return response.toString();
    }
}
