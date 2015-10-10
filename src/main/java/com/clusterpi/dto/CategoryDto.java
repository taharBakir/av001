package com.clusterpi.dto;


public class CategoryDto {
    private final long id;
    private final String name;
    private String description;
    private CategoryDto parentCategory;

    public static class Builder {
        private final long id;
        private String name;
        private String description;
        private CategoryDto parentCategory;


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


        public Builder withParentCategory(CategoryDto category){
            this.parentCategory = category ;
            return this ;
        }

        public CategoryDto build(){
            return new CategoryDto(this);
        }

    }

    private CategoryDto(Builder builder){
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

    public CategoryDto getParentCategory() {
        return this.parentCategory;
    }

}
