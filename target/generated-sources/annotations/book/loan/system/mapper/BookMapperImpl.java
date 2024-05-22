package book.loan.system.mapper;

import book.loan.system.domain.Book;
import book.loan.system.request.BookPostRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T01:40:29-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class BookMapperImpl extends BookMapper {

    @Override
    public Book toBook(BookPostRequestBody bookPostRequestBody) {
        if ( bookPostRequestBody == null ) {
            return null;
        }

        Book.BookBuilder book = Book.builder();

        book.title( bookPostRequestBody.getTitle() );
        book.author( bookPostRequestBody.getAuthor() );
        book.isbn( bookPostRequestBody.getIsbn() );

        return book.build();
    }
}
