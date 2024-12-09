package br.com.rafaelaperruci.screenmatch_cmd_line.models;

public enum Category {

    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    FICTION("Fiction"),
    FANTASY("Fantasy"),
    CRIME("Crime");

    private String categoryOMDB;

    Category(String categoryOMDB) {
        this.categoryOMDB = categoryOMDB;
    }

    public static Category fromString(String text){
        for (Category c : Category.values()){
            if (c.categoryOMDB.equalsIgnoreCase(text)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria correspondente foi encontrada.");
    }
}
