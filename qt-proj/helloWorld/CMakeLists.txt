add_executable(hello)

target_sources(hello PRIVATE src/hello.cpp)

target_link_libraries(hello PRIVATE scl)
