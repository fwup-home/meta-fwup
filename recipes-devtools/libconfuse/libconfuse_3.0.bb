SUMMARY = "Small configuration file parser library for C"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/martinh/libconfuse"
SECTION = "devel"
LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b4e3ffd607d6686c6cb2f63394370841"

DEPENDS = "flex-native"

SRC_URI = "https://github.com/martinh/libconfuse/releases/download/v${PV}/${PN}-${PV}.tar.gz"

SRC_URI[md5sum] = "c534b51a2118ed57031dc548032304a3"
SRC_URI[sha256sum] = "bb75174e02aa8b44fa1a872a47beeea1f5fe715ab669694c97803eb6127cc861"

inherit autotools-brokensep lib_package

BBCLASSEXTEND = "native nativesdk"
