class Book:
    def __init__(self, title, author, isbn, pages):
        self.title = title
        self.author = author
        self.isbn = isbn
        self.pages = pages

    def __str__(self):
        return f"{self.title} by {self.author} has {self.pages} pages"

    def __repr__(self):
        return f"""Book(title="{self.title}", author="{self.author}", isbn="{self.isbn}", pages="{self.pages}")"""

    def __eq__(self, other):
        return self.isbn == other.isbn


class EBook(Book):
    def __init__(self, title, author, isbn, pages, file_size_mb):
        super().__init__(title, author, isbn, pages)
        self.file_size_mb = file_size_mb

    def __str__(self):
        base_str = super().__str__()
        return f"{base_str}, file_size_mb={self.file_size_mb}"

    def __repr__(self):
        return f"""EBook(title="{self.title}", author="{self.author}", isbn="{self.isbn}", pages="{self.pages}", file_size_mb="{self.file_size_mb}")"""


book = Book("The Beginning of Infinity", "David Deutsch", "978-0-140-27816-3", 487)
ebook = EBook(
    "The Beginning of Infinity", "David Deutsch", "978-0-140-27816-3", 487, 1.5
)

for book in [book, ebook]:
    print(book)

for book in [book, ebook]:
    print(repr(book))

print(book == ebook)
