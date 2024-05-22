package book.loan.system.mapper;


import book.loan.system.domain.Book;
import book.loan.system.request.BookPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "Spring")
public abstract class BookMapper {
    public static final BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);
    public abstract Book toBook(BookPostRequestBody bookPostRequestBody);
}