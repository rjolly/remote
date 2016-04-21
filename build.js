mkdir("classes")
scalac("src", "classes")
javac("src", "classes")
jar("remote.jar", "classes")
