package com.example.goodreads.model.dto.bookDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RateBookDTO {

    private long bookId;
    private int rating;

}
