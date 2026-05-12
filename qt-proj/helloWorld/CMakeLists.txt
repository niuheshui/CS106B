add_executable(hello)

target_sources(hello PRIVATE src/hello.cpp)

target_link_libraries(hello PRIVATE scl)

add_custom_target(copy_res ALL
    COMMAND ${CMAKE_COMMAND} -E copy_directory ${CMAKE_CURRENT_SOURCE_DIR}/res ${CMAKE_CURRENT_BINARY_DIR}/res
)
