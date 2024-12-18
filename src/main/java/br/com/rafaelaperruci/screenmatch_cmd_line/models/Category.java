package br.com.rafaelaperruci.screenmatch_cmd_line.models;

public enum Category {

    ACTION("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    COMEDY("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    FICTION("Fiction", "Ficção"),
    FANTASY("Fantasy", "Fantasia"),
    CRIME("Crime", "Crime"),
    MUSICAL("Musical", "Musical");

    private String categoryOMDB;
    private String categoryPortuguese;

    Category(String categoryOMDB, String categoryPortuguese) {
        this.categoryOMDB = categoryOMDB;
        this.categoryPortuguese = categoryPortuguese;
    }

    public static Category fromString(String text){
        for (Category c : Category.values()){
            if (c.categoryOMDB.equalsIgnoreCase(text)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria correspondente foi encontrada.");
    }
    public static Category fromPortuguese(String text){
        for (Category c : Category.values()){
            if (c.categoryPortuguese.equalsIgnoreCase(text)){
                return c;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria correspondente foi encontrada.");
    }
}
