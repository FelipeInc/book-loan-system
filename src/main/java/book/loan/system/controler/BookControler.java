package book.loan.system.controler;

import book.loan.system.domain.Book;
import book.loan.system.request.BookPostRequestBody;
import book.loan.system.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("books")
@Log4j2
@RequiredArgsConstructor
public class BookControler {
    @Autowired
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<Book>> list(Pageable pageable){
            return ResponseEntity.ok(bookService.listAll(pageable));
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody @Valid BookPostRequestBody book){
        return new ResponseEntity<>(bookService.save(book), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Book> findBookDetails(@PathVariable Long id){
        return ResponseEntity.ok(bookService.findBookDetails(id));
    }
}
