package book.loan.system.service;

import book.loan.system.domain.Book;
import book.loan.system.mapper.BookMapper;
import book.loan.system.repository.BookRepository;
import book.loan.system.request.BookPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BookService  {
    private final BookRepository bookRepository;

    public Page<Book> listAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }

    public Book save(BookPostRequestBody bookPostRequestBody){
        return bookRepository.save(BookMapper.INSTANCE.toBook(bookPostRequestBody));
    }

}
