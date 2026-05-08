# Stanford CS 106 B/X Qt Creator project file
#
# This file specifies the information about your project to Qt Creator.
# You should not need to modify this file to complete your assignment.
#
# If you need to add files or folders to your project, we recommend the following:
# - close Qt Creator.
# - delete your ".pro.user" file and "build_xxxxxxx" directory.
# - place the new files/folders into your project directory.
# - re-open and "Configure" your project again.
#
# @author Marty Stepp, Reid Watson, Rasmus Rygaard, Jess Fisher, etc.
# @version 2015/04/09
# - decreased Mac stack size to avoid sporatic crashes on Mac systems
# @version 2014/11/29
# - added pthread library on Mac/Linux for running each test in its own thread
# @version 2014/11/13
# - fixes related to generating stack traces
# - support for putting testing files in a src/test/ folder (used in development)
# @version 2014/11/05
# - improved/fixed flags for exception-handling
# @version 2014/10/31
# - standard autograder-compatible version; should work with all assignments and graders.

# TEMPLATE = app

!isEmpty(INCLUDE_DIR) {
    message("Included by: $$INCLUDE_DIR")
} else {
    error(INCLUDE_DIR not set)
}

LIBS += -L$$OUT_PWD/../$$basename(PWD) -lscl

# make sure we do not accidentally #include files placed in 'resources'
CONFIG += no_include_pwd

