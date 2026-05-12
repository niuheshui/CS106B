
add_library(scl)

file(GLOB_RECURSE SCL_HEADERS RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "lib/StanfordCPPLib/*.h")
file(GLOB_RECURSE SCL_SOURCES RELATIVE ${CMAKE_CURRENT_SOURCE_DIR} "lib/StanfordCPPLib/*.cpp")

target_sources(scl 
PRIVATE
    ${SCL_HEADERS}
    ${SCL_SOURCES}

PUBLIC 
    FILE_SET h1
    TYPE HEADERS
    BASE_DIRS lib/StanfordCPPLib

PRIVATE
    FILE_SET h2
    TYPE HEADERS
    BASE_DIRS lib/StanfordCPPLib/private

PRIVATE
    FILE_SET h3
    TYPE HEADERS
    BASE_DIRS lib/StanfordCPPLib/stacktrace

)

target_compile_options(scl PUBLIC
    -std=c++11
    -Wall
    -Wextra
    -Wreturn-type
    -Werror=return-type
    -Wunreachable-code
    -Wno-missing-field-initializers
    -Wno-sign-compare
    -Wno-write-strings
    -O0
    -g3
    -ggdb3
)

target_compile_definitions(scl 
PRIVATE 
    SPL_CONSOLE_X=999999
    SPL_CONSOLE_Y=999999
    SPL_CONSOLE_WIDTH=750
    SPL_CONSOLE_HEIGHT=500
    SPL_CONSOLE_FONTSIZE=14
    SPL_CONSOLE_ECHO
    SPL_CONSOLE_EXIT_ON_CLOSE
    SPL_VERIFY_JAVA_BACKEND_VERSION
    SPL_PROJECT_VERSION=20141113
    SPL_CONSOLE_PRINT_EXCEPTIONS
)