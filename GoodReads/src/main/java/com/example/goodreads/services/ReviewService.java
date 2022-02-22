package com.example.goodreads.services;

import com.example.goodreads.exceptions.BadRequestException;
import com.example.goodreads.exceptions.NotFoundException;
import com.example.goodreads.model.dto.reviewDTO.AddReviewDTO;
import com.example.goodreads.model.entities.Book;
import com.example.goodreads.model.entities.Review;
import com.example.goodreads.model.entities.User;
import com.example.goodreads.model.repository.BookRepository;
import com.example.goodreads.model.repository.ReviewRepository;
import com.example.goodreads.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public Review addReview(AddReviewDTO reviewDTO, long userId) {
        if (reviewDTO == null) {
            throw new BadRequestException("Invalid parameters!");
        }
        Book book = bookRepository.findById(reviewDTO.getBookId())
                .orElseThrow(() -> (new NotFoundException("Book not found!")));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> (new NotFoundException("User not found!")));
        if (reviewDTO.getReview() == null || reviewDTO.getReview().isBlank()) {
            throw new BadRequestException("Invalid review parameters!");
        }
        Optional<Review> opt = reviewRepository.findByBookAndUser(book, user);
        Review review;
        if (opt.isPresent()) {
            review = opt.get();
        }
        else {
            review = new Review();
            review.setBook(book);
            review.setUser(user);
            review.setReviewDate(LocalDate.now());
        }
        review.setReview(reviewDTO.getReview());
        return reviewRepository.save(review);
    }
}