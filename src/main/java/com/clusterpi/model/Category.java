package com.clusterpi.model;


public class Category {
    private final long id;
    private final String name;
    private String description;
    private Category parentCategory;

    public static class Builder {
        private final long id;
        private String name;
        private String description;
        private Category parentCategory;


        public Builder(long id){
            this.id = id;
        }

        public Builder withName(String name){
            this.name = name ;
            return this ;
        }

        public Builder withDescription(String description){
            this.description = description ;
            return this ;
        }


        public Builder withParentCategory(Category category){
            this.parentCategory = category ;
            return this ;
        }

        public Category build(){
            return new Category(this);
        }

    }

    private Category(Builder builder){
        this.id = builder.id ;
        this.name = builder.name ;
        this.description = builder.description ;
        this.parentCategory = builder.parentCategory ;
    }

    public long getId(){
        return this.id ;
    }

    public String getName(){
        return this.name ;
    }

    public String getDescription(){
        return this.description ;
    }

    public Category getParentCategory() {
        return this.parentCategory;
    }

}
