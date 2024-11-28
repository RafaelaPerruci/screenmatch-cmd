package br.com.rafaelaperruci.screenmatch_cmd_line.services;

public interface IDataParser {

    <T> T fromObject(String json, Class<T> obj);
}
