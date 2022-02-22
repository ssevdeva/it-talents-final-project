package com.example.goodreads.model.repository;

import com.example.goodreads.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findBookByISBN(String ISBN);
    List<Book> findBooksByTitleLike(String searchWord);

}
