    package com.example.itog.model;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "products")
    public class Product {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String description;
        private Double price;
        private String photo;

        @ManyToOne
        @JoinColumn(name = "material_id")
        private Material material;

    //------------------------------------------------------
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public Material getMaterial() {
            return material;
        }

        public void setMaterial(Material material) {
            this.material = material;
        }

        public Product(Long id, String name, String description, Double price, String photo, Material material) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.photo = photo;
            this.material = material;
        }

        public Product() {
        }
    }