exists($$INCLUDE_DIR/src/*.cpp) {
    SOURCES += $$INCLUDE_DIR/src/*.cpp
}
exists($$INCLUDE_DIR/src/test/*.cpp) {
    SOURCES += $$INCLUDE_DIR/src/test/*.cpp
}

exists($$INCLUDE_DIR/src/*.h) {
    HEADERS += $$INCLUDE_DIR/src/*.h
}
exists($$INCLUDE_DIR/src/test/*.h) {
    HEADERS += $$INCLUDE_DIR/src/test/*.h
}

# set up flags for the C++ compiler
# (In general, many warnings/errors are enabled to tighten compile-time checking.
# A few overly pedantic/confusing errors are turned off for simplicity.)
QMAKE_CXXFLAGS += -std=c++11
QMAKE_CXXFLAGS += -Wall
QMAKE_CXXFLAGS += -Wextra
QMAKE_CXXFLAGS += -Wreturn-type
QMAKE_CXXFLAGS += -Werror=return-type
QMAKE_CXXFLAGS += -Wunreachable-code
QMAKE_CXXFLAGS += -Wno-missing-field-initializers
QMAKE_CXXFLAGS += -Wno-sign-compare
QMAKE_CXXFLAGS += -Wno-write-strings

unix:!macx {
    QMAKE_CXXFLAGS += -rdynamic
    QMAKE_LFLAGS += -rdynamic
    QMAKE_LFLAGS += -Wl,--export-dynamic
    QMAKE_CXXFLAGS += -Wl,--export-dynamic
}
!win32 {
    QMAKE_CXXFLAGS += -Wno-dangling-field
    QMAKE_CXXFLAGS += -Wno-unused-const-variable
    LIBS += -ldl
}

# increase system stack size (helpful for recursive programs)
win32 {
    QMAKE_LFLAGS += -Wl,--stack,536870912
    LIBS += -lDbghelp
    LIBS += -lbfd
    #LIBS += -liberty
    LIBS += -limagehlp
}
macx {
    #QMAKE_LFLAGS += -Wl,-stack_size,0x2000000
}

# directories examined by Qt Creator when student writes an #include statement
INCLUDEPATH += $$PWD/lib/StanfordCPPLib/
INCLUDEPATH += $$PWD/lib/StanfordCPPLib/private/
INCLUDEPATH += $$PWD/lib/StanfordCPPLib/stacktrace/
INCLUDEPATH += $$INCLUDE_DIR/src/
INCLUDEPATH += $$PWD/
exists($$INCLUDE_DIR/src/test/*.h) {
    INCLUDEPATH += $$INCLUDE_DIR/src/test/
}

# build-specific options (debug vs release)
CONFIG(release, debug|release) {
    # make 'release' target be statically linked so it is a stand-alone executable
    # (this code comes from Rasmus Rygaard)
    QMAKE_CXXFLAGS += -O2
    macx {
        QMAKE_POST_LINK += 'macdeployqt $${OUT_PWD}/$${TARGET}.app && rm $${OUT_PWD}/*.o && rm $${OUT_PWD}/Makefile'
    }
    unix:!macx {
        QMAKE_POST_LINK += 'rm $${OUT_PWD}/*.o && rm $${OUT_PWD}/Makefile'
        QMAKE_LFLAGS += -static
        QMAKE_LFLAGS += -static-libgcc
        QMAKE_LFLAGS += -static-libstdc++
    }
    win32 {
        TARGET_PATH = $${OUT_PWD}/release/$${TARGET}.exe
        TARGET_PATH ~= s,/,\\,g

        OUT_PATH = $${OUT_PWD}/
        OUT_PATH ~= s,/,\\,g

        REMOVE_DIRS += $${OUT_PWD}/release
        REMOVE_DIRS += $${OUT_PWD}/debug
        REMOVE_FILES += $${OUT_PWD}/Makefile
        REMOVE_FILES += $${OUT_PWD}/Makefile.Debug
        REMOVE_FILES += $${OUT_PWD}/Makefile.Release
        REMOVE_FILES += $${OUT_PWD}/object_script.$${TARGET}.Release
        REMOVE_FILES += $${OUT_PWD}/object_script.$${TARGET}.Debug
        REMOVE_DIRS ~= s,/,\\,g
        REMOVE_FILES ~= s,/,\\,g

        QMAKE_LFLAGS += -static
        QMAKE_LFLAGS += -static-libgcc
        QMAKE_LFLAGS += -static-libstdc++
        QMAKE_POST_LINK += 'move $${TARGET_PATH} $${OUT_PWD} \
            && rmdir /s /q $${REMOVE_DIRS} \
            && del $${REMOVE_FILES}'
    }
}
CONFIG(debug, debug|release) {
    # make 'debug' target use no optimization, generate debugger symbols,
    # and catch/print any uncaught exceptions thrown by the program
    QMAKE_CXXFLAGS += -O0
    QMAKE_CXXFLAGS += -g3
    QMAKE_CXXFLAGS += -ggdb3
    DEFINES += SPL_CONSOLE_PRINT_EXCEPTIONS
}

# This function copies the given files to the destination directory.
# Used to place important resources from res/ and spl.jar into build/ folder.
defineTest(copyToDestdir) {
    files = $$1

    for(FILE, files) {
        DDIR = $$OUT_PWD

        # Replace slashes in paths with backslashes for Windows
        win32:FILE ~= s,/,\\,g
        win32:DDIR ~= s,/,\\,g

        !win32 {
            copyResources.commands += cp -r '"'$$FILE'"' '"'$$DDIR'"' $$escape_expand(\\n\\t)
        }
        win32 {
            copyResources.commands += xcopy '"'$$FILE'"' '"'$$DDIR'"' /e /y $$escape_expand(\\n\\t)
        }
    }
    export(copyResources.commands)
}

!win32 {
    copyToDestdir($$files($$PWD/lib/*.jar))
}
win32 {
    copyToDestdir($$PWD/lib/*.jar)
    copyToDestdir($$PWD/lib/addr2line.exe)
}

copyResources.input += $$files($$PWD/lib/*.jar)
win32 {
    copyResources.input += $$files($$PWD/lib/addr2line.exe)
}

QMAKE_EXTRA_TARGETS += copyResources
POST_TARGETDEPS += copyResources

# Platform-specific project settings to reduce warnings on Mac OS X systems
macx {
    cache()
    QMAKE_MAC_SDK = macosx
}

# ================== END GENERAL PROJECT SETTINGS ==================