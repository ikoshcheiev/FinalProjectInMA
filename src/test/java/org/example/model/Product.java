package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    String name;
    String title;
    Double oldPrice;
    Double salePrice;

    Double fromPrice;
    Double toPrice;

    Double regularPrice;
}
