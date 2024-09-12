package book.loan.system.request;

public record BookPutRequestDTO(Long id, String title, String author, Long isbn) {
}