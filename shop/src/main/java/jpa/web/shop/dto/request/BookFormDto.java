package jpa.web.shop.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookFormDto {

    public Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private String author;
    private String isbn;

    public BookFormDto() {
    }

    public BookFormDto(String name, int price, int stockQuantity, String author, String isbn) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.author = author;
        this.isbn = isbn;
    }
}
