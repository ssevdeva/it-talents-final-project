package com.example.goodreads.controller;

import com.example.goodreads.model.dto.bookDTO.BookResponseDTO;
import com.example.goodreads.model.dto.quoteDTO.AddQuoteDTO;
import com.example.goodreads.model.dto.quoteDTO.QuoteResponseDTO;
import com.example.goodreads.model.entities.Book;
import com.example.goodreads.model.entities.Quote;
import com.example.goodreads.services.QuoteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class QuoteController extends BaseController {

    @Autowired
    private QuoteService quoteService;
    @Autowired
    private ModelMapper mapper;

    @PostMapping("/quotes/new")
    public ResponseEntity<QuoteResponseDTO> addQuote(@RequestBody AddQuoteDTO quoteDTO,
                                                     HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        Quote q = quoteService.addQuote(quoteDTO, (long)session.getAttribute(USER_ID));
        QuoteResponseDTO dto = mapper.map(q, QuoteResponseDTO.class);
        return ResponseEntity.ok(dto);

    }

    @PutMapping("/quotes/react/{quote_id}")
    public ResponseEntity<QuoteResponseDTO> reactOnQuote(@PathVariable long quote_id,
                                                         HttpSession session, HttpServletRequest request) {
        validateSession(session, request);
        Quote q = quoteService.reactOnQuote(quote_id, (long)session.getAttribute(USER_ID));
        QuoteResponseDTO dto = mapper.map(q, QuoteResponseDTO.class);
        return ResponseEntity.ok(dto);
    }


//    @GetMapping("/quotes")
//    @GetMapping("/quotes/fav")
//    @GetMapping("/quotes/my_quotes")

//    @DeleteMapping("quote/{id}")

}
