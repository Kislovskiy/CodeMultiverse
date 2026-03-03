class TextFile:
    def read(self):
        print("Reading from the file.")

    def close(self):
        print("Closing the file.")


class Database:
    def read(self):
        print("Reading form the database.")

    def close(self):
        print("Closing the Database connection.")


class WebService:
    def read(self):
        print("Reading form webservice.")

    def close(self):
        print("Closing connection to web service.")


def process_resource(resource):
    resource.read()
    resource.close()


process_resource(TextFile())
process_resource(Database())
process_resource(WebService())
