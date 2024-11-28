package br.com.rafaelaperruci.screenmatch_cmd_line.services;

import br.com.rafaelaperruci.screenmatch_cmd_line.models.SeriesData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataParser implements IDataParser{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T fromObject(String json, Class<T> obj) {
        try {
            return mapper.readValue(json, obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
