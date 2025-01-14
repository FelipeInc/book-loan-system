package book.loan.system.controler;

import book.loan.system.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class BookControllerTest {


    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookServiceMock;

    @Test
    void test(){

    }
}