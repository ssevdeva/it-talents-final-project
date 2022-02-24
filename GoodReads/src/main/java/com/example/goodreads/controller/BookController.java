package com.example.goodreads.controller;

import com.example.goodreads.model.dto.bookDTO.AddBookToShelfDTO;
import com.example.goodreads.model.dto.bookDTO.BookResponseDTO;
import com.example.goodreads.model.dto.bookDTO.GetBookDTO;
import com.example.goodreads.model.dto.bookDTO.SearchBookDTO;
import com.example.goodreads.services.BookService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books/add")
    public ResponseEntity<BookResponseDTO> addBook(@RequestParam(name = "bookInfo") String bookInfo,
                                                   @RequestParam(name = "cover") MultipartFile cover,
                                                   HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        BookResponseDTO dto = bookService.addBook(bookInfo, cover, (long)session.getAttribute(USER_ID));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/books/add_edition/{book_id}")
    public ResponseEntity<BookResponseDTO> addEdition(@PathVariable long book_id,
                                                      @RequestParam(name = "book_info") String bookInfo,
                                                      @RequestParam(name = "cover") MultipartFile cover,
                                                      HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        BookResponseDTO dto = bookService.addEdition(book_id, bookInfo, cover, (long)session.getAttribute(USER_ID));
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/books/add_to_shelf")
    public ResponseEntity<BookResponseDTO> addToShelf(@RequestBody AddBookToShelfDTO bookDTO,
                                                      HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        BookResponseDTO dto = bookService.addToShelf(bookDTO, (long)session.getAttribute(USER_ID));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search/by_title/{title}")
    public ResponseEntity<List<SearchBookDTO>> searchBooksByTitle(@PathVariable String title, HttpSession session) {
        validateSession(session);
        List<SearchBookDTO> responseList = bookService.searchBooksByTitle(title);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/search/by_author/{author}")
    public ResponseEntity<List<SearchBookDTO>> searchBooksByAuthor(@PathVariable String author, HttpSession session) {
        validateSession(session);
        List<SearchBookDTO> responseList = bookService.searchBooksByAuthor(author);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/search/by_genre/{id}")
    public ResponseEntity<List<SearchBookDTO>> searchBooksByGenre(@PathVariable long id, HttpSession session) {
        validateSession(session);
        List<SearchBookDTO> responseList = bookService.searchBooksByGenre(id);
        return ResponseEntity.ok(responseList);
    }

    @SneakyThrows
    @GetMapping("/books/show/{id}")
    public ResponseEntity<GetBookDTO> getBook(@PathVariable long id,HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        GetBookDTO bookDTO = bookService.getBook(id);
        return ResponseEntity.ok(bookDTO);
    }

    @SneakyThrows
    @GetMapping("/books/cover/{id}")
    public void getCover(@PathVariable long id, HttpSession session, HttpServletResponse response) {
        validateSession(session);
        File cover = bookService.getCover(id);
        Files.copy(cover.toPath(), response.getOutputStream());
    }

    @DeleteMapping("/books/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable long id, HttpSession session, HttpServletRequest request){
        validateSession(session, request);
        String msg = bookService.deleteBook(id, (long) session.getAttribute(USER_ID));
        return ResponseEntity.ok(msg);
    }

}
