cmake_minimum_required(VERSION 3.4.1)
project(breakpad-core)

set(ENABLE_INPROCESS ON)
set(ENABLE_OUTOFPROCESS ON)
set(ENABLE_LIBCORKSCREW ON)
set(ENABLE_LIBUNWIND ON)
set(ENABLE_LIBUNWINDSTACK ON)
set(ENABLE_CXXABI ON)
set(ENABLE_STACKSCAN ON)

if (${ENABLE_INPROCESS})
    add_definitions(-DENABLE_INPROCESS)
endif ()
if (${ENABLE_OUTOFPROCESS})
    add_definitions(-DENABLE_OUTOFPROCESS)
endif ()

set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Werror=implicit-function-declaration")
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++11 ")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../jniLibs/${CMAKE_ANDROID_ARCH_ABI})

# breakpad
include_directories(
        src/main/cpp/breakpad/src
        src/main/cpp/breakpad/src/common/android/include
)

add_subdirectory(
        src/main/cpp/breakpad
)

list(
        APPEND
        LINK_LIBRARIES
        breakpad
)

add_library(
        breakpad-core
        SHARED
        src/main/cpp/breakpad.cpp
)

target_link_libraries(
        breakpad-core
        ${LINK_LIBRARIES}
        log
)