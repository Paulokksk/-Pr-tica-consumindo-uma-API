package com.superloja.superloja.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import com.superloja.superloja.model.endereco.CepDTO;
import com.superloja.superloja.model.endereco.Endereco;

@Controller
@RequestMapping("/enderecos")
public class EnderecoController {

    private String apiURL = "https://viacep.com.br/ws";
    private String dataFormat = "json";

    @GetMapping("/consulta")
    @ResponseBody
    public String consultaCep (String cep){
        RestTemplate rt = new RestTemplate();
        String urlconsulta = apiURL + "/" + cep + "/" + dataFormat;
        CepDTO dados = rt.getForObject(urlconsulta, CepDTO.class);
        Endereco e = new Endereco(dados);
        String resposta = "Resultado da busca" + e;
        return resposta;
    }


/////////////////////////////////////////////////////////////////////////////////////

@GetMapping("/consultaPorLocalidade")
@ResponseBody
public String consultaCepPorLocalidade(String uf, String cidade,  String localidade) {
    RestTemplate rt = new RestTemplate();
    String urlConsulta = apiURL + "/" + uf + "/" + cidade + "/" + localidade + "/" + dataFormat;
    CepDTO[] dados = rt.getForObject(urlConsulta, CepDTO[].class);

    if (dados != null && dados.length > 0) {
        StringBuilder resposta = new StringBuilder("Resultado da busca:\n");

        for (CepDTO cepDTO : dados) {
            Endereco e = new Endereco(cepDTO);
            resposta.append(e.toString()).append("\n");
        }

        return resposta.toString();
    } else {
        return "Nenhum CEP encontrado para a UF, Cidade e Localidade especificadas.";
    }
}

}
